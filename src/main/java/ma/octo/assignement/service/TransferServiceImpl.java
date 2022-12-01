package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.audit.Audit;
import ma.octo.assignement.domain.audit.AuditTransfer;
import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.operationdto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.TransferMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.interfaces.AuditService;
import ma.octo.assignement.service.interfaces.TransferService;
import ma.octo.assignement.service.utils.OperationValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static ma.octo.assignement.service.utils.OperationValidationResult.SUCCES;
import static ma.octo.assignement.service.validators.OperationValidator.*;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final CompteRepository compteRepository;
    private final TransferRepository transferRepository;
    private final AuditService auditService;

    public TransferServiceImpl(CompteRepository compteRepository,
                               TransferRepository transferRepository,
                               AuditService monservice) {
        this.compteRepository = compteRepository;
        this.transferRepository = transferRepository;
        this.auditService = monservice;
    }

    @Override
    public List<TransferDto> allTransfer() {
        return transferRepository.findAll()
                .stream()
                .map(TransferMapper::mapToTransfertDto)
                .collect(Collectors.toList());
    }

    @Override
    public Transfer createTransaction(TransferDto transferDto)
            throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException {

        // validation input
        OperationValidationResult result = isNumeroCompteNonValide()
                .and(isMontantNonVide())
                .and(isMontantNonAtteind())
                .and(isMontantDepasse())
                .and(isMotifValid())
                .apply(transferDto);

        if (!result.equals(SUCCES))
            throw new  TransactionException(result.getType());

        // check accounts
        Compte emetteur = compteRepository.findByNumeroCompte(transferDto.getNrCompteEmetteur());
        Compte beneficiaire = compteRepository.findByNumeroCompte(transferDto.getNrCompteBeneficiaire());

        if (emetteur == null || beneficiaire == null) {
            throw new CompteNonExistantException(
                    "verifiez bien les numeros de comptes");
        }

        // check sold
        result = isMontantSuffisant(emetteur.getSolde()).apply(transferDto);

        if (!result.equals(SUCCES))
            throw new SoldeDisponibleInsuffisantException(result.getType());


        // update sold emetteur
        emetteur.setSolde(emetteur.getSolde().subtract(transferDto.getMontant()));
        compteRepository.save(emetteur);

        // update sold of the receiver
        beneficiaire.setSolde(beneficiaire.getSolde().add(transferDto.getMontant()));
        compteRepository.save(beneficiaire);

        // save the transfer details
        Transfer transfer = TransferMapper.mapToTransfer(transferDto, emetteur, beneficiaire);
        transferRepository.save(transfer);

        // save an audit transfer
        Audit audit = new AuditTransfer();
        String message = "Transfer depuis " + transferDto.getNrCompteEmetteur() + " vers " + transferDto
                .getNrCompteBeneficiaire() + " d'un montant de " + transferDto.getMontant()
                .toString();
        audit.setMessage(message);
        auditService.createAudit(audit);

        return transfer;

    }
}

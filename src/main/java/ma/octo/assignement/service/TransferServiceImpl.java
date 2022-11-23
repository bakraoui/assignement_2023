package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.audit.Audit;
import ma.octo.assignement.domain.audit.AuditTransfer;
import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.operationDto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.exceptions.TransferNonExistantException;
import ma.octo.assignement.mapper.TransferMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.interfaces.AuditService;
import ma.octo.assignement.service.interfaces.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ma.octo.assignement.service.validators.OperationValidator.*;
import static ma.octo.assignement.service.validators.OperationValidator.ValidationResult.SUCCES;

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
    public TransferDto getTransfer(Long id) {
        Optional<Transfer> optionalTransferDto = transferRepository.findById(id);

        if (!optionalTransferDto.isPresent())
            throw new TransferNonExistantException("ce transfert non existant");

        return TransferMapper.mapToTransfertDto(optionalTransferDto.get());
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

        ValidationResult result = isNumeroCompteNonValide()
                .and(isMontantNonVide())
                .and(isMontantNonAtteind())
                .and(isMontantDepasse())
                .and(isMotifValid())
                .apply(transferDto);

        if (!result.equals(SUCCES))
            throw new  TransactionException(result.getType());

        Compte emetteur = compteRepository.findByNumeroCompte(transferDto.getNrCompteEmetteur());
        Compte beneficiaire = compteRepository.findByNumeroCompte(transferDto.getNrCompteBeneficiaire());

        if (emetteur == null || beneficiaire == null) {
            throw new CompteNonExistantException(
                    "Compte Non existant, verifiez bien les deux numeros de comptes");
        }

        result = isMontantSuffisant(emetteur.getSolde()).apply(transferDto);

        if (!result.equals(SUCCES))
            throw new  TransactionException(result.getType());


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

package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.operation.Operation;
import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.TransferDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static ma.octo.assignement.service.validators.OperationValidator.*;
import static ma.octo.assignement.service.validators.OperationValidator.ValidationResult.SUCCES;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    public static final int MONTANT_MAXIMAL = 10000;
    Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);


    private final CompteRepository compteRepository;
    private final TransferRepository transferRepository;
    private final AuditService auditService;

    public TransferServiceImpl(CompteRepository compteRepository, TransferRepository transferRepository, AuditService monservice) {
        this.compteRepository = compteRepository;
        this.transferRepository = transferRepository;
        this.auditService = monservice;
    }

    @Override
    public Transfer getTransfer(Long id) {
        return transferRepository.findById(id)
                .orElseThrow(TransferNonExistantException::new);
    }

    @Override
    public List<Transfer> allTransfer() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer createTransaction(TransferDto transferDto)
            throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException {

        Compte emetteur = compteRepository.findByNrCompte(transferDto.getNrCompteEmetteur());
        Compte beneficiaire = compteRepository.findByNrCompte(transferDto.getNrCompteBeneficiaire());

        if (emetteur == null) {
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (beneficiaire == null) {
            throw new CompteNonExistantException("Compte Non existant");
        }

        ValidationResult result = isMontantNonVide()
                .and(isMontantNonAtteind())
                .and(isMontantDepasse())
                .and(isMotifValid())
                .and(isMontantSuffisant(emetteur.getSolde()))
                .apply(transferDto);

        if (!result.equals(SUCCES))
            throw new  TransactionException(result.getType());


        emetteur.setSolde(emetteur.getSolde().subtract(transferDto.getMontant()));
        compteRepository.save(emetteur);

        // update sold of the receiver
        beneficiaire.setSolde(
                new BigDecimal(beneficiaire.getSolde().intValue()
                                + transferDto.getMontant().intValue()));
        compteRepository.save(beneficiaire);

        // save the transfer details
        Transfer transfer = TransferMapper.toTransfer(transferDto, emetteur, beneficiaire);
        transferRepository.save(transfer);

        // save an audit transfer
        auditService.auditTransfer("Transfer depuis " + transferDto.getNrCompteEmetteur() + " vers " + transferDto
                .getNrCompteBeneficiaire() + " d'un montant de " + transferDto.getMontant()
                .toString());

        return transfer;

    }
}

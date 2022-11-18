package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.audit.Audit;
import ma.octo.assignement.domain.audit.AuditDeposit;
import ma.octo.assignement.domain.operation.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.DepositMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.DepositRepository;
import ma.octo.assignement.service.interfaces.AuditService;
import ma.octo.assignement.service.interfaces.DepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ma.octo.assignement.service.validators.OperationValidator.*;
import static  ma.octo.assignement.service.validators.OperationValidator.ValidationResult.*;

import java.util.List;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;
    private final CompteRepository compteRepository;
    private final AuditService auditService;

    public DepositServiceImpl(DepositRepository depositRepository,
                              CompteRepository compteRepository, AuditService auditService) {
        this.depositRepository = depositRepository;
        this.compteRepository = compteRepository;
        this.auditService = auditService;
    }


    @Override
    public List<MoneyDeposit> loadAll() {
        return depositRepository.findAll() ;
    }

    @Override
    public void createTransaction(DepositDto depositDto) throws TransactionException, CompteNonExistantException {

        ValidationResult result = isNumeroCompteNonValide()
                .and(isMontantNonVide())
                .and(isMontantNonAtteind())
                .and(isMontantDepasse())
                .and(isMotifValid())
                .apply(depositDto);

        if (!result.equals(SUCCES))
            throw new TransactionException(result.getType());


        Compte beneficiaire = compteRepository
                .findByNrCompte(depositDto.getNrCompteBeneficiaire());

        if (beneficiaire == null) {
            throw new CompteNonExistantException("Compte beneficiaire non Existant");
        }


        // update receiver's sold
        beneficiaire.setSolde(beneficiaire.getSolde().add(depositDto.getMontant()));
        compteRepository.save(beneficiaire);

        // create new deposit
        MoneyDeposit moneyDeposit = DepositMapper.toMoneyDeposit(depositDto, beneficiaire);
        depositRepository.save(moneyDeposit);

        // create an audit
        Audit audit = new AuditDeposit();
        String message = "Deposit fait par " + depositDto.getNomPrenomEmetteur() + " vers "
                + depositDto.getNrCompteBeneficiaire() + " d'un montant de " + depositDto.getMontant()
                .toString();
        audit.setMessage(message);
        auditService.createAudit(audit);

    }
}

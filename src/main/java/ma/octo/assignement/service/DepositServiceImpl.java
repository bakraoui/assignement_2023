package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.audit.Audit;
import ma.octo.assignement.domain.audit.AuditDeposit;
import ma.octo.assignement.domain.operation.MoneyDeposit;
import ma.octo.assignement.dto.operationdto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.DepositMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.DepositRepository;
import ma.octo.assignement.service.interfaces.AuditService;
import ma.octo.assignement.service.interfaces.DepositService;
import ma.octo.assignement.service.utils.OperationValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ma.octo.assignement.service.utils.OperationValidationResult.*;
import static ma.octo.assignement.service.validators.OperationValidator.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<DepositDto> loadAllDeposits() {
        return depositRepository.findAll()
                .stream().map(DepositMapper::mapToDepositDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createTransaction(DepositDto depositDto)
            throws TransactionException, CompteNonExistantException {

        // inputs validation
        OperationValidationResult result = isNumeroCompteNonValide()
                .and(isMontantNonVide())
                .and(isMontantNonAtteind())
                .and(isMontantDepasse())
                .and(isMotifValid())
                .apply(depositDto);

        if (!result.equals(SUCCES))
            throw new TransactionException(result.getType());

        // check account
        Compte beneficiaire = compteRepository
                .findByRib(depositDto.getRib());

        if (beneficiaire == null) {
            throw new CompteNonExistantException("Compte beneficiaire non Existant");
        }

        // create new deposit
        MoneyDeposit moneyDeposit = DepositMapper.mapToMoneyDeposit(depositDto, beneficiaire);
        depositRepository.save(moneyDeposit);

        // update receiver's sold
        beneficiaire.setSolde(beneficiaire.getSolde().add(depositDto.getMontant()));
        compteRepository.save(beneficiaire);

        // create an audit
        Audit audit = new AuditDeposit();
        String message = "Deposit fait par " + depositDto.getNomPrenomEmetteur() + " vers "
                + depositDto.getNrCompteBeneficiaire() + " d'un montant de "
                + depositDto.getMontant().toString();

        audit.setMessage(message);
        auditService.createAudit(audit);


    }
}

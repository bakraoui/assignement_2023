package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.operation.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface DepositService {

    List<MoneyDeposit> loadAll();
    void createTransaction(DepositDto depositDto) throws TransactionException, CompteNonExistantException;

}

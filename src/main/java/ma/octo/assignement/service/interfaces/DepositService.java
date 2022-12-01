package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.dto.operationdto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface DepositService {

    List<DepositDto> loadAllDeposits();
    void createTransaction(DepositDto depositDto) throws TransactionException, CompteNonExistantException;

}

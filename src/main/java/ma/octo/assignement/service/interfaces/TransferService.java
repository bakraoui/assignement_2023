package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.operationdto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface TransferService {

    List<TransferDto> allTransfer();
    Transfer createTransaction(TransferDto transferDto) throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException;
}

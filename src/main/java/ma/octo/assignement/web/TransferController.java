package ma.octo.assignement.web;

import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.TransferServiceImpl;
import ma.octo.assignement.service.interfaces.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/transfers")
class TransferController {

    Logger LOGGER = LoggerFactory.getLogger(TransferController.class);
    @Autowired
    private final TransferService transferService;

    @Autowired
    TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    List<Transfer> loadAll() {
        LOGGER.info("Lister des utilisateurs");
        return transferService.allTransfer();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransferDto transferDto)
            throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException {
       transferService.createTransaction(transferDto);
    }


}

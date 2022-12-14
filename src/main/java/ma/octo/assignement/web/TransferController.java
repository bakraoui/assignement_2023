package ma.octo.assignement.web;

import ma.octo.assignement.dto.operationdto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.interfaces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/transfers")
class TransferController {

    @Autowired
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    List<TransferDto> loadAll() {
        return transferService.allTransfer();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDto createTransaction(@RequestBody TransferDto transferDto)
            throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException {
       return transferService.createTransaction(transferDto);
    }


}

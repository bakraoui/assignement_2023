package ma.octo.assignement.web;

import ma.octo.assignement.dto.operationDto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.interfaces.DepositService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deposits")
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody DepositDto depositDto)
            throws CompteNonExistantException, TransactionException {
        depositService.createTransaction(depositDto);
    }
}

package ma.octo.assignement.web;

import ma.octo.assignement.dto.comptedto.CompteRequestDto;
import ma.octo.assignement.dto.comptedto.CompteResponseDto;
import ma.octo.assignement.service.interfaces.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteController  {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping
    List<CompteResponseDto> loadAllComptes() {
        return compteService.allComptes();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompteResponseDto save(@RequestBody CompteRequestDto compteRequestDto){
        return compteService.saveCompte(compteRequestDto);
    }

}

package ma.octo.assignement.web;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.service.interfaces.CompteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping
    List<CompteResponseDto> loadAllComptes() {
        return compteService.allComptes();
    }


    @PostMapping
    public CompteResponseDto save(@RequestBody CompteRequestDto compteRequestDto){
        return compteService.save(compteRequestDto);
    }

}

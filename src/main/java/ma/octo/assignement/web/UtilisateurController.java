package ma.octo.assignement.web;

import ma.octo.assignement.dto.utilisateurdto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurdto.UtilisateurResponseDto;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UtilisateurRequestDto utilisateurRequestDto) {
        utilisateurService.save(utilisateurRequestDto);
    }

    @GetMapping
    List<UtilisateurResponseDto> loadAllUtilisateur() {
        return utilisateurService.allUtilisateurs();
    }
}

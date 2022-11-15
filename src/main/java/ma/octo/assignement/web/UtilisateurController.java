package ma.octo.assignement.web;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.service.UtilisateurServiceImpl;
import ma.octo.assignement.service.interfaces.UtilisateurService;
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
    public void save(@RequestBody UtilisateurDto utilisateurDto) {
        utilisateurService.save(utilisateurDto);
    }

    @GetMapping("")
    List<Utilisateur> loadAllUtilisateur() {
        return utilisateurService.allUtilisateurs();
    }
}

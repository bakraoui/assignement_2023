package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {


    Utilisateur save(UtilisateurDto utilisateurDto);
    List<Utilisateur> allUtilisateurs();

}

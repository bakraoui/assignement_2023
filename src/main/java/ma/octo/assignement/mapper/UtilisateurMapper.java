package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;

public class UtilisateurMapper {

    public static UtilisateurDto map(Utilisateur utilisateur) {
        UtilisateurDto utilisateurDto = new UtilisateurDto();

        utilisateurDto.setFirstname(utilisateur.getFirstname());
        utilisateurDto.setLastname(utilisateur.getLastname());
        utilisateurDto.setGender(utilisateur.getGender());
        utilisateurDto.setBirthdate(utilisateur.getBirthdate());
        utilisateurDto.setUsername(utilisateur.getUsername());


        return utilisateurDto;
    }

    public static Utilisateur toUtilisateur(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setFirstname(utilisateurDto.getFirstname());
        utilisateur.setLastname(utilisateurDto.getLastname());
        utilisateur.setGender(utilisateurDto.getGender());
        utilisateur.setUsername(utilisateurDto.getUsername());
        utilisateur.setBirthdate(utilisateurDto.getBirthdate());

        return utilisateur;
    }
}

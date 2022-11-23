package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurResponseDto;

public class UtilisateurMapper {

    public static UtilisateurResponseDto mapToUtilisateurResponseDto(Utilisateur utilisateur) {
        UtilisateurResponseDto utilisateurResponseDto = new UtilisateurResponseDto();

        utilisateurResponseDto.setFirstname(utilisateur.getFirstname());
        utilisateurResponseDto.setLastname(utilisateur.getLastname());
        utilisateurResponseDto.setGender(utilisateur.getGender());
        utilisateurResponseDto.setBirthdate(utilisateur.getBirthDate());
        utilisateurResponseDto.setUsername(utilisateur.getUsername());

        return utilisateurResponseDto;
    }

    public static Utilisateur mapToUtilisateur(UtilisateurRequestDto utilisateurRequestDto) {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setFirstname(utilisateurRequestDto.getFirstname());
        utilisateur.setLastname(utilisateurRequestDto.getLastname());
        utilisateur.setGender(utilisateurRequestDto.getGender());
        utilisateur.setBirthDate(utilisateurRequestDto.getBirthdate());
        utilisateur.setUsername(utilisateurRequestDto.getUsername());
        utilisateur.setPassword(utilisateurRequestDto.getPassword());



        return utilisateur;
    }
}

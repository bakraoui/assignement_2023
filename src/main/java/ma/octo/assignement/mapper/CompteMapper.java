package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.comptedto.CompteRequestDto;
import ma.octo.assignement.dto.comptedto.CompteResponseDto;

public class CompteMapper {


    public static CompteResponseDto mapToCompteResponseDto(Compte compte){
        CompteResponseDto compteResponseDto = new CompteResponseDto();

        compteResponseDto.setNrCompte(compte.getNumeroCompte());
        compteResponseDto.setRib(compte.getRib());
        compteResponseDto.setSolde(compte.getSolde());
        compteResponseDto.setUtilisateurUsername(compte.getUtilisateur().getUsername());

        return compteResponseDto;
    }

    public static Compte mapToCompte(CompteRequestDto compteRequestDto, Utilisateur utilisateur){
        Compte compte = new Compte();

        compte.setNumeroCompte(compteRequestDto.getNumeroCompte());
        compte.setRib(compteRequestDto.getRib());
        compte.setSolde(compteRequestDto.getSolde());
        compte.setUtilisateur(utilisateur);

        return compte;
    }
}

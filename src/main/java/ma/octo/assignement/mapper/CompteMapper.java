package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;

public class CompteMapper {


    public static CompteResponseDto map(Compte compte){
        CompteResponseDto compteResponseDto = new CompteResponseDto();

        compteResponseDto.setNrCompte(compte.getNrCompte());
        compteResponseDto.setRib(compte.getRib());
        compteResponseDto.setSolde(compte.getSolde());
        compteResponseDto.setUtilisateurUsername(compte.getUtilisateur().getUsername());

        return compteResponseDto;
    }

    public static Compte toCompte(CompteRequestDto compteRequestDto, Utilisateur utilisateur){
        Compte compte = new Compte();

        compte.setNrCompte(compteRequestDto.getNrCompte());
        compte.setRib(compteRequestDto.getRib());
        compte.setSolde(compteRequestDto.getSolde());
        compte.setUtilisateur(utilisateur);

        return compte;
    }
}

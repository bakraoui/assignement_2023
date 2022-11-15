package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.CompteDto;

public class CompteMapper {


    public static CompteDto map(Compte compte){

        CompteDto compteDto = new CompteDto();

        compteDto.setNrCompte(compte.getNrCompte());
        compteDto.setRib(compte.getRib());
        compteDto.setSolde(compte.getSolde());
        compteDto.setUtilisateurUsername(compte.getUtilisateur().getUsername());

        return compteDto;
    }


    public static Compte toCompte(CompteDto compteDto, Utilisateur utilisateur){
        Compte compte = new Compte();

        compte.setNrCompte(compteDto.getNrCompte());
        compte.setRib(compteDto.getRib());
        compte.setSolde(compteDto.getSolde());
        compte.setUtilisateur(utilisateur);

        return compte;
    }
}

package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.DepositService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

public class DepositTest {

    private final DepositService depositService;
    private final UtilisateurService utilisateurService;
    private final CompteService compteService;

    @Autowired
    public DepositTest(DepositService depositService, UtilisateurService utilisateurService, CompteService compteService) {
        this.depositService = depositService;
        this.utilisateurService = utilisateurService;
        this.compteService = compteService;
    }

    @Test
    public void save() throws TransactionException, CompteNonExistantException {

        // creer un utilisateur et son compte
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setUsername("userF");
        utilisateurDto.setLastname("lastF");
        utilisateurDto.setFirstname("firstF");
        utilisateurDto.setGender("Male");
        Utilisateur utilisateur = utilisateurService.save(utilisateurDto);

        CompteDto compteDto = new CompteDto();
        compteDto.setNrCompte("010000F000001000");
        compteDto.setRib("RIBF");
        compteDto.setSolde(BigDecimal.valueOf(200_000.0).setScale(2, RoundingMode.HALF_DOWN));
        compteDto.setUtilisateurUsername(utilisateur.getUsername());
        Compte compte = compteService.save(compteDto);

        // faire un deposit d'argent
        DepositDto depositDto = new DepositDto();
        depositDto.setDate(new Date());
        depositDto.setNomPrenomEmetteur("Bakraoui Ayoub");
        depositDto.setMontant(BigDecimal.valueOf(10_000).setScale(2, RoundingMode.CEILING));
        depositDto.setNrCompteBeneficiaire("010000F000001000");
        depositDto.setMotif("Deposit de Ayoub Bakraoui");
        depositService.createTransaction(depositDto);


        // THEN

        Compte beneficiaire = compteService.getCompte("010000F000001000");

        assertThat(beneficiaire.getSolde())
                .isEqualByComparingTo(compte.getSolde().add(depositDto.getMontant()));


    }
}

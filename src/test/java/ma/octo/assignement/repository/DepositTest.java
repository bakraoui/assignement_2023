package ma.octo.assignement.repository;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.dto.operationDto.DepositDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurResponseDto;
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
        UtilisateurRequestDto utilisateurRequestDto = new UtilisateurRequestDto();
        utilisateurRequestDto.setUsername("userF");
        utilisateurRequestDto.setLastname("lastF");
        utilisateurRequestDto.setFirstname("firstF");
        utilisateurRequestDto.setPassword("ffff");
        utilisateurRequestDto.setGender("Male");
        UtilisateurResponseDto utilisateur = utilisateurService.save(utilisateurRequestDto);

        CompteRequestDto compteRequestDto = new CompteRequestDto();
        compteRequestDto.setNrCompte("010000F000001000");
        compteRequestDto.setRib("RIBF");
        compteRequestDto.setSolde(BigDecimal.valueOf(200_000.0).setScale(2, RoundingMode.HALF_DOWN));
        compteRequestDto.setUtilisateurUsername(utilisateur.getUsername());
        CompteResponseDto compte = compteService.save(compteRequestDto);

        // faire un deposit d'argent
        DepositDto depositDto = new DepositDto();
        depositDto.setDate(new Date());
        depositDto.setRib("RIBF");
        depositDto.setNomPrenomEmetteur("Bakraoui Ayoub");
        depositDto.setMontant(BigDecimal.valueOf(10_000).setScale(2, RoundingMode.CEILING));
        depositDto.setNrCompteBeneficiaire("010000F000001000");
        depositDto.setMotif("Deposit de Ayoub Bakraoui");
        depositService.createTransaction(depositDto);

        // THEN
        CompteResponseDto beneficiaire = compteService.getCompte("010000F000001000");

        // solde du beneficiaire doit etre = old + deposit
        // 200_000 + 10_000 = 210_000
        assertThat(beneficiaire.getSolde())
                .isEqualByComparingTo(compte.getSolde().add(depositDto.getMontant()));


    }
}

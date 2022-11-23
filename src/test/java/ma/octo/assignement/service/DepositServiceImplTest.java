package ma.octo.assignement.service;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.dto.operationDto.DepositDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.DepositService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class DepositServiceImplTest {

    // solde initiale
    public static final int BENEFICIAIRE_SOLD = 200_000;

    // montant du deposit
    public static final int MONTANT_DEPOSIT = 10_000;

    // solde apres transaction
    public static final int EXPECTED_BENEFICIAIRE_SOLD = 210_000;


    private final DepositService depositService;
    private final UtilisateurService utilisateurService;
    private final CompteService compteService;

    @Autowired
    public DepositServiceImplTest(DepositService depositService, UtilisateurService utilisateurService, CompteService compteService) {
        this.depositService = depositService;
        this.utilisateurService = utilisateurService;
        this.compteService = compteService;
    }

    @Test
    public void save() throws TransactionException, CompteNonExistantException {

        // creer un utilisateur et son compte
        UtilisateurRequestDto utilisateurRequest = UtilisateurRequestDto.builder()
                .username("userF")
                .password("ffff")
                .firstname("firstF")
                .lastname("lastF")
                .gender("Male")
                .build();
        utilisateurService.save(utilisateurRequest);

        CompteRequestDto compteRequest = CompteRequestDto.builder()
                .numeroCompte("010000F000001000")
                .rib("RIBF")
                .solde(BigDecimal.valueOf(BENEFICIAIRE_SOLD).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("userF")
                .build();
        compteService.saveCompte(compteRequest);

        // faire un deposit d'argent
        DepositDto depositDto = new DepositDto();
        depositDto.setDate(new Date());
        depositDto.setRib("RIBF");
        depositDto.setNomPrenomEmetteur("Bakraoui Ayoub");
        depositDto.setMontant(BigDecimal.valueOf(MONTANT_DEPOSIT).setScale(2, RoundingMode.FLOOR));
        depositDto.setNrCompteBeneficiaire("010000F000001000");
        depositDto.setMotif("Deposit de Ayoub Bakraoui");
        depositService.createTransaction(depositDto);


        // solde du beneficiaire doit etre = old + deposit
        // 200_000 + 10_000 = 210_000

        CompteResponseDto beneficiaire = compteService.getCompte("010000F000001000");

        assertThat(beneficiaire.getSolde())
                .isEqualByComparingTo(BigDecimal.valueOf(EXPECTED_BENEFICIAIRE_SOLD));


    }
}

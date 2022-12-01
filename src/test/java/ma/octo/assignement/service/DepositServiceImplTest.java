package ma.octo.assignement.service;

import ma.octo.assignement.dto.comptedto.CompteRequestDto;
import ma.octo.assignement.dto.comptedto.CompteResponseDto;
import ma.octo.assignement.dto.operationdto.DepositDto;
import ma.octo.assignement.dto.utilisateurdto.UtilisateurRequestDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.DepositService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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


    @BeforeAll
    public void setUp() {
        // creer un utilisateur et son compte
        UtilisateurRequestDto utilisateurRequest = UtilisateurRequestDto.builder()
                .username("username")
                .password("1234")
                .firstname("firstname")
                .lastname("lastname")
                .gender("Male")
                .build();
        utilisateurService.save(utilisateurRequest);

        CompteRequestDto compteRequest = CompteRequestDto.builder()
                .numeroCompte("010000F000001000")
                .rib("RIB")
                .solde(BigDecimal.valueOf(BENEFICIAIRE_SOLD).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("username")
                .build();
        compteService.saveCompte(compteRequest);

    }
    @Test
    public void save_deposit_successfully() {
        // faire un deposit d'argent
        DepositDto depositDto = new DepositDto();
        depositDto.setDate(new Date());
        depositDto.setRib("RIB");
        depositDto.setNomPrenomEmetteur("Bakraoui Ayoub");
        depositDto.setMontant(BigDecimal.valueOf(MONTANT_DEPOSIT).setScale(2, RoundingMode.FLOOR));
        depositDto.setNrCompteBeneficiaire("010000F000001000");
        depositDto.setMotif("Deposit de Ayoub Bakraoui");
        depositService.createTransaction(depositDto);

        // solde du beneficiaire doit etre = old + deposit
        // 200_000 + 10_000 = 210_000
        CompteResponseDto beneficiaire = compteService.getCompteByNrCompte("010000F000001000");
        assertThat(beneficiaire.getSolde())
                .isEqualByComparingTo(BigDecimal.valueOf(EXPECTED_BENEFICIAIRE_SOLD));

    }


    @Test
    public void save_deposit_shouldThrow_CompteNonExistantException() {
        // faire un deposit d'argent
        DepositDto depositDto = new DepositDto();
        depositDto.setDate(new Date());
        depositDto.setRib("RIB0"); // rib n'existe pas
        depositDto.setNomPrenomEmetteur("Bakraoui Ayoub");
        depositDto.setMontant(BigDecimal.valueOf(MONTANT_DEPOSIT).setScale(2, RoundingMode.FLOOR));
        depositDto.setNrCompteBeneficiaire("010000F000001000"); // numero de compte n'existe pas
        depositDto.setMotif("Deposit de Ayoub Bakraoui");

        assertThrows(CompteNonExistantException.class, ()->{
                depositService.createTransaction(depositDto);
        });
    }
}

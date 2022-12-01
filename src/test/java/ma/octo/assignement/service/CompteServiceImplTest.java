package ma.octo.assignement.service;

import ma.octo.assignement.dto.comptedto.CompteRequestDto;
import ma.octo.assignement.dto.comptedto.CompteResponseDto;
import ma.octo.assignement.dto.utilisateurdto.UtilisateurRequestDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.CompteValidationException;
import ma.octo.assignement.exceptions.UtilisateurNonExistantException;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompteServiceImplTest {

    private final CompteService compteService;
    private final UtilisateurService utilisateurService;

    @Autowired
    public CompteServiceImplTest(CompteService compteService, UtilisateurService utilisateurService) {
        this.compteService = compteService;
        this.utilisateurService = utilisateurService;
    }

    @BeforeAll
    public void setUp()  {
        // Create utilisateur + compte (1)
        UtilisateurRequestDto utilisateur1 =UtilisateurRequestDto.builder()
                .username("username1")
                .password("1234")
                .lastname("lastname1")
                .firstname("firstname1")
                .gender("Male")
                .build();
        utilisateurService.save(utilisateur1);

        CompteRequestDto compteRequest1 = CompteRequestDto.builder()
                .numeroCompte("010000C000001000")

                .rib("RIB1")
                .solde(BigDecimal.valueOf(200000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("username1")
                .build();
        compteService.saveCompte(compteRequest1);

        // Create utilisateur + compte (2)
        UtilisateurRequestDto utilisateur2 = UtilisateurRequestDto.builder()
                .username("username2")
                .password("1234")
                .lastname("lastname2")
                .firstname("firstname2")
                .gender("Female")
                .build();
        utilisateurService.save(utilisateur2);

        CompteRequestDto compteRequest2 = CompteRequestDto.builder()
                .numeroCompte("010000D025001000")
                .rib("RIB2")
                .solde(BigDecimal.valueOf(140000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("username2")
                .build();
        compteService.saveCompte(compteRequest2);
    }

    @Test
    public void create_compte_shouldThrow_UtilisateurNonExistantException() {
        CompteRequestDto compteRequest = CompteRequestDto.builder()
                .numeroCompte("010000C000001000")
                .rib("RIB1")
                .solde(BigDecimal.valueOf(200000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("usernameX")
                .build();

        assertThrows(UtilisateurNonExistantException.class, ()->{
            compteService.saveCompte(compteRequest);
        });
    }

    @Test
    public void create_compte_shouldThrow_CompteValidationException_RibNotProvided() {
        CompteRequestDto compteRequest = CompteRequestDto.builder()
                .numeroCompte("010000C000001000")
                //.rib("RIB1")
                .solde(BigDecimal.valueOf(200000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("usernameX")
                .build();

        assertThrows(CompteValidationException.class, ()->{
            compteService.saveCompte(compteRequest);
        });
    }

    @Test
    public void create_compte_shouldThrow_CompteValidationException_NrCompteNotProvided() {
        CompteRequestDto compteRequest = CompteRequestDto.builder()
                //.numeroCompte("010000C000001000")
                .rib("RIB1")
                .solde(BigDecimal.valueOf(200000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("usernameX")
                .build();

        assertThrows(CompteValidationException.class, ()->{
            compteService.saveCompte(compteRequest);
        });
    }


    @Test
    public void getCompte_shouldThrow_CompteNonExistException() {

        // Check if the account was saved
        assertThrows(CompteNonExistantException.class, () -> {
            // numero de compte non existant
            compteService.getCompteByNrCompte("01000C000001000");
        });

    }


    @Test
    public void getCompte_successfully() {

        // numero de compte non existant
        CompteResponseDto compte = compteService.getCompteByNrCompte("010000C000001000");
        assertThat(compte).isNotNull();

    }

    @Test
    public void getAllComptes_() {

        // expected elements
        CompteResponseDto savedCompte1 = compteService.getCompteByNrCompte("010000C000001000");
        CompteResponseDto savedCompte2 = compteService.getCompteByNrCompte("010000D025001000");

        // actual elements = result
        List<CompteResponseDto> comptes = compteService.allComptes();

        assertThat(comptes).isEqualTo(List.of(savedCompte1, savedCompte2));

    }
}

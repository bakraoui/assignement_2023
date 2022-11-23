package ma.octo.assignement.service;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurResponseDto;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CompteServiceImplTest {

    private final CompteService compteService;
    private final UtilisateurService utilisateurService;

    @Autowired
    public CompteServiceImplTest(CompteService compteService, UtilisateurService utilisateurService) {
        this.compteService = compteService;
        this.utilisateurService = utilisateurService;
    }

    @Test
    public void testShouldSavedSuccessfully() {

        // Create UtilisateurRequestDto
        UtilisateurRequestDto utilisateurRequest = UtilisateurRequestDto.builder()
                .username("userA")
                .password("aaaa")
                .lastname("lastA")
                .firstname("firstA")
                .gender("Male")
                .build();
        UtilisateurResponseDto utilisateur = utilisateurService.save(utilisateurRequest);

        // Create a CompteRequestDto for the user
        CompteRequestDto compteRequest = CompteRequestDto.builder()
                .numeroCompte("010000A000001000")
                .rib("RIBA")
                .solde(BigDecimal.valueOf(200000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername(utilisateur.getUsername())
                .build();

        CompteResponseDto savedCompte = compteService.saveCompte(compteRequest);

        // Check if the account was saved
        CompteResponseDto compte = compteService.getCompte("010000A000001000");

        // compte should be not null if the account inserted successfully
        assertThat(compte).isNotNull();

        // compte should be equal to the inserted account
        assertThat(compte).isEqualTo(savedCompte);

    }

    @Test
    public void allComptes() {
        // Create utilisateur + compte (1)
        UtilisateurRequestDto utilisateur1 =UtilisateurRequestDto.builder()
                .username("userC")
                .password("cccc")
                .lastname("lastC")
                .firstname("firstC")
                .gender("Male")
                .build();
        utilisateurService.save(utilisateur1);

        CompteRequestDto compteRequest1 = CompteRequestDto.builder()
                .numeroCompte("010000C000001000")
                .rib("RIBC")
                .solde(BigDecimal.valueOf(200000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("userC")
                .build();
        compteService.saveCompte(compteRequest1);

        // Create utilisateur + compte (2)
        UtilisateurRequestDto utilisateur2 = UtilisateurRequestDto.builder()
                .username("userD")
                .password("dddd")
                .lastname("lastD")
                .firstname("firstD")
                .gender("Female")
                .build();
        utilisateurService.save(utilisateur2);

        CompteRequestDto compteRequest2 = CompteRequestDto.builder()
                .numeroCompte("010000D025001000")
                .rib("RIBD")
                .solde(BigDecimal.valueOf(140000).setScale(2, RoundingMode.FLOOR))
                .utilisateurUsername("userD")
                .build();
        compteService.saveCompte(compteRequest2);

        // expected elements
        CompteResponseDto savedCompte1 = compteService.getCompte("010000C000001000");
        CompteResponseDto savedCompte2 = compteService.getCompte("010000D025001000");

        // actual elements = result
        List<CompteResponseDto> comptes = compteService.allComptes();

        assertThat(comptes).isEqualTo(List.of(savedCompte1, savedCompte2));

    }
}

package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurResponseDto;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CompteTest {

    private final CompteService compteService;
    private final UtilisateurService utilisateurService;
    @Autowired
    public CompteTest(CompteService compteService, UtilisateurService utilisateurService) {
        this.compteService = compteService;
        this.utilisateurService = utilisateurService;
    }

    @Test
    public void saveCompte() {
        // GIVEN
        UtilisateurRequestDto utilisateurRequestDto = new UtilisateurRequestDto();
        utilisateurRequestDto.setUsername("userA");
        utilisateurRequestDto.setPassword("aaaa");
        utilisateurRequestDto.setLastname("lastA");
        utilisateurRequestDto.setFirstname("firstA");
        utilisateurRequestDto.setGender("Male");
        UtilisateurResponseDto saveUtilisateur = utilisateurService.save(utilisateurRequestDto);

        // WHEN
        CompteRequestDto compteRequestDto = new CompteRequestDto();
        compteRequestDto.setNrCompte("010000A000001000");
        compteRequestDto.setRib("RIBA");
        compteRequestDto.setSolde(BigDecimal.valueOf(200000.0).setScale(2, RoundingMode.HALF_DOWN));
        compteRequestDto.setUtilisateurUsername(saveUtilisateur.getUsername());
        CompteResponseDto compte = compteService.save(compteRequestDto);

        // THEN
        assertThat(compte)
                .isEqualTo(compteService.getCompte(compteRequestDto.getNrCompte()));

    }

    @Test
    public void allComptes() {

        // given
        UtilisateurRequestDto utilisateur1 = new UtilisateurRequestDto();
        utilisateur1.setUsername("userC");
        utilisateur1.setPassword("cccc");
        utilisateur1.setLastname("lastC");
        utilisateur1.setFirstname("firstC");
        utilisateur1.setGender("Male");
        utilisateurService.save(utilisateur1);
        UtilisateurResponseDto savedUtilisateur1 = utilisateurService.loadUserByUsername("userC");

        CompteRequestDto compteRequestDto1 = new CompteRequestDto();
        compteRequestDto1.setNrCompte("010000C000001000");
        compteRequestDto1.setRib("RIBC");
        compteRequestDto1.setSolde(BigDecimal.valueOf(200000.0).setScale(2, RoundingMode.HALF_DOWN));
        compteRequestDto1.setUtilisateurUsername(savedUtilisateur1.getUsername());
        compteService.save(compteRequestDto1);

        UtilisateurRequestDto utilisateur2 = new UtilisateurRequestDto();
        utilisateur2.setUsername("userD");
        utilisateur2.setPassword("dddd");
        utilisateur2.setLastname("lastD");
        utilisateur2.setFirstname("firstD");
        utilisateur2.setGender("Female");
        utilisateurService.save(utilisateur2);
        UtilisateurResponseDto savedUtilisateur2 = utilisateurService.loadUserByUsername("userD");

        CompteRequestDto compteRequestDto = new CompteRequestDto();
        compteRequestDto.setNrCompte("010000D025001000");
        compteRequestDto.setRib("RIBD");
        compteRequestDto.setSolde(BigDecimal.valueOf(140000).setScale(2, RoundingMode.HALF_DOWN));
        compteRequestDto.setUtilisateurUsername(savedUtilisateur2.getUsername());
        compteService.save(compteRequestDto);

        CompteResponseDto savedCompte1 = compteService.getCompte("010000C000001000");
        CompteResponseDto savedCompte2 = compteService.getCompte("010000D025001000");

        // when
        List<CompteResponseDto> comptes = compteService.allComptes();

        // then
        assertThat(List.of(savedCompte1, savedCompte2))
                .isEqualTo(comptes);

    }
}

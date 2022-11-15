package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.service.CompteServiceImpl;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CompteTest {

    @Autowired
    CompteService compteService;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Test
    public void saveCompte() {
        // GIVEN
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setUsername("userA");
        utilisateurDto.setLastname("lastA");
        utilisateurDto.setFirstname("firstA");
        utilisateurDto.setGender("Male");
        Utilisateur saveUtilisateur = utilisateurService.save(utilisateurDto);

        // WHEN
        CompteDto compteDto = new CompteDto();
        compteDto.setNrCompte("010000A000001000");
        compteDto.setRib("RIBA");
        compteDto.setSolde(BigDecimal.valueOf(200000.0).setScale(2, RoundingMode.HALF_DOWN));
        compteDto.setUtilisateurUsername(saveUtilisateur.getUsername());
        Compte compte = compteService.save(compteDto);

        // THEN
        assertThat(compte)
                .isEqualTo(compteService.getCompte(compteDto.getNrCompte()));

    }

    @Test
    public void getCompte() {
        // GIVEN
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setUsername("userG");
        utilisateurDto.setLastname("lastG");
        utilisateurDto.setFirstname("firstG");
        utilisateurDto.setGender("Male");
        Utilisateur saveUtilisateur = utilisateurService.save(utilisateurDto);

        // WHEN
        CompteDto compteDto = new CompteDto();
        compteDto.setNrCompte("010000G000001000");
        compteDto.setRib("RIBG");
        compteDto.setSolde(BigDecimal.valueOf(200000.0).setScale(2, RoundingMode.HALF_DOWN));
        compteDto.setUtilisateurUsername(saveUtilisateur.getUsername());
        Compte compte = compteService.save(compteDto);

        // THEN
        assertThat(compte)
                .isEqualTo(compteService.getCompte(compteDto.getNrCompte()));
    }

    @Test
    public void allComptes() {

        // given
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setUsername("userC");
        utilisateur1.setLastname("lastC");
        utilisateur1.setFirstname("firstC");
        utilisateur1.setGender("Male");
        utilisateurRepository.save(utilisateur1);
        Utilisateur savedUtilisateur1 = utilisateurRepository.findByUsername("userC");

        CompteDto compteDto1 = new CompteDto();
        compteDto1.setNrCompte("010000C000001000");
        compteDto1.setRib("RIBC");
        compteDto1.setSolde(BigDecimal.valueOf(200000.0).setScale(2, RoundingMode.HALF_DOWN));
        compteDto1.setUtilisateurUsername(savedUtilisateur1.getUsername());
        compteService.save(compteDto1);

        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setUsername("userD");
        utilisateur2.setLastname("lastD");
        utilisateur2.setFirstname("firstD");
        utilisateur2.setGender("Female");
        utilisateurRepository.save(utilisateur2);
        Utilisateur savedUtilisateur2 = utilisateurRepository.findByUsername("userD");

        CompteDto compteDto = new CompteDto();
        compteDto.setNrCompte("010000D025001000");
        compteDto.setRib("RIBD");
        compteDto.setSolde(BigDecimal.valueOf(140000).setScale(2, RoundingMode.HALF_DOWN));
        compteDto.setUtilisateurUsername(savedUtilisateur2.getUsername());
        compteService.save(compteDto);

        Compte savedCompte1 = compteService.getCompte("010000C000001000");
        Compte savedCompte2 = compteService.getCompte("010000D025001000");

        // when
        List<Compte> comptes = compteService.allComptes();

        // then
        assertThat(List.of(savedCompte1, savedCompte2))
                .isEqualTo(comptes);

    }
}

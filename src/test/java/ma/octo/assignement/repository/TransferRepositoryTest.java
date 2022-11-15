package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.audit.Audit;
import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.CompteServiceImpl;
import ma.octo.assignement.service.TransferServiceImpl;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.TransferService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

// @ExtendWith(SpringExtension.class)
@SpringBootTest
// @Transactional
public class TransferRepositoryTest {

  @Autowired
  CompteService compteService;
  @Autowired
  UtilisateurService utilisateurService;
  @Autowired
  private TransferService transferService;

  @Test
  public void save() throws TransactionException, CompteNonExistantException, SoldeDisponibleInsuffisantException {

    // Creer un Compte Beneficiaire avec utilisateur beneficiaire
    UtilisateurDto utilisateurDtoBeneficiaire = new UtilisateurDto();
    utilisateurDtoBeneficiaire.setUsername("userB");
    utilisateurDtoBeneficiaire.setLastname("lastB");
    utilisateurDtoBeneficiaire.setFirstname("firstB");
    utilisateurDtoBeneficiaire.setGender("Male");
    Utilisateur utilisateurBeneficiaire = utilisateurService.save(utilisateurDtoBeneficiaire);

    CompteDto compteDtoBeneficiaire = new CompteDto();
    compteDtoBeneficiaire.setNrCompte("010000B000001000");
    compteDtoBeneficiaire.setRib("RIBB");
    compteDtoBeneficiaire.setSolde(BigDecimal.valueOf(200000.0).setScale(2, RoundingMode.HALF_DOWN));
    compteDtoBeneficiaire.setUtilisateurUsername(utilisateurBeneficiaire.getUsername());
    compteService.save(compteDtoBeneficiaire);

    // Creer un Compte Emetteur avec utilisateur emetteur
    UtilisateurDto utilisateurDtoEmetteur = new UtilisateurDto();
    utilisateurDtoEmetteur.setUsername("userE");
    utilisateurDtoEmetteur.setLastname("lastE");
    utilisateurDtoEmetteur.setFirstname("firstE");
    utilisateurDtoEmetteur.setGender("Female");
    Utilisateur utilisateurEmetteur = utilisateurService.save(utilisateurDtoEmetteur);

    CompteDto compteDtoEmetteur = new CompteDto();
    compteDtoEmetteur.setNrCompte("010000E025001000");
    compteDtoEmetteur.setRib("RIBE");
    compteDtoEmetteur.setSolde(BigDecimal.valueOf(140000).setScale(2, RoundingMode.HALF_DOWN));
    compteDtoEmetteur.setUtilisateurUsername(utilisateurEmetteur.getUsername());
    compteService.save(compteDtoEmetteur);

    // creer un transfer
    TransferDto transferDto = new TransferDto();
    transferDto.setMotif("TRANSFERT D'ARGENT");
    transferDto.setMontant(BigDecimal.valueOf(10000));
    transferDto.setDate(new Date());
    transferDto.setNrCompteEmetteur("010000E025001000");
    transferDto.setNrCompteBeneficiaire("010000B000001000");

    Transfer transfer = transferService.createTransaction(transferDto);


    Compte compteEmetteur = compteService.getCompte("010000E025001000");
    Compte compteBeneficiaire = compteService.getCompte("010000B000001000");

    // THEN

    // soldEmetteur = oldSoldEmetteur - transferredMontant
    assertThat(compteEmetteur.getSolde())
            .isEqualByComparingTo(compteDtoEmetteur.getSolde().subtract(transferDto.getMontant()));

    // soldBeneficiaire = oldSoldBeneficiaire + transferredMontant
    assertThat(compteBeneficiaire.getSolde())
            .isEqualByComparingTo(compteDtoBeneficiaire.getSolde().add(transferDto.getMontant()) );



  }


}
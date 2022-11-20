package ma.octo.assignement.repository;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.dto.operationDto.TransferDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
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

// @ExtendWith(SpringExtension.class)

@SpringBootTest
public class TransferRepositoryTest {

  private final CompteService compteService;
  private final UtilisateurService utilisateurService;
  private final TransferService transferService;

  @Autowired
  public TransferRepositoryTest(CompteService compteService, UtilisateurService utilisateurService, TransferService transferService) {
    this.compteService = compteService;
    this.utilisateurService = utilisateurService;
    this.transferService = transferService;
  }

  @Test
  public void save() throws TransactionException, CompteNonExistantException, SoldeDisponibleInsuffisantException {

    // Creer un Compte Beneficiaire avec utilisateur beneficiaire
    UtilisateurRequestDto utilisateurRequestDtoBeneficiaire = new UtilisateurRequestDto();
    utilisateurRequestDtoBeneficiaire.setUsername("userB");
    utilisateurRequestDtoBeneficiaire.setPassword("bbbb");
    utilisateurRequestDtoBeneficiaire.setLastname("lastB");
    utilisateurRequestDtoBeneficiaire.setFirstname("firstB");
    utilisateurRequestDtoBeneficiaire.setGender("Male");
    UtilisateurResponseDto utilisateurBeneficiaire = utilisateurService.save(utilisateurRequestDtoBeneficiaire);

    CompteRequestDto compteRequestDtoBeneficiaire = new CompteRequestDto();
    compteRequestDtoBeneficiaire.setNrCompte("010000B000001000");
    compteRequestDtoBeneficiaire.setRib("RIBB");
    compteRequestDtoBeneficiaire.setSolde(BigDecimal.valueOf(200_000.0).setScale(2, RoundingMode.HALF_DOWN));
    compteRequestDtoBeneficiaire.setUtilisateurUsername(utilisateurBeneficiaire.getUsername());
    compteService.save(compteRequestDtoBeneficiaire);

    // Creer un Compte Emetteur avec utilisateur emetteur
    UtilisateurRequestDto utilisateurRequestDtoEmetteur = new UtilisateurRequestDto();
    utilisateurRequestDtoEmetteur.setUsername("userE");
    utilisateurRequestDtoEmetteur.setPassword("eeee");
    utilisateurRequestDtoEmetteur.setLastname("lastE");
    utilisateurRequestDtoEmetteur.setFirstname("firstE");
    utilisateurRequestDtoEmetteur.setGender("Female");
    UtilisateurResponseDto utilisateurEmetteur = utilisateurService.save(utilisateurRequestDtoEmetteur);

    CompteRequestDto compteRequestDtoEmetteur = new CompteRequestDto();
    compteRequestDtoEmetteur.setNrCompte("010000E025001000");
    compteRequestDtoEmetteur.setRib("RIBE");
    compteRequestDtoEmetteur.setSolde(BigDecimal.valueOf(140_000).setScale(2, RoundingMode.HALF_DOWN));
    compteRequestDtoEmetteur.setUtilisateurUsername(utilisateurEmetteur.getUsername());
    compteService.save(compteRequestDtoEmetteur);

    // creer un transfer
    TransferDto transferDto = new TransferDto();
    transferDto.setMotif("TRANSFERT D'ARGENT");
    transferDto.setMontant(BigDecimal.valueOf(10_000));
    transferDto.setDate(new Date());
    transferDto.setNrCompteEmetteur("010000E025001000");
    transferDto.setNrCompteBeneficiaire("010000B000001000");

    transferService.createTransaction(transferDto);

    CompteResponseDto compteEmetteur = compteService.getCompte("010000E025001000");
    CompteResponseDto compteBeneficiaire = compteService.getCompte("010000B000001000");

    // THEN

    // soldEmetteur = oldSoldEmetteur - transferredMontant
    // soldEmetteur = 140_000 - 10_000 = 130_000
    assertThat(compteRequestDtoEmetteur.getSolde().subtract(transferDto.getMontant()))
            .isEqualByComparingTo(compteEmetteur.getSolde());

    // soldBeneficiaire = oldSoldBeneficiaire + transferredMontant
    // soldBeneficiaire = 200_000.0 + 10_000 = 210_000

    assertThat(compteRequestDtoBeneficiaire.getSolde().add(transferDto.getMontant()) )
            .isEqualByComparingTo(compteBeneficiaire.getSolde());



  }


}
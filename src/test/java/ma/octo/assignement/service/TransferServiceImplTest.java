package ma.octo.assignement.service;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.dto.operationDto.TransferDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.interfaces.CompteService;
import ma.octo.assignement.service.interfaces.TransferService;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class TransferServiceImplTest {

  // les soldes initiales
  public static final int BENEFICIAIRE_SOLD = 200_000;
  public static final int EMETTEUR_SOLD = 140_000;

  // montant du transfert
  public static final int MONTANT_TRANSFER = 10_000;

  // soldes apres transaction
  public static final int EXPECTED_EMETTEUR_SOLD = 130_000;
  public static final int EXPECTED_BENEFICIAIRE_SOLD = 210_000;


  private final CompteService compteService;
  private final UtilisateurService utilisateurService;
  private final TransferService transferService;

  @Autowired
  public TransferServiceImplTest(CompteService compteService, UtilisateurService utilisateurService, TransferService transferService) {
    this.compteService = compteService;
    this.utilisateurService = utilisateurService;
    this.transferService = transferService;
  }

  @Test
  public void testSavingValidTransfer() throws TransactionException, CompteNonExistantException, SoldeDisponibleInsuffisantException {

    // Creer un Compte Beneficiaire avec utilisateur beneficiaire
    UtilisateurRequestDto utilisateurBeneficiaire = UtilisateurRequestDto.builder()
            .username("userB")
            .password("bbbb")
            .firstname("firstB")
            .lastname("lastB")
            .gender("Male")
            .build();
    utilisateurService.save(utilisateurBeneficiaire);

    CompteRequestDto compteRequestBeneficiaire = CompteRequestDto.builder()
            .numeroCompte("010000B000001000")
            .rib("RIBB")
            .solde(BigDecimal.valueOf(BENEFICIAIRE_SOLD).setScale(2, RoundingMode.FLOOR))
            .utilisateurUsername("userB")
            .build();
    compteService.saveCompte(compteRequestBeneficiaire);

    // Creer un Compte Emetteur avec utilisateur emetteur
    UtilisateurRequestDto utilisateurEmetteur = UtilisateurRequestDto.builder()
            .username("userE")
            .password("eeee")
            .firstname("firstE")
            .lastname("lastE")
            .gender("Female")
            .build();
     utilisateurService.save(utilisateurEmetteur);

    CompteRequestDto compteRequestEmetteur = CompteRequestDto.builder()
            .numeroCompte("010000E025001000")
            .rib("RIBE")
            .solde(BigDecimal.valueOf(EMETTEUR_SOLD).setScale(2, RoundingMode.FLOOR))
            .utilisateurUsername("userE")
            .build();
    compteService.saveCompte(compteRequestEmetteur);

    // creer un transfer
    TransferDto transferDto = new TransferDto();
    transferDto.setMotif("TRANSFERT D'ARGENT");
    transferDto.setMontant(BigDecimal.valueOf(MONTANT_TRANSFER));
    transferDto.setDate(new Date());
    transferDto.setNrCompteEmetteur("010000E025001000");
    transferDto.setNrCompteBeneficiaire("010000B000001000");
    transferService.createTransaction(transferDto);

    CompteResponseDto compteEmetteur = compteService.getCompte("010000E025001000");
    CompteResponseDto compteBeneficiaire = compteService.getCompte("010000B000001000");


    // soldEmetteur = oldSoldEmetteur - transferredMontant
    // soldEmetteur = 140_000 - 10_000 = 130_000
    assertThat(compteEmetteur.getSolde())
            .isEqualByComparingTo(BigDecimal.valueOf(EXPECTED_EMETTEUR_SOLD));

    // soldBeneficiaire = oldSoldBeneficiaire + transferredMontant
    // soldBeneficiaire = 200_000.0 + 10_000 = 210_000

    assertThat(compteBeneficiaire.getSolde())
            .isEqualByComparingTo(BigDecimal.valueOf(EXPECTED_BENEFICIAIRE_SOLD));


  }


}
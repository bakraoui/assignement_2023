package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.operation.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;

public class DepositMapper {

    private static DepositDto depositDto;

    public static DepositDto map(MoneyDeposit moneyDeposit) {

        depositDto = new DepositDto();
        depositDto.setNrCompteBeneficiaire(moneyDeposit.getCompteBeneficiaire().getNrCompte());
        depositDto.setDate(moneyDeposit.getDateExecution());
        depositDto.setMotif(moneyDeposit.getMotif());
        depositDto.setMontant(moneyDeposit.getMontant());
        return depositDto;

    }

    public static MoneyDeposit toMoneyDeposit(DepositDto depositDto, Compte beneficiaiare) {
        MoneyDeposit moneyDeposit = new MoneyDeposit();

        moneyDeposit.setMotif(depositDto.getMotif());
        moneyDeposit.setMontant(depositDto.getMontant());
        moneyDeposit.setCompteBeneficiaire(beneficiaiare);
        moneyDeposit.setDateExecution(depositDto.getDate());
        moneyDeposit.setNom_prenom_emetteur(depositDto.getNom_prenom_emetteur());

        return moneyDeposit;
    }

}

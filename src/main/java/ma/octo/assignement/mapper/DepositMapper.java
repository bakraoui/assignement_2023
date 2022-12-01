package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.operation.MoneyDeposit;
import ma.octo.assignement.dto.operationdto.DepositDto;

public class DepositMapper {

    private static DepositDto depositDto;

    public static DepositDto mapToDepositDto(MoneyDeposit moneyDeposit) {

        depositDto = new DepositDto();
        depositDto.setNrCompteBeneficiaire(moneyDeposit.getCompteBeneficiaire().getNumeroCompte());
        depositDto.setDate(moneyDeposit.getDateExecution());
        depositDto.setMotif(moneyDeposit.getMotif());
        depositDto.setMontant(moneyDeposit.getMontant());
        depositDto.setRib(moneyDeposit.getCompteBeneficiaire().getRib());
        return depositDto;

    }

    public static MoneyDeposit mapToMoneyDeposit(DepositDto depositDto, Compte beneficiaiare) {
        MoneyDeposit moneyDeposit = new MoneyDeposit();

        moneyDeposit.setMotif(depositDto.getMotif());
        moneyDeposit.setMontant(depositDto.getMontant());
        moneyDeposit.setCompteBeneficiaire(beneficiaiare);
        moneyDeposit.setDateExecution(depositDto.getDate());
        moneyDeposit.setNomPrenomEmetteur(depositDto.getNomPrenomEmetteur());

        return moneyDeposit;
    }

}

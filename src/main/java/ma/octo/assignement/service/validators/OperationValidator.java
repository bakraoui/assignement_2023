package ma.octo.assignement.service.validators;

import ma.octo.assignement.dto.OperationDto;

import java.math.BigDecimal;
import java.util.function.Function;

import static ma.octo.assignement.service.validators.OperationValidator.ValidationResult;
import static ma.octo.assignement.service.validators.OperationValidator.ValidationResult.*;

import static ma.octo.assignement.service.utils.Constant.MONTANT_MAXIMAL;

public interface OperationValidator extends Function<OperationDto, ValidationResult> {

    enum ValidationResult {
        NUMERO_COMPTE_NON_VALIDE("numero de compte saisi non valide"),
        MONTANT_VIDE("Montant vide"),
        MONTANT_MINIMAL_NON_ATTEIND("Montant minimal de transfer non atteint"),
        MONTANT_MAXIMAL_DEPASSE("Montant maximal de transfer dépassé"),
        MOTIF_VIDE("Motif vide"),
        SUCCES("les champs sont valides"),
        SOLDE_INSUFFISANT("Solde insuffisant pour l'utilisateur");

        private String type;
        ValidationResult(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    static OperationValidator isNumeroCompteNonValide() {
        return operationDto ->
                operationDto.getNrCompteBeneficiaire() == null
                        || operationDto.getNrCompteBeneficiaire().equals("")
                        || !operationDto.getNrCompteBeneficiaire().matches("[!-_+=\\.\\;!@]")?
                        NUMERO_COMPTE_NON_VALIDE : SUCCES;
    }

    static OperationValidator isMontantNonVide() {
        return operationDto ->  operationDto.getMontant().intValue() <= 0 ? MONTANT_VIDE : SUCCES;
    }

    static OperationValidator isMontantNonAtteind() {
        return operationDto -> operationDto.getMontant().intValue() < 10 ? MONTANT_MINIMAL_NON_ATTEIND : SUCCES;
    }

    static OperationValidator isMontantDepasse() {
        return operationDto -> operationDto.getMontant().intValue() > MONTANT_MAXIMAL ? MONTANT_MAXIMAL_DEPASSE : SUCCES;
    }

    static OperationValidator isMontantSuffisant(BigDecimal solde) {
        return operationDto -> solde.intValue() < operationDto.getMontant().intValue() ? SOLDE_INSUFFISANT : SUCCES ;
    }

    static OperationValidator isMotifValid() {
        return operationDto -> operationDto.getMotif() == null || operationDto.getMotif().length() == 0 ? MOTIF_VIDE : SUCCES;
    }




    default OperationValidator and (OperationValidator other) {
        return operationDto -> {
            ValidationResult result = this.apply(operationDto);
            return result.equals(SUCCES) ? other.apply(operationDto) : result;
        };
    }

}

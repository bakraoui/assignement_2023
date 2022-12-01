package ma.octo.assignement.service.validators;

import ma.octo.assignement.dto.operationdto.OperationDto;
import ma.octo.assignement.service.utils.OperationValidationResult;
import static ma.octo.assignement.service.utils.OperationValidationResult.*;

import java.math.BigDecimal;
import java.util.function.Function;

import static ma.octo.assignement.service.utils.Constant.MONTANT_MINIMAL;

import static ma.octo.assignement.service.utils.Constant.MONTANT_MAXIMAL;

public interface OperationValidator extends Function<OperationDto, OperationValidationResult> {


    static OperationValidator isNumeroCompteNonValide() {
        return operationDto ->
                operationDto.getNrCompteBeneficiaire() == null
                        || operationDto.getNrCompteBeneficiaire().equals("")
                        || operationDto.getNrCompteBeneficiaire().matches("[!-_+=\\.\\;!@]") ?
                        NUMERO_COMPTE_NON_VALIDE : SUCCES;
    }

    static OperationValidator isMontantNonVide() {
        return operationDto -> operationDto.getMontant()==null
                || operationDto.getMontant().intValue() <= 0 ? MONTANT_VIDE : SUCCES;
    }

    static OperationValidator isMontantNonAtteind() {
        return operationDto -> operationDto.getMontant().intValue() < MONTANT_MINIMAL ? MONTANT_MINIMAL_NON_ATTEIND : SUCCES;
    }

    static OperationValidator isMontantDepasse() {
        return operationDto -> operationDto.getMontant().intValue() > MONTANT_MAXIMAL ? MONTANT_MAXIMAL_DEPASSE : SUCCES;
    }

    static OperationValidator isMontantSuffisant(BigDecimal solde) {
        return operationDto -> solde.intValue() < operationDto.getMontant().intValue() ? SOLDE_INSUFFISANT : SUCCES ;
    }

    static OperationValidator isMotifValid() {
        return operationDto -> operationDto.getMotif() == null
                || operationDto.getMotif().length() == 0 ? MOTIF_VIDE : SUCCES;
    }

    default OperationValidator and (OperationValidator other) {
        return operationDto -> {
            OperationValidationResult result = this.apply(operationDto);
            return result.equals(SUCCES) ? other.apply(operationDto) : result;
        };
    }

}

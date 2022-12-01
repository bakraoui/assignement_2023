package ma.octo.assignement.service.validators;

import ma.octo.assignement.dto.comptedto.CompteRequestDto;
import ma.octo.assignement.service.utils.CompteValidationResult;
import static ma.octo.assignement.service.utils.CompteValidationResult.*;

import java.util.function.Function;
public interface CompteValidator extends Function<CompteRequestDto, CompteValidationResult> {

    static CompteValidator isNumeroCompteValid(){
        return compteDto -> compteDto.getNumeroCompte() == null
                || compteDto.getNumeroCompte().length() == 0 ?
                NB_COMPTE_INVALIDE : SUCCES;
    }

    static CompteValidator isSoldValid() {
        return compteDto -> compteDto.getSolde().intValue() < 0 ? SOLDE_INVALIDE : SUCCES;
    }

    static CompteValidator isRibValid() {
        return compteDto -> compteDto.getRib()==null || compteDto.getRib().length() == 0 ?
                RIB_INVALIDE : SUCCES;
    }

    default CompteValidator and (CompteValidator other) {
        return compteDto -> {
            CompteValidationResult result = this.apply(compteDto);
            return result.equals(SUCCES) ? other.apply(compteDto) : result;
        };
    }
}

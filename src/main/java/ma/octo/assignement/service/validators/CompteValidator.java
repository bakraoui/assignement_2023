package ma.octo.assignement.service.validators;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;

import java.util.function.Function;
import static ma.octo.assignement.service.validators.CompteValidator.*;
import static ma.octo.assignement.service.validators.CompteValidator.ValidationResult.*;
public interface CompteValidator extends Function<CompteRequestDto, ValidationResult> {

    enum ValidationResult {
        NB_COMPTE_INVALIDE("Numero de compte est invalide"),
        RIB_INVALIDE("numero RIB est invalide"),
        SOLDE_INVALIDE("entrer un solde valide"),
        SUCCES("Tous les champs sont valides");

        private String message;
        ValidationResult(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }


    static CompteValidator isNbCompteValid(){
        return compteDto -> compteDto.getNumeroCompte()==null || compteDto.getNumeroCompte().length() == 0 ?
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
            ValidationResult result = this.apply(compteDto);

            return result.equals(SUCCES) ? other.apply(compteDto) : result;
        };
    }
}

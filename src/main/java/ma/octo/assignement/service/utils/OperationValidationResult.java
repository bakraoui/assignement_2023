package ma.octo.assignement.service.utils;

import static ma.octo.assignement.service.utils.Constant.MONTANT_MAXIMAL;

public enum OperationValidationResult {

        NUMERO_COMPTE_NON_VALIDE("numero de compte saisi est non valide"),
        MONTANT_VIDE("champs montant est obligatoire"),
        MONTANT_MINIMAL_NON_ATTEIND("Montant minimal de transfer non atteint (au moin 10dh)"),
        MONTANT_MAXIMAL_DEPASSE("Montant maximal de transfer dépassé ( "+ MONTANT_MAXIMAL +" )"),
        MOTIF_VIDE("Le champs motif est obligatoire"),
        SUCCES("les champs sont valides"),
        SOLDE_INSUFFISANT("votre Solde est insuffisant pour completer ce transfert");

        private String type;
        OperationValidationResult(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

}

package ma.octo.assignement.service.utils;

public enum CompteValidationResult {

    NB_COMPTE_INVALIDE("Numero de compte est invalide"),
    RIB_INVALIDE("numero RIB est invalide"),
    SOLDE_INVALIDE("entrer un solde valide"),
    SUCCES("Tous les champs sont valides");

    private String message;
    CompteValidationResult(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

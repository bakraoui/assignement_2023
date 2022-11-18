package ma.octo.assignement.web.common;

import ma.octo.assignement.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandelingController {

    @ExceptionHandler(SoldeDisponibleInsuffisantException.class)
    public ResponseEntity<String> handleSoldeDisponibleInsuffisantException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Pas de solde pas de transfer", null, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(CompteNonExistantException.class)
    public ResponseEntity<String> handleCompteNonExistantException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Compte introuvable", null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CompteExistantException.class)
    public ResponseEntity<String> handleCompteExistantException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<String> handleTransactionException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TransferNonExistantException.class)
    public ResponseEntity<String> handleTransferNonExistantException(Exception ex, WebRequest r){
        return new ResponseEntity<>("Transfer non existant", null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompteValidationException.class)
    public ResponseEntity<String> handleCompteValidationException(Exception ex, WebRequest r){
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UtilisateurExistantException.class)
    public ResponseEntity<String> handleUtilisateurExistantException(Exception ex, WebRequest r){
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UtilisateurNonExistantException.class)
    public ResponseEntity<String> handleUtilisateurNonExistantException(Exception ex, WebRequest r){
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }
}

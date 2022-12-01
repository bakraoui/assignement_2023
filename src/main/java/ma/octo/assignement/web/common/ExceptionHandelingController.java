package ma.octo.assignement.web.common;

import ma.octo.assignement.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandelingController {

    @ExceptionHandler(SoldeDisponibleInsuffisantException.class)
    public ResponseEntity<ErrorApi> handleSoldeDisponibleInsuffisantException(
            SoldeDisponibleInsuffisantException exception, WebRequest request) {

        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.CONFLICT,
                request.getContextPath()
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CompteNonExistantException.class)
    public ResponseEntity<ErrorApi> handleCompteNonExistantException(
            CompteNonExistantException exception, WebRequest request) {

        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.NOT_FOUND,
                request.getContextPath()
        );

        return new ResponseEntity<>(errorApi, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompteExistantException.class)
    public ResponseEntity<ErrorApi> handleCompteExistantException(CompteExistantException exception, WebRequest request) {
        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.CONFLICT,
                request.getContextPath()
        );
        return new ResponseEntity<>(errorApi,  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorApi> handleTransactionException(TransactionException exception, WebRequest request) {
        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.CONFLICT,
                request.getContextPath()
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TransferNonExistantException.class)
    public ResponseEntity<ErrorApi> handleTransferNonExistantException(TransferNonExistantException exception, WebRequest request){
        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.NOT_FOUND,
                request.getContextPath()
        );
        return new ResponseEntity<>(errorApi, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompteValidationException.class)
    public ResponseEntity<ErrorApi> handleCompteValidationException(CompteValidationException exception, WebRequest request){
        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.CONFLICT,
                request.getContextPath()
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UtilisateurExistantException.class)
    public ResponseEntity<ErrorApi> handleUtilisateurExistantException(Exception exception, HttpServletRequest request){
        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.CONFLICT,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UtilisateurNonExistantException.class)
    public ResponseEntity<ErrorApi> handleUtilisateurNonExistantException(UtilisateurNonExistantException exception, WebRequest request){
        ErrorApi errorApi = new ErrorApi(
                exception.getMessage(),
                new Date(),
                HttpStatus.NOT_FOUND,
                request.getContextPath()
        );

        return new ResponseEntity<>(errorApi, HttpStatus.NOT_FOUND);
    }

}

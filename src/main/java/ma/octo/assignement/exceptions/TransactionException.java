package ma.octo.assignement.exceptions;

public class TransactionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TransactionException() {
  }

  public TransactionException(String message) {
    super(message);
  }
}

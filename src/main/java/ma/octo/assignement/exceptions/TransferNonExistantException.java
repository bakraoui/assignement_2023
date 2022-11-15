package ma.octo.assignement.exceptions;

import java.util.function.Supplier;

public class TransferNonExistantException extends RuntimeException {

    public TransferNonExistantException() {
    }

    public TransferNonExistantException(String message) {
        super(message);
    }
}

package common.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(final String message) {
        super(message);
    }

    public AccountAlreadyExistsException(final Throwable throwable) {
        super(throwable);
    }
}


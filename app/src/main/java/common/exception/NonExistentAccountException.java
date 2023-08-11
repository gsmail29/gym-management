package common.exception;

public class NonExistentAccountException extends RuntimeException {
    
    public NonExistentAccountException(final String message) {
        super(message);
    }

    public NonExistentAccountException(final Throwable throwable) {
        super(throwable);
    }
    
}

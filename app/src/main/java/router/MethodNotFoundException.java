package router;

public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(final String errorString) {
        super(errorString);
    }
}

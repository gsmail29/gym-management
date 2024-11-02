package router;

public class MethodNotFoundException extends BaseException {
    public MethodNotFoundException(final String errorCode,
                                   final String errorMsg) {
        super(errorCode, errorMsg);
    }
}

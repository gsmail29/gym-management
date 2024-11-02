package router;

public class BaseException extends RuntimeException {
    private String errorCode;

    public BaseException(final String errorCode, final String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

}

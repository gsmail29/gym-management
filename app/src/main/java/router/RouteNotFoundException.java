package router;

public class RouteNotFoundException extends BaseException {

    public RouteNotFoundException(final String errorCode, final String errorMsg) {

        super(errorCode, errorMsg);
    }
}

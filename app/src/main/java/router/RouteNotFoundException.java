package router;

public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException(final String errorString) {
        super(errorString);
    }
}

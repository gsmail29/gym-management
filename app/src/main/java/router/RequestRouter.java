package router;

import accountmanager.activity.CreateAccountActivity;
import accountmanager.activity.DeleteAccountActivity;
import accountmanager.activity.GetAccountActivity;
import accountmanager.activity.UpdateAccountActivity;
import common.activity.Activity;
import common.constant.ErrorCodes;
import common.model.ApiRequest;
import common.model.ApiResponse;
import common.model.RequestRoutingDetails;
import common.model.RequestRoutingParameters;
import eventing.DeleteEventActivity;
import eventing.GetEventActivity;
import eventing.UpdateEventActivity;
import reporter.DeleteReportActivity;
import reporter.GetReportActivity;
import reporter.UpdateReportActivity;
import scheduler.lambda.activity.DeleteScheduleActivity;
import scheduler.lambda.activity.GetScheduleAcvitity;
import scheduler.lambda.activity.UpdateScheduleActivity;

import static common.constant.ApiConstants.ERROR_CODE_HEADER_NAME;
import static common.constant.ApiConstants.ERROR_MESSAGE_HEADER_NAME;
import static common.constant.ErrorCodes.INTERNAL_SERVICE_ERROR;
import static common.constant.ErrorCodes.REQUEST_PARAMETER_VALIDATION_ERROR;
import static common.constant.ErrorCodes.UNSUPPORTED_HTTP_METHOD;
import static common.constant.ErrorCodes.UNSUPPORTED_RESOURCE_ERROR;

public class RequestRouter {

    private RequestRoutingDetails findRequestRoute(final RequestRoutingParameters params) {
        Activity api = null;
        switch(params.getResource().toLowerCase()) {
            case "account":
                switch(params.getHttpMethod().toLowerCase()) {
                    case "get":
                        api = new GetAccountActivity();
                        break;
                    case "put":
                        api = new CreateAccountActivity();
                        break;
                    case "delete":
                        api = new DeleteAccountActivity();
                        break;
                    case "post":
                        api = new UpdateAccountActivity();
                        break;
                    default:
                        throw new MethodNotFoundException(UNSUPPORTED_HTTP_METHOD, "The requested method is not supported");
                }
                break;
            case "event":
                switch(params.getHttpMethod().toLowerCase()) {
                    case "get":
                        api = new GetEventActivity();
                        break;
                    case "put":
                        api = new UpdateEventActivity();
                        break;
                    case "delete":
                        api = new DeleteEventActivity();
                        break;
                    case "post":
                        api = new UpdateEventActivity();
                        break;
                    default:
                        throw new MethodNotFoundException(UNSUPPORTED_HTTP_METHOD, "The requested method is not supported");
                }
                break;
            case "schedule":
                switch(params.getHttpMethod().toLowerCase()) {
                    case "get":
                        api = new GetScheduleAcvitity();
                        break;
                    case "put":
                        api = new UpdateScheduleActivity();
                        break;
                    case "delete":
                        api = new DeleteScheduleActivity();
                        break;
                    case "post":
                        api = new UpdateScheduleActivity();
                        break;
                    default:
                        throw new MethodNotFoundException(UNSUPPORTED_HTTP_METHOD, "The requested method is not supported");
                }
                break;
            case "report":
                switch(params.getHttpMethod().toLowerCase()) {
                    case "get":
                        api = new GetReportActivity();
                        break;
                    case "put":
                    case "post":
                        api = new UpdateReportActivity();
                        break;
                    case "delete":
                        api = new DeleteReportActivity();
                        break;
                    default:
                        throw new MethodNotFoundException(UNSUPPORTED_HTTP_METHOD, "The requested method is not supported");
                }
                break;
            default:
                throw new RouteNotFoundException(UNSUPPORTED_RESOURCE_ERROR, "The requested resource doesn't have any mapped api");

        }

        return RequestRoutingDetails.builder().apiToCall(api).build();
    }

    public ApiResponse routeRequest(final ApiRequest apiRequest,
                                    final RequestRoutingParameters routingParameters) {
        try {
        final RequestRoutingDetails routingDetails = findRequestRoute(routingParameters);
        routingDetails.getApiToCall().validate(apiRequest);
        return routingDetails.getApiToCall().enact(apiRequest);
        } catch (final RouteNotFoundException  routeNotFoundException) {
            return getApiResponseForException(400, routeNotFoundException);
        } catch (final IllegalArgumentException illegalArgumentException) {
            return getApiResponseForException(400, new BaseException(REQUEST_PARAMETER_VALIDATION_ERROR, illegalArgumentException.getMessage()));
        }
        catch (final Exception exception) {
            exception.printStackTrace(System.out);
            return getApiResponseForException(500, new BaseException(INTERNAL_SERVICE_ERROR, exception.getMessage()));
        }
    }

    private ApiResponse getApiResponseForException(final int statusCode,
                                                   final BaseException exception) {
        final ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(statusCode);
        apiResponse.addResponseHeader(ERROR_MESSAGE_HEADER_NAME, exception.getMessage());
        apiResponse.addResponseHeader(ERROR_CODE_HEADER_NAME, exception.getErrorCode());
        return apiResponse;
    }

}

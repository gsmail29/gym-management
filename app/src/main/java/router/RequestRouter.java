package router;

import accountmanager.activity.CreateAccountActivity;
import accountmanager.activity.DeleteAccountActivity;
import accountmanager.activity.GetAccountActivity;
import accountmanager.activity.UpdateAccountActivity;
import common.activity.Activity;
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
                        throw new MethodNotFoundException("The requested method is not supported");
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
                        throw new MethodNotFoundException("The requested method is not supported");
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
                        throw new MethodNotFoundException("The requested method is not supported");
                }
                break;
            case "report":
                switch(params.getHttpMethod().toLowerCase()) {
                    case "get":
                        api = new GetReportActivity();
                        break;
                    case "put":
                        api = new UpdateReportActivity();
                        break;
                    case "delete":
                        api = new DeleteReportActivity();
                        break;
                    case "post":
                        api = new UpdateReportActivity();
                        break;
                    default:
                        throw new MethodNotFoundException("The requested method is not supported");
                }
                break;
            default:
                throw new RouteNotFoundException("The requested resource doesn't have any mapped api");

        }

        return RequestRoutingDetails.builder().apiToCall(api).build();
    }

    public ApiResponse routeRequest(final ApiRequest apiRequest,
                                    final RequestRoutingParameters routingParameters) {
        final RequestRoutingDetails routingDetails = findRequestRoute(routingParameters);
        routingDetails.getApiToCall().validate(apiRequest);
        return routingDetails.getApiToCall().enact(apiRequest);
    }

}

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package scheduler.lambda;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import common.model.ApiRequest;
import common.model.ApiResponse;
import common.model.RequestRoutingParameters;
import router.RequestRouter;

import java.lang.reflect.MalformedParametersException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import router.RouteNotFoundException;

public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Gson json = new Gson();
    private static final RequestRouter requestRouter = new RequestRouter();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        final LambdaLogger logger = context.getLogger();
        logger.log("Event received:" + input);
        final ApiResponse apiResponse;
        apiResponse = requestRouter.routeRequest(getApiRequestParamsFromApiGatewayRequest(input, context),
                getRoutingParamsFromApiGatewayRequest(input, context));
        return getApiGatewayResponseFromApiResponse(apiResponse);
    }

    private RequestRoutingParameters getRoutingParamsFromApiGatewayRequest(final APIGatewayProxyRequestEvent requestEvent,
                                                                           final Context context) {
        String resourcePath = requestEvent.getPath();
        if(resourcePath.startsWith("/")) {
            resourcePath = StringUtils.stripStart(resourcePath, "/");
        }
        final RequestRoutingParameters routingParameters =  RequestRoutingParameters.builder()
            .httpMethod(requestEvent.getHttpMethod())
            .resource(resourcePath)
            .build();
        context.getLogger().log("Request routing parameters:" + routingParameters);
        return routingParameters;
    }

    private ApiRequest getApiRequestParamsFromApiGatewayRequest(final APIGatewayProxyRequestEvent requestEvent,
                                                                final Context context) {

        final Type requestMapType = new TypeToken<Map<String, String>>() {}.getType();
        final Map<String, String> requestParamMap = requestEvent.getHttpMethod().equalsIgnoreCase("Get") ? requestEvent.getQueryStringParameters() : json.fromJson(requestEvent.getBody(), requestMapType);
        final ApiRequest apiRequest = new ApiRequest();
        if (requestParamMap != null) {
            apiRequest.addRequestHeaders(requestParamMap);
        }
        context.getLogger().log("Api request:" + apiRequest);
        return apiRequest;
    }

    private APIGatewayProxyResponseEvent getApiGatewayResponseFromApiResponse(final ApiResponse apiResponse) {
        return new APIGatewayProxyResponseEvent()
            .withStatusCode(apiResponse.getStatus())
            .withBody(apiResponse.getResponseBody())
            .withIsBase64Encoded(false)
            .withHeaders(apiResponse.getResponseHeadersMap());
    }
}

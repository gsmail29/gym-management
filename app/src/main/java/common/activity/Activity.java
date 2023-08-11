package common.activity;

import common.model.ApiRequest;
import common.model.ApiResponse;

public interface Activity {

    /*
     * Function to validate the request parameters. Implementors are expected to throw
     * exceptions in case the validation fails. This will be called by request router
     * before calling enact method.
     */
    void validate(final ApiRequest apiRequest);

    /*
     * This is where the API logic resides. This will be called by request router
     * to generate response for an API.
     */
    ApiResponse enact(final ApiRequest apiRequest);
}

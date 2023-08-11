package scheduler.lambda.activity;

import common.activity.Activity;
import common.constant.FieldConstants;
import common.model.ApiRequest;
import common.model.ApiResponse;
import scheduler.lambda.component.ScheduleCreatorComponent;

public class CreateScheduleActivity implements Activity{
    
    private final ScheduleCreatorComponent scheduleCreatorComponent;

    public CreateScheduleActivity() {
        scheduleCreatorComponent = new ScheduleCreatorComponent();
    }

    private String getAccountIdFromApiRequest(final ApiRequest apiRequest) {
        return apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_ID);
    }

    @Override
    public void validate(ApiRequest apiRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public ApiResponse enact(ApiRequest apiRequest) {
        scheduleCreatorComponent.createSchedule(getAccountIdFromApiRequest(apiRequest));
        final ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        return apiResponse;
    }



}

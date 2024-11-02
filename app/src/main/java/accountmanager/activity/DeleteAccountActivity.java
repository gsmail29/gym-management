package accountmanager.activity;

import accountmanager.component.AccountComponent;
import com.google.common.base.Preconditions;
import common.activity.Activity;
import common.constant.FieldConstants;
import common.exception.NonExistentAccountException;
import common.model.ApiRequest;
import common.model.ApiResponse;
import common.pojo.Account;
import org.checkerframework.checker.units.qual.A;

import static common.constant.ApiConstants.ERROR_CODE_HEADER_NAME;
import static common.constant.ApiConstants.ERROR_MESSAGE_HEADER_NAME;
import static common.constant.ErrorCodes.ACCOUNT_NOT_EXIST;

public class DeleteAccountActivity implements Activity {
    private AccountComponent accountComponent;

    public DeleteAccountActivity() {
        this.accountComponent = new AccountComponent();
    }

    @Override
    public void validate(ApiRequest apiRequest) {
        // the request should contain accountId, name and accountType
        Preconditions.checkArgument(apiRequest.getRequestHeadersMap().containsKey(FieldConstants.ACCOUNT_ID),
                "Delete account request should contain accountId");
    }

    @Override
    public ApiResponse enact(ApiRequest apiRequest) {
        final ApiResponse apiResponse = new ApiResponse();
        try {
            accountComponent.deleteAccount(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_ID));
            apiResponse.setStatus(200);
            return apiResponse;
        } catch (final NonExistentAccountException nonExistentAccountException) {
            apiResponse.setStatus(400);
            apiResponse.addResponseHeader(ERROR_CODE_HEADER_NAME, ACCOUNT_NOT_EXIST);
            apiResponse.addResponseHeader(ERROR_MESSAGE_HEADER_NAME, nonExistentAccountException.getMessage());
            return apiResponse;
        }
    }
}

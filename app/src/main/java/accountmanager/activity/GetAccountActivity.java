package accountmanager.activity;

import accountmanager.component.AccountComponent;
import com.google.common.base.Preconditions;
import common.activity.Activity;
import common.constant.FieldConstants;
import common.enums.AccountTypeEnum;
import common.exception.AccountAlreadyExistsException;
import common.exception.NonExistentAccountException;
import common.model.ApiRequest;
import common.model.ApiResponse;
import common.pojo.Account;

import static common.constant.ApiConstants.ERROR_CODE_HEADER_NAME;
import static common.constant.ApiConstants.ERROR_MESSAGE_HEADER_NAME;
import static common.constant.ErrorCodes.ACCOUNT_ALREADY_EXISTS;
import static common.constant.ErrorCodes.ACCOUNT_NOT_EXIST;

public class GetAccountActivity implements Activity {

    private AccountComponent accountComponent;

    public GetAccountActivity() {
        this.accountComponent = new AccountComponent();
    }

    @Override
    public void validate(ApiRequest apiRequest) {
        // the request should contain accountId, name and accountType
        Preconditions.checkArgument(apiRequest.getRequestHeadersMap().containsKey(FieldConstants.ACCOUNT_ID),
                "Get account request should contain accountId");
    }

    @Override
    public ApiResponse enact(ApiRequest apiRequest) {
        final ApiResponse apiResponse = new ApiResponse();
        try {
            final Account accountDetails =  accountComponent.getAccount(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_ID));
            apiResponse.setStatus(200);
            apiResponse.addResponseHeader(FieldConstants.ACCOUNT_TYPE, accountDetails.getAccountType().accountType);
            apiResponse.addResponseHeader(FieldConstants.ACCOUNT_ID, accountDetails.getAccountId());
            apiResponse.addResponseHeader(FieldConstants.ACCOUNT_NAME, accountDetails.getName());
            return apiResponse;
        } catch (final NonExistentAccountException nonExistentAccountException) {
            apiResponse.setStatus(400);
            apiResponse.addResponseHeader(ERROR_CODE_HEADER_NAME, ACCOUNT_NOT_EXIST);
            apiResponse.addResponseHeader(ERROR_MESSAGE_HEADER_NAME, nonExistentAccountException.getMessage());
            return apiResponse;
        }
    }

}

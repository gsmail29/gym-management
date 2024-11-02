package accountmanager.activity;
import com.google.common.base.Preconditions;

import accountmanager.component.AccountCreationComponent;
import common.activity.Activity;
import common.constant.FieldConstants;
import common.enums.AccountTypeEnum;
import common.exception.AccountAlreadyExistsException;
import common.model.ApiRequest;
import common.model.ApiResponse;
import common.pojo.Account;

import static common.constant.ApiConstants.ERROR_CODE_HEADER_NAME;
import static common.constant.ApiConstants.ERROR_MESSAGE_HEADER_NAME;
import static common.constant.ErrorCodes.ACCOUNT_ALREADY_EXISTS;

public class CreateAccountActivity implements Activity {
    private AccountCreationComponent accountCreationComponent;

    public CreateAccountActivity() {
        accountCreationComponent = new AccountCreationComponent();
    }

    @Override
    public void validate(ApiRequest apiRequest) {
        // the request should contain accountId, name and accountType
        Preconditions.checkArgument(apiRequest.getRequestHeadersMap().containsKey(FieldConstants.ACCOUNT_ID),
            "Create account request should contain accountId");
        Preconditions.checkArgument(apiRequest.getRequestHeadersMap().containsKey(FieldConstants.ACCOUNT_NAME),
            "Create account request should contain account name");
        Preconditions.checkArgument(apiRequest.getRequestHeadersMap().containsKey(FieldConstants.ACCOUNT_TYPE),
            "Create account request should contain account type");
        AccountTypeEnum.valueOf(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_TYPE));
    }

    @Override
    public ApiResponse enact(ApiRequest apiRequest) {
        final ApiResponse apiResponse = new ApiResponse();
        try {
            accountCreationComponent.createAccount(getAccountDetailsFromApiRequest(apiRequest));
            apiResponse.setStatus(200);
            return apiResponse;
        } catch (final AccountAlreadyExistsException accountAlreadyExistsException) {
            apiResponse.setStatus(400);
            apiResponse.addResponseHeader(ERROR_CODE_HEADER_NAME, ACCOUNT_ALREADY_EXISTS);
            apiResponse.addResponseHeader(ERROR_MESSAGE_HEADER_NAME, accountAlreadyExistsException.getMessage());
            return apiResponse;
        }
    }

    private Account getAccountDetailsFromApiRequest(final ApiRequest apiRequest) {
        return Account.builder()
            .accountId(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_ID))
            .name(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_NAME))
            .accountType(AccountTypeEnum.valueOf(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_TYPE)))
            .build();
    }
}
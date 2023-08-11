package accountmanager.activity;
import com.google.common.base.Preconditions;

import accountmanager.component.AccountCreationComponent;
import common.activity.Activity;
import common.constant.FieldConstants;
import common.enums.AccountTypeEnum;
import common.model.ApiRequest;
import common.model.ApiResponse;
import common.pojo.Account;

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
        Preconditions.checkArgument(apiRequest.getRequestHeadersMap().containsKey(FieldConstants.ACCOUNT_NAME), 
            "Create account request should contain account type");

    }

    @Override
    public ApiResponse enact(ApiRequest apiRequest) {
        accountCreationComponent.createAccount(getAccountDetailsFromApiRequest(apiRequest));
        final ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        return apiResponse;
    }

    private Account getAccountDetailsFromApiRequest(final ApiRequest apiRequest) {
        return Account.builder()
            .accountId(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_ID))
            .name(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_NAME))
            .accountType(AccountTypeEnum.valueOf(apiRequest.getRequestHeadersMap().get(FieldConstants.ACCOUNT_TYPE)))
            .build();
    }

}
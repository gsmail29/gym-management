package accountmanager.component;

import common.accessor.DynamoDbAccessor;
import common.exception.AccountAlreadyExistsException;
import common.pojo.Account;

public class AccountCreationComponent {
    private DynamoDbAccessor dbAccessor;

    public AccountCreationComponent() {
        dbAccessor = new DynamoDbAccessor();
    }

    public void createAccount(final Account account) {
        if(dbAccessor.isAccountExists(account.getAccountId())) {
            throw new AccountAlreadyExistsException("Account with id:" + account.getAccountId() + " already exists!!");
        }
        dbAccessor.writeAccountDetailsIntoDb(account);
    }
}

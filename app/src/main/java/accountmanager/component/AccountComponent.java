package accountmanager.component;

import common.accessor.DynamoDbAccessor;
import common.exception.AccountAlreadyExistsException;
import common.pojo.Account;

public class AccountComponent {
    private DynamoDbAccessor dbAccessor;

    public AccountComponent() {
        dbAccessor = new DynamoDbAccessor();
    }

    public void createAccount(final Account account) {
        if(dbAccessor.isAccountExists(account.getAccountId())) {
            throw new AccountAlreadyExistsException("Account with id:" + account.getAccountId() + " already exists!!");
        }
        dbAccessor.writeAccountDetailsIntoDb(account);
    }

    public Account getAccount(final String accountId) {
        return dbAccessor.readAccountDetailsFromDb(accountId);
    }
}

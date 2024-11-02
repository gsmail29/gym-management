package common.accessor;
import java.util.HashMap;
import java.util.Map;

import common.client.DDbClient;
import common.constant.FieldConstants;
import common.enums.AccountTypeEnum;
import common.exception.NonExistentAccountException;
import common.pojo.Account;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

public class DynamoDbAccessor {

    private DynamoDbClient dynamoDbClient;
    private static String ACCOUNT_DDB_TABLE_NAME = System.getenv("ACCOUNT_INFO_TABLE_NAME");
    private static String ACCOUNT_DDB_TABLE_KEY_NAME = FieldConstants.ACCOUNT_ID;

    public DynamoDbAccessor() {
        dynamoDbClient = DDbClient.getInstance();
    }

    public void writeAccountDetailsIntoDb(final Account account) {
        dynamoDbClient.putItem(getPutItemRequestForAccount(account));
    }

    public Account readAccountDetailsFromDb(final String accountId) {
        final Map<String, AttributeValue> returnedItem = dynamoDbClient.getItem(getItemRequestForAccountId(accountId)).item();
        if(returnedItem == null || returnedItem.size() == 0) {
            throw new NonExistentAccountException("The account with Id:" + accountId + " is not found.");
        }
        return mapDbOutputToAccount(returnedItem);
    }

    public boolean isAccountExists(final String accountId) {
        try {
            readAccountDetailsFromDb(accountId);
            return true;
        } catch (NonExistentAccountException exception) {
            return false;
        }
    }

    public void deleteAccount(final String accountId) {
        dynamoDbClient.deleteItem(getDeleteItemRequestForAccount(accountId));
    }

    private Account mapDbOutputToAccount(final Map<String, AttributeValue> ddbItem) {
        return Account.builder()
            .accountId(ddbItem.get("accountId").s())
            .name(ddbItem.get("accountName").s())
            .accountType(AccountTypeEnum.valueOf(ddbItem.get("accountType").s()))
            .build();
    }

    private GetItemRequest getItemRequestForAccountId(final String accountId) {
        final HashMap<String, AttributeValue> keyHashMap = new HashMap<>();
        keyHashMap.put(ACCOUNT_DDB_TABLE_KEY_NAME, AttributeValue.builder().s(accountId).build());

        return GetItemRequest.builder()
            .key(keyHashMap)
            .tableName(ACCOUNT_DDB_TABLE_NAME)
            .build();
    }

    private PutItemRequest getPutItemRequestForAccount(final Account account) {
        final Map<String, AttributeValue> itemHashMap = new HashMap<>();
        itemHashMap.put(FieldConstants.ACCOUNT_ID, AttributeValue.builder().s(account.getAccountId()).build());
        itemHashMap.put(FieldConstants.ACCOUNT_NAME, AttributeValue.builder().s(account.getName()).build());
        itemHashMap.put(FieldConstants.ACCOUNT_TYPE, AttributeValue.builder().s(account.getAccountType().name()).build());
        return PutItemRequest.builder()
            .item(itemHashMap)
            .tableName(ACCOUNT_DDB_TABLE_NAME)
            .build();
    }

    private DeleteItemRequest getDeleteItemRequestForAccount(final String accountId) {
        final Map<String, AttributeValue> itemHashMap = new HashMap<>();
        itemHashMap.put(FieldConstants.ACCOUNT_ID, AttributeValue.builder().s(accountId).build());
        return DeleteItemRequest.builder()
                .key(itemHashMap)
                .tableName(ACCOUNT_DDB_TABLE_NAME)
                .build();
    }
}

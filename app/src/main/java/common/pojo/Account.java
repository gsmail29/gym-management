package common.pojo;

import common.enums.AccountTypeEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Account {
    private String accountId;
    private String name;
    private AccountTypeEnum accountType;
}

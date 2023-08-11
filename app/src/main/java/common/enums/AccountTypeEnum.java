package common.enums;

public enum AccountTypeEnum {
    Instructor("Instructor"),
    Member("Member"),
    Equipment("Equipment");

    public final String accountType;

    private AccountTypeEnum(final String accountType) {
        this.accountType = accountType;
    }
}

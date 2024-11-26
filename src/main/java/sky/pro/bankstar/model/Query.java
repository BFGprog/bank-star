package sky.pro.bankstar.model;

public enum Query {
    USER_OF("USER_OF"),

    ACTIVE_USER_OF("ACTIVE_USER_OF"),
    TRANSACTION_SUM_COMPARE("TRANSACTION_SUM_COMPARE"),
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");

    private String query;

    Query(String query) {
        this.query = query;
    }
}

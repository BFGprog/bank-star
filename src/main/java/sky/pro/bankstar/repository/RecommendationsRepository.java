package sky.pro.bankstar.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public int getCountOfUsers() {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT COUNT(amount) FROM transactions",
                Integer.class);
        return result != null ? result : 0;
    }

    public List<String> getListOfUsers() {
        List<String> result = jdbcTemplate.queryForList(
                "SELECT user_id FROM TRANSACTIONs T INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE  P.\"TYPE\" = 'DEBIT' UNION SELECT user_id FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id WHERE   P.\"TYPE\" != 'INVEST' " +
                        "UNION " +
                        "SELECT T.USER_ID FROM TRANSACTIONS t INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE  t.AMOUNT > 1000 AND P.\"TYPE\" = 'SAVING'",
                String.class);
        return result;
    }

    // Recommendation Invest 500
    public List<String> getUsersInvest500() {
        List<String> result = jdbcTemplate.queryForList(
                "SELECT user_id FROM TRANSACTIONs T INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE  P.\"TYPE\" = 'DEBIT' " +
                        "UNION " +
                        "SELECT user_id FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id WHERE   P.\"TYPE\" != 'INVEST' " +
                        "UNION " +
                        "SELECT T.USER_ID FROM TRANSACTIONS t INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE  t.AMOUNT > 1000 AND P.\"TYPE\" = 'SAVING'",
                String.class);
        return result;
    }

    public List<String> getTopSaving() {
        List<String> result = jdbcTemplate.queryForList(
                "SELECT DISTINCT t.USER_ID FROM TRANSACTIONs T INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE P.\"TYPE\" = 'DEBIT' " +
                        "and T.PRODUCT_ID <> ALL (SELECT PRODUCTS.ID FROM PRODUCTS WHERE PRODUCTS.\"TYPE\" = 'INVEST') " +
                        "AND T.USER_ID <> ALL (SELECT T.USER_ID FROM TRANSACTIONS t INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE  t.AMOUNT < 1000 AND P.\"TYPE\" = 'SAVING')",
                String.class);
        return result;
    }

    public List<String> getCredit() {
        List<String> result = jdbcTemplate.queryForList(
                "SELECT DISTINCT t.USER_ID FROM TRANSACTIONs T INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE P.\"TYPE\" = 'DEBIT' " +
                        "and T.PRODUCT_ID <> ALL (SELECT PRODUCTS.ID FROM PRODUCTS WHERE PRODUCTS.\"TYPE\" = 'INVEST') " +
                        "AND T.USER_ID <> ALL (SELECT T.USER_ID FROM TRANSACTIONS t INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE  t.AMOUNT < 1000 AND P.\"TYPE\" = 'SAVING')",
                String.class);
        return result;
    }


}

package sky.pro.bankstar.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<String> getListOfUsersForTwoRecommendation() {
        List<String> result = jdbcTemplate.queryForList(
                "WITH wit AS  " +
                        "(SELECT user_id, sum(amount) AS sum_wit, type FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = 'DEBIT') AND TYPE = 'WITHDRAW' " +
                        "GROUP BY USER_ID ), " +
                        "dep AS  " +
                        "(SELECT user_id, sum(amount) AS sum_dep, type FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = 'DEBIT') AND TYPE = 'DEPOSIT' " +
                        "GROUP BY USER_ID) " +
                        "SELECT DISTINCT user_id FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE P.TYPE = 'DEBIT' " +
                        "AND user_id <> ALL (SELECT USER_id FROM(SELECT T.USER_ID FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE p.TYPE = 'INVEST')) " +
                        "AND T.USER_ID <> ALL (SELECT T.USER_ID FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE  t.AMOUNT < 1000 AND P.TYPE = 'SAVING') " +
                        "AND user_id in ( " +
                        "SELECT DISTINCT t.USER_ID FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE P.TYPE = 'DEBIT' " +
                        "AND T.USER_ID <> ALL (SELECT USER_ID FROM ( " +
                        "SELECT t.USER_ID ,p.TYPE , sum(t.AMOUNT) AS sum_amount FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE (P.TYPE = 'DEBIT' OR P.TYPE = 'SAVING') AND t.TYPE = 'DEPOSIT' " +
                        "GROUP BY t.USER_ID, p.TYPE " +
                        "HAVING sum_amount <= 50000)) " +
                        "AND USER_ID IN ( " +
                        "SELECT USER_ID FROM(SELECT TRANSACTIONS.user_id, wit.sum_wit, dep.sum_dep FROM TRANSACTIONS " +
                        "left JOIN wit ON wit.user_id = TRANSACTIONS.user_id " +
                        "left JOIN dep ON dep.user_id = TRANSACTIONS.user_id " +
                        "WHERE wit.sum_wit IS NOT NULL OR dep.sum_dep IS NOT null " +
                        "GROUP BY TRANSACTIONS.USER_ID " +
                        "HAVING wit.sum_wit < dep.sum_dep)))"
                ,
                String.class);
        return result;
    }

    // Recommendation Invest 500
    public List<String> getUsersInvest500() {
        List<String> result = jdbcTemplate.queryForList(
                "SELECT DISTINCT user_id FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE P.TYPE = 'DEBIT' " +
                        "AND user_id <> ALL (SELECT USER_id FROM(SELECT T.USER_ID FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE p.TYPE = 'INVEST')) " +
                        "AND T.USER_ID <> ALL (SELECT T.USER_ID FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS P ON t.product_id = P.id WHERE  t.AMOUNT < 1000 AND P.TYPE = 'SAVING')"
                ,
                String.class);
        return result;
    }

    public List<String> getTopSaving() {
        List<String> result = jdbcTemplate.queryForList(
                "WITH wit AS  " +
                        "(SELECT user_id, sum(amount) AS sum_wit, type FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = 'DEBIT') AND TYPE = 'WITHDRAW' " +
                        "GROUP BY USER_ID ), " +
                        "dep AS  " +
                        "(SELECT user_id, sum(amount) AS sum_dep, type FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = 'DEBIT') AND TYPE = 'DEPOSIT' " +
                        "GROUP BY USER_ID) " +
                        "SELECT USER_ID FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE P.\"TYPE\" = 'DEBIT' " +
                        "AND T.USER_ID <> ALL (SELECT USER_ID FROM ( " +
                        "SELECT t.USER_ID ,p.\"TYPE\" , sum(t.AMOUNT) AS sum_amount FROM TRANSACTIONs T " +
                        "INNER JOIN PRODUCTS P ON T.product_id = P.id " +
                        "WHERE (P.\"TYPE\" = 'DEBIT' OR P.\"TYPE\" = 'SAVING') AND t.\"TYPE\" = 'DEPOSIT' " +
                        "GROUP BY t.USER_ID, p.\"TYPE\" " +
                        "HAVING sum_amount < 50000)) " +
                        "AND USER_ID IN ( " +
                        "SELECT USER_ID FROM(SELECT TRANSACTIONS.user_id, wit.sum_wit, dep.sum_dep FROM TRANSACTIONS " +
                        "left JOIN wit ON wit.user_id = TRANSACTIONS.user_id " +
                        "left JOIN dep ON dep.user_id = TRANSACTIONS.user_id " +
                        "WHERE wit.sum_wit IS NOT NULL OR dep.sum_dep IS NOT null " +
                        "GROUP BY TRANSACTIONS.USER_ID " +
                        "HAVING wit.sum_wit < dep.sum_dep))"
                ,
                String.class);
        return result;
    }

    public List<String> getCredit() {
        List<String> result = jdbcTemplate.queryForList(
                "WITH wit AS  " +
                        "(SELECT user_id, sum(amount) AS sum_wit, type FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = 'DEBIT') AND TYPE = 'WITHDRAW' " +
                        "GROUP BY USER_ID ), " +
                        "dep AS  " +
                        "(SELECT user_id, sum(amount) AS sum_dep, type FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = 'DEBIT') AND TYPE = 'DEPOSIT' " +
                        "GROUP BY USER_ID) " +
                        "SELECT USER_ID FROM(SELECT TRANSACTIONS.user_id, wit.sum_wit, dep.sum_dep FROM TRANSACTIONS " +
                        "left JOIN wit ON wit.user_id = TRANSACTIONS.user_id " +
                        "left JOIN dep ON dep.user_id = TRANSACTIONS.user_id " +
                        "WHERE wit.sum_wit IS NOT NULL OR dep.sum_dep IS NOT NULL " +
                        "AND TRANSACTIONS.PRODUCT_ID <> ALL (SELECT id FROM PRODUCTS WHERE TYPE = 'CREDIT') " +
                        "GROUP BY TRANSACTIONS.USER_ID " +
                        "HAVING wit.sum_wit < dep.sum_dep and wit.sum_wit > 100000)"
                ,
                String.class);
        return result;
    }


}

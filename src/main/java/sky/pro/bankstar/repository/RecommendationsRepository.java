package sky.pro.bankstar.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sky.pro.bankstar.model.UserDB;

import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable(value = "randomTransactionAmount", key = "#user_id") // для каждого метода работает своё "кэширование", по названию метода
    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }
    @Cacheable(value = "hasDebitProduct", key = "#user_id")
    public boolean hasDebitProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'", Integer.class, user_id);

        return result > 0 ? true : false;
    }
    @Cacheable(value = "hasInvestProduct", key = "#user_id")
    public boolean hasInvestProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'INVEST'", Integer.class, user_id);

        return result > 0 ? true : false;
    }
    @Cacheable(value = "hasCreditProduct", key = "#user_id")
    public boolean hasCreditProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'CREDIT'", Integer.class, user_id);

        return result > 0 ? true : false;
    }
    @Cacheable(value = "savingAmount", key = "#user_id")
    public Long getSavingAmount(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'SAVING' AND t.TYPE = 'DEPOSIT'",
                Long.class, user_id);

        return result != null ? result : 0;
    }
    @Cacheable(value = "debitAmount", key = "#user_id")
    public Long getDebitAmount(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT' AND t.TYPE = 'DEPOSIT'",
                Long.class, user_id);

        return result != null ? result : 0;
    }
    @Cacheable(value = "debitExpenses", key = "#user_id")
    public Long getDebitExpenses(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT' AND p.TYPE = 'WITHDRAW'",
                Long.class, user_id);

        return result != null ? result : 0;
    }

    // rule 1
    @Cacheable(value = "checkUserOf", key = "#user_id.toString() + #arguments.get(0) + #negate")
    public boolean checkUserOf(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN ( " +
                        "SELECT COUNT(*) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.TYPE = '" + arguments.get(0) + "' " +
                        ") > 0 then 'true' else 'false' end",
                Boolean.class,
                user_id);
        return result == negate ? true : false;
    }

    // rule 2
    @Cacheable(value = "checkActiveUserOf", key = "#user_id.toString() + #arguments.get(0) + #negate")
    public boolean checkActiveUserOf(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN ( " +
                        "SELECT COUNT(*) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.TYPE = '" + arguments.get(0) + "' " +
                        ") > 4 then 'true' else 'false' end",
                Boolean.class,
                user_id);
        return result == negate ? false : true;
    }

    // rule 3
    @Cacheable(value = "checkTransactionSumCompare", key = "#user_id.toString() + #arguments.get(0) + #arguments.get(1) + #arguments.get(2) + #arguments.get(3) + #negate")
    public boolean checkTransactionSumCompare(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN ( " +
                        "SELECT sum(amount) FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = '" + arguments.get(0) + "') " +
                        "AND TYPE = '" + arguments.get(1) + "' AND USER_id = ? " +
                        ") " + arguments.get(2) + " " + arguments.get(3) + " then 'true' else 'false' end ",
                Boolean.class,
                user_id);
        return result == negate ? true : false;
    }

    // rule 4
    @Cacheable(value = "checkTransactionSumCompareDepositWithdraw", key = "#user_id.toString() + #arguments.get(0) + #arguments.get(1) + #negate")
    public boolean checkTransactionSumCompareDepositWithdraw(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN ( " +
                        "SELECT sum(amount) FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = '" + arguments.get(0) + "') " +
                        "AND TYPE = 'DEPOSIT' AND USER_id = ? " +
                        ") " + arguments.get(1) + " ( " +
                        "SELECT sum(amount) FROM TRANSACTIONS " +
                        "WHERE PRODUCT_ID IN (SELECT id FROM PRODUCTS WHERE TYPE = '" + arguments.get(0) + "') " +
                        "AND TYPE = 'WITHDRAW' AND USER_id = ? " +
                        ") " +
                        "then 'true' else 'false' end",
                Boolean.class,
                user_id,
                user_id);
        return result == negate ? true : false;
    }
//   Получаем пользователя по имени.
    @Cacheable(value = "getUser", key = "#userName")
    public UserDB getUser(String userName) {

        UserDB userDB = jdbcTemplate.queryForObject(
                "SELECT ID, FIRST_NAME, LAST_NAME FROM USERS u WHERE u.USERNAME = ?",
                new Object[]{userName},
                new BeanPropertyRowMapper<>(UserDB.class));
            return userDB;
    }
}

package sky.pro.bankstar.repository;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sky.pro.bankstar.model.CacheFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable("randomTransactionAmount") // для каждого метода работает своё "кэширование", по названию метода
    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }
    @Cacheable("hasDebitProduct")
    public boolean hasDebitProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'", Integer.class, user_id);

        return result > 0 ? true : false;
    }
    @Cacheable("hasInvestProduct")
    public boolean hasInvestProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'INVEST'", Integer.class, user_id);

        return result > 0 ? true : false;
    }
    @Cacheable("hasCreditProduct")
    public boolean hasCreditProduct(UUID user_id) {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT (DISTINCT t.USER_ID) FROM TRANSACTIONS t " +
                "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = 'CREDIT'", Integer.class, user_id);

        return result > 0 ? true : false;
    }
    @Cacheable("savingAmount")
    public Long getSavingAmount(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'SAVING' AND t.TYPE = 'DEPOSIT'",
                Long.class, user_id);

        return result != null ? result : 0;
    }
    @Cacheable("debitAmount")
    public Long getDebitAmount(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT' AND t.TYPE = 'DEPOSIT'",
                Long.class, user_id);

        return result != null ? result : 0;
    }
    @Cacheable("debitExpenses")
    public Long getDebitExpenses(UUID user_id) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT' AND p.TYPE = 'WITHDRAW'",
                Long.class, user_id);

        return result != null ? result : 0;
    }
}

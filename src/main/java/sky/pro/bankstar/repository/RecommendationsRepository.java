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

    public int getRandomTransactionAmount(UUID user){
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

    public List<String> getUsersInvest500() {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT users_id FROM transactions ",
                Integer.class);
        return null;
    }



}

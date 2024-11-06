package sky.pro.bankstar.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sky.pro.bankstar.model.Query;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.model.Rule;
import sky.pro.bankstar.model.RuleRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RuleRecommendRepository {
    private final JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(RuleRecommendRepository.class);

    public RuleRecommendRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Recommendations addRecommendations(Recommendations recommendation) {
        final String insertRecommendation = "INSERT into recommendations (product_name, product_id, product_text) VALUES(?,?,?)";
        final String insertRule = "INSERT into rules (recommendation_id, query, arguments, negate) VALUES(?,?,?,?)";
        List<String> ruleId = new ArrayList<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolderRule = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement(insertRecommendation, new String[]{"id"});
                                    ps.setString(1, recommendation.getProduct_name());
                                    ps.setString(2, recommendation.getProduct_id().toString());
                                    ps.setString(3, recommendation.getProduct_text());
                                    return ps;
                                }
                            },
                keyHolder);
        logger.debug("Genereted Recommendations id: " + String.valueOf(keyHolder.getKey()));

        List<Rule> rules = recommendation.getRule();
        for (int i = 0; i < rules.size(); i++) {
            Query query = rules.get(i).getQuery();
            String arguments = rules.get(i).getArguments()//.toString();
                    .stream()
                    .collect(Collectors.joining(", "));
            String negate = rules.get(i).isNegate() + "";

            jdbcTemplate.update(new PreparedStatementCreator() {
                                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                        PreparedStatement ps = connection.prepareStatement(insertRule, new String[]{"rules_id"});
                                        ps.setString(1, keyHolder.getKey().toString());
                                        ps.setString(2, String.valueOf(query));
                                        ps.setString(3, arguments);
                                        ps.setString(4, negate);
                                        return ps;
                                    }
                                },
                    keyHolderRule);
            ruleId.add(keyHolderRule.getKey().toString());
        }
        logger.debug("Genereted Rule id: " + String.valueOf(ruleId));

        Recommendations obtainedRecommendation = (Recommendations) jdbcTemplate.queryForObject("select * from recommendations where id = ?",
                new Object[]{keyHolder.getKey().toString()},
                new BeanPropertyRowMapper(Recommendations.class));

        List<Rule> obtainedRules = jdbcTemplate.query("select * from rules where recommendation_id = ?",
                new Object[]{keyHolder.getKey().toString()},
                new RuleRowMapper());

        obtainedRecommendation.setRule(obtainedRules);
        return obtainedRecommendation;
    }

    public void delRecommendation(Long id) {
        jdbcTemplate.update("DELETE FROM recommendations where id = ?", id);
    }

    public List<Rule> getRules(Long id) {
        return jdbcTemplate.query("select * from rules where recommendation_id = ?",
                new Object[]{id},
                new RuleRowMapper());
    }

    public List<Long> listRecommendationsId() {
    List<Long> recommendationsId = jdbcTemplate.queryForList(
            "select id from recommendations",
            Long.class);
    return recommendationsId;
    }

    public Recommendations getRecommendation(Long id) {
        return (Recommendations) jdbcTemplate.queryForObject("select * from recommendations where id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper(Recommendations.class));
    }

}

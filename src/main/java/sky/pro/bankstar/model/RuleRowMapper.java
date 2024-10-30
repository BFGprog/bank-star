package sky.pro.bankstar.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class RuleRowMapper implements RowMapper<Rule> {

    @Override
    public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rule rule = new Rule();
        rule.setQuery(rs.getString("query"));
        rule.setArguments(Collections.singletonList(rs.getString("arguments")));
        rule.setNegate(rs.getBoolean("negate"));
    return rule;
    }
}

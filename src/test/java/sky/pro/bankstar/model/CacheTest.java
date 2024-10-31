package sky.pro.bankstar.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sky.pro.bankstar.repository.RecommendationsRepository;

import javax.sql.DataSource;
import java.util.UUID;

public class CacheTest {

    public static void main(String[] args) {
        DataSource dataSource = new EmbeddedDatabaseBuilder() // проверка, на то что кэширование работает и второй и последующие запросы обрабатываются быстрее
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        RecommendationsRepository repository = new RecommendationsRepository(jdbcTemplate);

        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        long startTime = System.currentTimeMillis();
        int randomTransactionAmount1 = repository.getRandomTransactionAmount(userId);
        long endTime = System.currentTimeMillis();
        System.out.println("First request time: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        int randomTransactionAmount2 = repository.getRandomTransactionAmount(userId);
        endTime = System.currentTimeMillis();
        System.out.println("Second request time: " + (endTime - startTime) + " ms");
    }
}
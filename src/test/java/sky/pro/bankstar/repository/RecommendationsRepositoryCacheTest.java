package sky.pro.bankstar.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class RecommendationsRepositoryCacheTest implements CommandLineRunner {

    @Autowired
    private RecommendationsRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(RecommendationsRepositoryCacheTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
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
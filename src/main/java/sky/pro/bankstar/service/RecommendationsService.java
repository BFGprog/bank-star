package sky.pro.bankstar.service;


import java.util.List;
import java.util.UUID;

public interface RecommendationsService {

    public int getRandomTransactionAmount(UUID user);
    public String getRecommendations(UUID users_id);

}

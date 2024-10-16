package sky.pro.bankstar.service;


import sky.pro.bankstar.model.Recommendation;

import java.util.List;
import java.util.UUID;

public interface StarService {

    public int getRandomTransactionAmount(UUID user);
    public int getCountOfUsers();

    public List<Recommendation> getRecommendation(UUID user);

    public List<String> getListOfUsers();

}

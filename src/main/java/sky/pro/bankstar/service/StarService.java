package sky.pro.bankstar.service;


import java.util.List;
import java.util.UUID;

public interface StarService {

    public int getRandomTransactionAmount(UUID user);
    public int getCountOfUsers();

    public List<String> getUsersInvest500();
    public List<String> getUsersTopSaving();
    public List<String> getUsersCredit();

}

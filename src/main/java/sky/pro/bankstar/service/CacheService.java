package sky.pro.bankstar.service;

public class CacheService {
    public void clearCashesAndUpdateDatabase() {
        clearCashe();
        updateDatabase();
    }

    private void clearCashe() {
        System.out.println("Cashe cleared");

    }

    private void updateDatabase() {
        System.out.println("Database updated");

    }
}

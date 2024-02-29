package dataAccess;

public class GameDAOManager {
    private static GameDAO gameDAOInstance;

    private GameDAOManager() {

    }

    public static GameDAO getGameDAO() {
        if (gameDAOInstance == null) {
            gameDAOInstance = new MemoryGameDAO();
        }
        return gameDAOInstance;
    }
}

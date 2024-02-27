package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> gameMap;

    public MemoryGameDAO() {
        // constructor
        this.gameMap = new HashMap<>();
    }
    @Override
    public void addGame(String whiteUsername, String blackUsername, String gameName, int gameID, ChessGame game) throws DataAccessException {
        GameData gameObject = new GameData(whiteUsername, blackUsername, gameName, gameID, new ChessGame());
        gameMap.put(gameID, gameObject);
    }

    @Override
    public GameData getGame(int gameID) {
        return gameMap.get(gameID);
    }


    @Override
    public void updateGame(int gameID, GameData updatedGame) {
        GameData gameObject = gameMap.get(gameID);
        if (gameObject != null) {
            gameMap.remove(gameID);
            gameMap.put(gameID, updatedGame);
        }
    }

    @Override
    public void deleteGame(int gameID) {
        gameMap.remove(gameID);
    }

    @Override
    public void clear() {
        gameMap.clear();
    }

    @Override
    public Collection<GameData> listGames() {
        return gameMap.values();
    }
}

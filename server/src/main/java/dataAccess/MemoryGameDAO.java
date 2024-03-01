package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> gameMap;
    private int currentGameID = 0;

    public MemoryGameDAO() {
        // constructor
        this.gameMap = new HashMap<>();
    }
    @Override
    public void addGame(String whiteUsername, String blackUsername, String gameName, int gameID, ChessGame game){
        GameData gameObject = new GameData(whiteUsername, blackUsername, gameName, gameID, game);
        gameMap.put(gameID, gameObject);
    }

    public int getNewGameID() {
        return (currentGameID + 1);
    }

    @Override
    public GameData getGame(int gameID) {
        return gameMap.get(gameID);
    }


    @Override
    public void updateGame(int gameID, GameData updatedGame) {
        GameData gameObject = gameMap.get(gameID);
        if (Objects.equals(gameObject, null)) {
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

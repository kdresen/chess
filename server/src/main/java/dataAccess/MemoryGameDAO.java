package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.GameInfo;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> gameMap;
    private int currentGameID = 0;

    public MemoryGameDAO() {
        // constructor
        this.gameMap = new HashMap<>();
    }
    @Override
    public GameData addGame(String whiteUsername, String blackUsername, String gameName, int gameID, ChessGame game){
        if (gameID == 0) {
            gameID = currentGameID + 1;
        }
        GameData gameObject = new GameData(whiteUsername, blackUsername, gameName, gameID, game);
        currentGameID++;
        gameMap.put(gameObject.gameID(), gameObject);
        return gameObject;
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
        currentGameID = 0;
        gameMap.clear();
    }

    @Override
    public List<GameInfo> listGames() {
        return gameMap.values().stream()
                .map(gameData -> new GameInfo(
                        gameData.whiteUsername(),
                        gameData.blackUsername(),
                        gameData.gameName(),
                        gameData.gameID()))
                .collect(Collectors.toList());
    }
}

package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDAO {

    public void addGame(String whiteUsername, String blackUsername, String gameName, int gameID, ChessGame game);

    public GameData getGame(int gameID);

    public void updateGame(int gameID, GameData updatedGame);

    public void deleteGame(int gameID);

    public void clear();

    public Collection<GameData> listGames();

}

package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.GameInfo;

import java.util.List;

public interface GameDAO {

    public GameData addGame(String whiteUsername, String blackUsername, String gameName, int gameID, ChessGame game);

    public GameData getGame(int gameID);

    public void updateGame(int gameID, GameData updatedGame);

    public void deleteGame(int gameID);

    public void clear();

    public List<GameInfo> listGames();

}

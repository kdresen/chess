package service;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.results.CreateGameResult;
import model.results.ListGamesResult;

import java.util.Objects;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService (AuthDAO authDAO, GameDAO gameDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }
    // returns list of all games in gameDAO
    public ListGamesResult listGames(String authToken) throws DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        if (Objects.equals(auth, null)) {
            throw new DataAccessException("Error: unauthorized");
        }
        return new ListGamesResult(gameDAO.listGames());
    }
    // creates new game in gameDAO
    public CreateGameResult createGame(CreateGameRequest req) throws DataAccessException {
        if (Objects.equals(req, null)) {
            throw new DataAccessException("Error: bad request");
        }
        if (Objects.equals(req.authToken(), null) || Objects.equals(req.gameName(), null)) {
            throw new DataAccessException("Error: bad request");
        }
        AuthData auth = authDAO.getAuth(req.authToken());
        if (Objects.equals(auth, null)) {
            throw new DataAccessException("Error: unauthorized");
        }
        GameData result = gameDAO.addGame(null, null, req.gameName(), 0, new ChessGame());
        return new CreateGameResult(result.gameID());
    }
    // adds user to game from gameID, teamColor can be null
    public void joinGame(JoinGameRequest req) throws DataAccessException {
        if (Objects.equals(req, null)) {
            throw new DataAccessException("Error: bad request");
        }
        if (Objects.equals(req.authToken(), null) || Objects.equals(gameDAO.getGame(req.gameID()), null)) {
            throw new DataAccessException("Error: bad request");
        }
        if (!Objects.equals(req.playerColor(), "WHITE") && !Objects.equals(req.playerColor(), "BLACK") && !Objects.equals(req.playerColor(), null)) {
            throw new DataAccessException("Error: bad request");
        }
        AuthData auth = authDAO.getAuth(req.authToken());
        if (Objects.equals(auth, null)) {
            throw new DataAccessException("Error: unauthorized");
        }
        GameData game = gameDAO.getGame(req.gameID());
        GameData updatedGame;
        if (Objects.equals(req.playerColor(), "WHITE")) {
            if (!game.isWhiteUsernameNull()) {
                throw new DataAccessException("Error: already taken");
            } else {
                updatedGame = new GameData(auth.username(), game.blackUsername(), game.gameName(), game.gameID(), game.game());
                gameDAO.deleteGame(game.gameID());
                gameDAO.addGame(updatedGame.whiteUsername(), updatedGame.blackUsername(), updatedGame.gameName(), updatedGame.gameID(), updatedGame.game());
            }
        }
        if (Objects.equals(req.playerColor(), "BLACK")) {
            if (!game.isBlackUsernameNull()) {
                throw new DataAccessException("Error: already taken");
            } else {
                updatedGame = new GameData(game.whiteUsername(), auth.username(), game.gameName(), game.gameID(), game.game());
                gameDAO.deleteGame(game.gameID());
                gameDAO.addGame(updatedGame.whiteUsername(), updatedGame.blackUsername(), updatedGame.gameName(), updatedGame.gameID(), updatedGame.game());
            }
        }
    }
}

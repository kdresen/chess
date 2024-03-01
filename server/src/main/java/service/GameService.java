package service;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.GameData;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.results.CreateGameResult;
import model.results.ErrorResult;
import model.results.JoinGameResult;
import model.results.ListGamesResult;

import java.util.Collection;
import java.util.Objects;

public class GameService {
    private final UserDAO userDAO;
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService (AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }
    // returns list of all games in gameDAO
    public Object listGames(String authToken) {
        AuthData auth = authDAO.getAuth(authToken);
        if (Objects.equals(auth.authToken(), null)) {
            return new ErrorResult(401, "Error: unauthorized");
        }
        return new ListGamesResult(200, gameDAO.listGames());
    }
    // creates new game in gameDAO
    public Object createGame(CreateGameRequest req) {
        if (Objects.equals(req.authToken(), null) || Objects.equals(req.gameName(), null)) {
            return new ErrorResult(400, "Error: bad request");
        }
        AuthData auth = authDAO.getAuth(req.authToken());
        if (Objects.equals(auth.authToken(), null)) {
            return new ErrorResult(401, "Error: unauthorized");
        }
        gameDAO.addGame(null, null, req.gameName(), 0, new ChessGame());
        return new CreateGameResult(200, req.gameName());
    }
    // adds user to game from gameID, teamColor can be null
    public Object joinGame(JoinGameRequest req) {
        if (Objects.equals(req.authToken(), null) || Objects.equals(gameDAO.getGame(req.gameID()), null)) {
            return new ErrorResult(400, "Error: bad request");
        }
        AuthData auth = authDAO.getAuth(req.authToken());
        if (Objects.equals(auth, null)) {
            return new ErrorResult(400, "Error: unauthorized");
        }
        GameData game = gameDAO.getGame(req.gameID());
        GameData updatedGame;
        if (Objects.equals(req.teamColor(), null)) {
            if (!game.isWhiteUsernameNull()) {
                updatedGame = new GameData(auth.username(), game.blackUsername(), game.gameName(), game.gameID(), game.game());
            } else if (!game.isBlackUsernameNull()) {
                updatedGame = new GameData(game.whiteUsername(), auth.username(), game.gameName(), game.gameID(), game.game());
            } else {
                return new ErrorResult(403, "Error: already taken");
            }
            gameDAO.deleteGame(game.gameID());
            gameDAO.addGame(updatedGame.whiteUsername(), updatedGame.blackUsername(), updatedGame.gameName(), updatedGame.gameID(), updatedGame.game());
            return new JoinGameResult(200, "{}");
        } else {
            if (req.teamColor() == ChessGame.TeamColor.WHITE) {
                if (!game.isWhiteUsernameNull()) {
                    return new ErrorResult(403, "Error: already taken");
                } else {
                    updatedGame = new GameData(auth.username(), game.blackUsername(), game.gameName(), game.gameID(), game.game());
                    gameDAO.deleteGame(game.gameID());
                    gameDAO.addGame(updatedGame.whiteUsername(), updatedGame.blackUsername(), updatedGame.gameName(), updatedGame.gameID(), updatedGame.game());
                    return new JoinGameResult(200, "{}");
                }
            }
            if (req.teamColor() == ChessGame.TeamColor.BLACK) {
                if (!game.isBlackUsernameNull()) {
                    return new ErrorResult(403, "Error: already taken");
                } else {
                    updatedGame = new GameData(game.whiteUsername(), auth.username(), game.gameName(), game.gameID(), game.game());
                    gameDAO.deleteGame(game.gameID());
                    gameDAO.addGame(updatedGame.whiteUsername(), updatedGame.blackUsername(), updatedGame.gameName(), updatedGame.gameID(), updatedGame.game());
                    return new JoinGameResult(200, "{}");
                }
            }
            return new ErrorResult(400, "Error: bad request");
        }
    }
}

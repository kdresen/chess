package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameInfo;
import model.UserData;
import model.requests.JoinGameRequest;
import model.results.ListGamesResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.GameService;
import service.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JoinGameTests {
    static final UserDAO userDAO = UserDAOManager.getUserDAO();
    static final AuthDAO authDAO = AuthDAOManager.getAuthDAO();
    static final GameDAO gameDAO = GameDAOManager.getGameDAO();
    static final GameService gameService = new GameService(authDAO, gameDAO);
    static final UserService userService = new UserService(authDAO, userDAO);
    static final ClearApplicationService clearApplicationService = new ClearApplicationService(userDAO, authDAO, gameDAO);




    @BeforeEach
    void reset() throws DataAccessException {
        clearApplicationService.deleteAll();
    }

    @Test
    @DisplayName("Join Game Successful")
    void joinGameSuccessful() {
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            GameInfo testGame1 = new GameInfo("a", null, "gameName1", 1);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            JoinGameRequest joinGameRequest = new JoinGameRequest(authData.authToken(), "WHITE", 1);

            ListGamesResult gamesList = new ListGamesResult(games);

            gameDAO.addGame(null, null, "gameName1", 1, new ChessGame());

            gameService.joinGame(joinGameRequest);

            ListGamesResult result = gameService.listGames(authData.authToken());
            assertEquals(gamesList, result, "Lists do not match");
        } catch (DataAccessException e) {
            assertNull(e, "Incorrectly sent error");
        }
    }

    @Test
    @DisplayName("Join Game Unsuccessful bad request")
    void JoinGameBadRequest() {
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            GameInfo testGame1 = new GameInfo("a", null, "gameName1", 1);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            JoinGameRequest joinGameRequest = new JoinGameRequest(null, "WHITE", 1);

            ListGamesResult gamesList = new ListGamesResult(games);

            gameDAO.addGame(null, null, "gameName1", 1, new ChessGame());

            gameService.joinGame(joinGameRequest);

            ListGamesResult result = gameService.listGames(authData.authToken());
            assertEquals(gamesList, result, "Lists do not match");
        } catch (DataAccessException e) {
            assertEquals("Error: bad request", e.getMessage(), "Did not throw Error: bad request");
        }
    }

    @Test
    @DisplayName("Join Game Unsuccessful unauthorized")
    void JoinGameUnauthorized() {
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            GameInfo testGame1 = new GameInfo("a", null, "gameName1", 1);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            JoinGameRequest joinGameRequest = new JoinGameRequest("bad authToken", "WHITE", 1);

            ListGamesResult gamesList = new ListGamesResult(games);

            gameDAO.addGame(null, null, "gameName1", 1, new ChessGame());

            gameService.joinGame(joinGameRequest);

            ListGamesResult result = gameService.listGames(authData.authToken());
            assertEquals(gamesList, result, "Lists do not match");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage(), "Did not throw Error: unauthorized");
        }
    }

    @Test
    @DisplayName("Join Game Already Taken")
    void JoinGameAlreadyTaken() {
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            GameInfo testGame1 = new GameInfo("a", null, "gameName1", 1);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            JoinGameRequest joinGameRequest = new JoinGameRequest(authData.authToken(), "WHITE", 1);

            ListGamesResult gamesList = new ListGamesResult(games);

            gameDAO.addGame("b", null, "gameName1", 1, new ChessGame());

            gameService.joinGame(joinGameRequest);

            ListGamesResult result = gameService.listGames(authData.authToken());
            assertEquals(gamesList, result, "Lists do not match");
        } catch (DataAccessException e) {
            assertEquals("Error: already taken", e.getMessage(), "Did not throw Error: already taken");
        }
    }
}

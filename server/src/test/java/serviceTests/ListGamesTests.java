package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.GameInfo;
import model.UserData;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.results.CreateGameResult;
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

public class ListGamesTests {

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
    @DisplayName("List Games Successful")
    void listGamesSuccessful() {
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            GameInfo testGame1 = new GameInfo(null, null, "gameName1", 1);
            GameInfo testGame2 = new GameInfo(null, null, "gameName2", 2);
            GameInfo testGame3 = new GameInfo(null, null, "gameName3", 3);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            games.add(testGame2);
            games.add(testGame3);

            ListGamesResult gamesList = new ListGamesResult(games);

            gameDAO.addGame(null, null, "gameName1", 1, new ChessGame());
            gameDAO.addGame(null, null, "gameName2", 2, new ChessGame());
            gameDAO.addGame(null, null, "gameName3", 3, new ChessGame());

            ListGamesResult result = gameService.listGames(authData.authToken());

            assertEquals(gamesList, result, "Lists do not match");
        } catch (DataAccessException e) {
            assertNull(e, "Incorrectly sent error");
        }
    }
    @Test
    @DisplayName("List Games Unsuccessful")
    void listGamesUnsuccessful() {
        ListGamesResult result = null;
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            GameInfo testGame1 = new GameInfo(null, null, "gameName1", 1);
            GameInfo testGame2 = new GameInfo(null, null, "gameName2", 2);
            GameInfo testGame3 = new GameInfo(null, null, "gameName3", 3);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            games.add(testGame2);
            games.add(testGame3);

            ListGamesResult gamesList = new ListGamesResult(games);

            gameDAO.addGame(null, null, "gameName1", 1, new ChessGame());
            gameDAO.addGame(null, null, "gameName2", 2, new ChessGame());
            gameDAO.addGame(null, null, "gameName3", 3, new ChessGame());

            result = gameService.listGames("bad auth Token");

            assertNull(result, "Error did not throw");
        } catch (DataAccessException e) {
            assertNull(result, "incorrectly submitted lists");
            assertEquals("Error: unauthorized", e.getMessage(), "Did not throw Error: unauthorized");
        }
    }
}

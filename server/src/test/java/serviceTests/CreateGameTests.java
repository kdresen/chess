package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.GameInfo;
import model.UserData;
import model.requests.CreateGameRequest;
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

public class CreateGameTests {
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
    @DisplayName("Create Game Successful")
    void createGameSuccessful() {
        CreateGameResult result = null;
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            CreateGameRequest request = new CreateGameRequest(authData.authToken(), "gameName1");

            GameInfo testGame1 = new GameInfo(null, null, "gameName1", 1);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            ListGamesResult gamesList = new ListGamesResult(games);

            result = gameService.createGame(request);
            ListGamesResult actualList = gameService.listGames(authData.authToken());

            assertEquals(gamesList, actualList, "lists do not match");

        } catch (DataAccessException e) {
            assertNull(result, "incorrectly sent error");
        }
    }

    @Test
    @DisplayName("Create Game Unsuccessful")
    void createGameUnsuccessful() {
        CreateGameResult result = null;
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            CreateGameRequest request = new CreateGameRequest(authData.authToken(), null);

            GameInfo testGame1 = new GameInfo(null, null, null, 1);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            ListGamesResult gamesList = new ListGamesResult(games);

            result = gameService.createGame(request);
            ListGamesResult actualList = gameService.listGames(authData.authToken());

            assertNull(result, "Did not throw error");

        } catch (DataAccessException e) {
            assertNull(result, "incorrectly returned result");
            assertEquals("Error: bad request", e.getMessage(), "Did not send correct error");
        }
    }
    @Test
    @DisplayName("Create Game Unsuccessful unauthorized")
    void createGameUnauthorized() {
        CreateGameResult result = null;
        try {
            UserData userData = new UserData("a", "password", "email");
            AuthData authData = userService.registerUser(userData);
            CreateGameRequest request = new CreateGameRequest("bad authToken", "gameName1");

            GameInfo testGame1 = new GameInfo(null, null, "gameName1", 1);
            java.util.List<GameInfo> games = new ArrayList<>();
            games.add(testGame1);
            ListGamesResult gamesList = new ListGamesResult(games);

            result = gameService.createGame(request);
            ListGamesResult actualList = gameService.listGames(authData.authToken());

            assertNull(result, "Did not throw error");

        } catch (DataAccessException e) {
            assertNull(result, "incorrectly returned result");
            assertEquals("Error: unauthorized", e.getMessage(), "Did not send correct error");
        }
    }
}

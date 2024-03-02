package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoginTests {

    static final UserDAO userDAO = UserDAOManager.getUserDAO();
    static final AuthDAO authDAO = AuthDAOManager.getAuthDAO();
    static final GameDAO gameDAO = GameDAOManager.getGameDAO();
    static final UserService userService = new UserService(authDAO, userDAO);
    static final ClearApplicationService clearApplicationService = new ClearApplicationService(userDAO, authDAO, gameDAO);
    @BeforeEach
    void reset() throws DataAccessException {
        clearApplicationService.deleteAll();
    }

    @Test
    @DisplayName("successful login")
    void loginUserSuccessful() {
        AuthData output = null;
        AuthData loginOutput = null;
        try {
            UserData testData = new UserData("a", "password", "email");
            output = userService.registerUser(testData);

            userService.logoutUser(output.authToken());
            loginOutput = userService.loginUser(testData);

            assertEquals(loginOutput.username(), testData.username(), "usernames do not match");

        } catch (DataAccessException e) {
            assertNull(loginOutput, "incorrectly returned auth upon error");
        }
    }

    @Test
    @DisplayName("unsuccessful login")
    void loginUserUnsuccessful() {
        AuthData output = null;
        AuthData loginOutput = null;
        try {
            UserData testData = new UserData("a", "password", "email");
            UserData badData = new UserData("a", "pbssword", "email");
            output = userService.registerUser(testData);

            userService.logoutUser(output.authToken());
            loginOutput = userService.loginUser(badData);

            assertNull(loginOutput, "No error thrown when info is incorrect");

        } catch (DataAccessException e) {
            assertNull(loginOutput, "incorrectly returned auth upon error");
            assertEquals("Error: unauthorized", e.getMessage(), "returned incorrect error message");
        }
    }
}

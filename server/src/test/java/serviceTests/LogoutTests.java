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

public class LogoutTests {

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
    @DisplayName("successful logout")
    void logoutUserSuccessful() {
        AuthData output = null;
        try {
            UserData testData = new UserData("a", "password", "email");
            output = userService.registerUser(testData);
            userService.logoutUser(output.authToken());

            AuthData testAuth = authDAO.getAuth(output.authToken());

            assertNull(testAuth, "Returned an AuthToken that should not exist");

        } catch (DataAccessException e) {
            assertNull(output, "Incorrectly returned output on error");
        }
    }

    @Test
    @DisplayName("unsuccessful logout")
    void logoutUserUnsuccessful() {
        AuthData testAuth = null;
        try {
            UserData testData = new UserData("a", "password", "email");
            AuthData output = userService.registerUser(testData);
            userService.logoutUser("bad authToken");

            testAuth = authDAO.getAuth(output.authToken());
        } catch (DataAccessException e) {
            assertNull(testAuth, "Incorrectly returned AuthToken on error");
            assertEquals("Error: unauthorized", e.getMessage(), "Did not return Error: unauthorized");
        }
    }
}

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

public class RegisterTests {

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
    @DisplayName("successful register")
    void registerUserSuccessful() throws DataAccessException {
        UserData userData = new UserData("a", "password","email");
        AuthData actualOutput = userService.registerUser(userData);

        UserData expectedResult = new UserData("a", "password", "email");
        UserData actualResult = userDAO.getUser("a");
        AuthData expectedOutput = authDAO.getAuth(actualOutput.authToken());

        assertEquals(actualResult, expectedResult, "UserData doesn't match");
        assertEquals(actualOutput, expectedOutput, "AuthTokens do not match");
    }

    @Test
    @DisplayName("unsuccessful register bad request")
    void registerUserFailureBadRequest() {
        AuthData output = null;
        try {
            UserData userData = new UserData(null, "password", "email");
            output = userService.registerUser(userData);
        } catch (DataAccessException e) {
            assertEquals("Error: bad request", e.getMessage(), "Error message was not Error: bad request");
            assertNull(output, "Incorrectly returned Auth");
        }
    }

    @Test
    @DisplayName("unsuccessful register already taken")
    void registerUserFailureAlreadyTaken() {
        AuthData output = null;
        AuthData duplicateOutput = null;
        try {
            UserData existingData = new UserData("a", "password", "email");
            output = userService.registerUser(existingData);

            UserData duplicateData = new UserData("a", "password", "email");
            duplicateOutput = userService.registerUser(duplicateData);

        } catch (DataAccessException e) {
            assertEquals("Error: already taken", e.getMessage(), "Error message was not Error: already taken");
            assertNull(duplicateOutput, "incorrectly returned Auth");
        }
    }



}

package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.Objects;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    // creates new User in userDAO returns a UserData object
    public AuthData registerUser(UserData userData) throws DataAccessException {
        if (Objects.equals(userData, null)) {
            throw new DataAccessException("Error: bad request");
        }
        userDAO.addUser(userData.username(), userData.password(), userData.email());
        if (Objects.equals(userData.username(), null) || Objects.equals(userData.password(), null) || Objects.equals(userData.email(), null)) {
            throw new DataAccessException("Error: bad request");
        }
        return authDAO.createAuth(userData.username());
    }
    // create and return AuthToken
    public AuthData loginUser(UserData userData) throws DataAccessException {
        if (Objects.equals(userData, null)) {
            throw new DataAccessException("Error: unauthorized");
        }
        UserData user = userDAO.getUser(userData.username());
        if (Objects.equals(user, null)) {
            throw new DataAccessException("Error: unauthorized");
        }
        if (Objects.equals(user.password(), userData.password())) {
            return authDAO.createAuth(userData.username());
        } else {
            throw new DataAccessException("Error: unauthorized");
        }
    }
    // deletes authToken from authDAO returns null object on success
    public void logoutUser(String authToken) throws DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        if (Objects.equals(auth, null)) {
            throw new DataAccessException("Error: unauthorized");
        } else {
            authDAO.deleteAuth(authToken);
        }
    }

}

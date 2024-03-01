package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import model.results.ErrorResult;

import javax.xml.crypto.Data;
import java.util.Objects;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    // creates new User in userDAO
    public Object registerUser(UserData userData) {
        try {
            userDAO.addUser(userData.username(), userData.password(), userData.email());
            if (Objects.equals(userData.username(), null) || Objects.equals(userData.password(), null) || Objects.equals(userData.email(), null)) {
                return new ErrorResult(400, "Error: bad request");
            }
            return authDAO.createAuth(userData.username());
        } catch(DataAccessException e) {
            return new ErrorResult(500, e.getMessage());
        }
    }
    // create and return AuthToken
    public Object loginUser(UserData userData) {
        try {
            UserData user = userDAO.getUser(userData.username());
            if (Objects.equals(user.password(), userData.password())) {
                return authDAO.createAuth(userData.username());
            } else {
                return new ErrorResult(401, "Error: unauthorized");
            }
        } catch(DataAccessException e) {

        }

    }
    // deletes authToken from authDAO
    public Object logoutUser(String authToken) {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            if (Objects.equals(auth.authToken(), null)) {
                return new ErrorResult(401, "Error: unauthorized");
            } else {
                authDAO.deleteAuth(authToken);
                return "{}";
            }
        } catch (DataAccessException e) {
            return new ErrorResult(500, e.getMessage());
        }
    }

}

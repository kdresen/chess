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
    private final GameDAO gameDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public Object registerUser(UserData userData) {
        try {
            userDAO.addUser(userData.username(), userData.password(), userData.email());
            return authDAO.createAuth(userData.username());
        } catch(DataAccessException e) {
            return new ErrorResult(e.getMessage());
        }
    }

    public Object loginUser(UserData userData) {
        try {
            UserData user = userDAO.getUser(userData.username());
            if (Objects.equals(user.password(), userData.password())) {
                return authDAO.createAuth(userData.username());
            } else {
                return new ErrorResult("Error: unauthorized");
            }
        } catch (DataAccessException e) {
            return new ErrorResult(e.getMessage());
        }
    }

    public Object logoutUser(String authToken) {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            if (auth.authToken() != null) {
                authDAO.deleteAuth(authToken);
                return "{}";
            } else {
                return new ErrorResult("Error: unauthorized");
            }
        } catch (DataAccessException e) {
            return new ErrorResult(e.getMessage());
        }
    }

}

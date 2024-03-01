package service;

import dataAccess.*;
import model.results.ClearApplicationResult;
import model.results.ErrorResult;

public class ClearApplicationService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ClearApplicationService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    // removes all objects from gameDAO, userDAO, and authDAO
    public Object deleteAll() {
        try {
            userDAO.clear();
            authDAO.clear();
            gameDAO.clear();
            return new ClearApplicationResult(200, "{}");
        } catch (DataAccessException e) {
            return new ErrorResult(500, e.getMessage());
        }

    }

}

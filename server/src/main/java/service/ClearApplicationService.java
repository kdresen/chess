package service;

import dataAccess.*;

public class ClearApplicationService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ClearApplicationService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public Object deleteAll() {
        try {
            userDAO.clear();
            authDAO.clear();
            gameDAO.clear();
            return "{}";
        } catch (DataAccessException e) {
            return null;
        }

    }

}

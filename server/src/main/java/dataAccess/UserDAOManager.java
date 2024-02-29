package dataAccess;

public class UserDAOManager {

    private static UserDAO userDAOInstance;

    private UserDAOManager() {

    }

    public static UserDAO getUserDAO() {
        if (userDAOInstance == null) {
            userDAOInstance = new MemoryUserDAO();
        }
        return userDAOInstance;
    }


}

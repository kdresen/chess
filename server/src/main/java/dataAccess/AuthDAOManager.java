package dataAccess;

public class AuthDAOManager {
    private static AuthDAO authDAOInstance;

    private AuthDAOManager() {

    }

    public static AuthDAO getAuthDAO() {
        if (authDAOInstance == null) {
            authDAOInstance = new MemoryAuthDAO();
        }
        return authDAOInstance;
    }
}

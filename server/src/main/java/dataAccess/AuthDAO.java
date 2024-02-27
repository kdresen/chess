package dataAccess;

import model.AuthData;

public interface AuthDAO {

    public void createAuth(String username) throws DataAccessException;

    public AuthData getAuth(String authToken);

    public void deleteAuth(String authToken);

    public void clear();
}

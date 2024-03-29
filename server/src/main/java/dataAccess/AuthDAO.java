package dataAccess;

import model.AuthData;

public interface AuthDAO {

    public AuthData createAuth(String username);

    public AuthData getAuth(String authToken);

    public void deleteAuth(String authToken) throws DataAccessException;

    public void clear() throws DataAccessException;
}

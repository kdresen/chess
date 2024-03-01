package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    private Map<String, AuthData> authMap;

    public MemoryAuthDAO() {
        this.authMap = new HashMap<>();
    }

    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        authMap.put(authToken, auth);
        return auth;
    }

    public AuthData getAuth(String authToken) {
        return authMap.get(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData auth = getAuth(authToken);
        if (Objects.equals(authToken, auth.authToken())) {
            authMap.remove(authToken);
        } else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    @Override
    public void clear() throws DataAccessException {
        authMap.clear();
    }
}

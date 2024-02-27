package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    private Map<String, AuthData> authMap;

    public MemoryAuthDAO() {
        this.authMap = new HashMap<>();
    }

    public void createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        authMap.put(authToken, auth);
    }

    public AuthData getAuth(String authToken) {
        return authMap.get(authToken);
    }

    public void deleteAuth(String authToken) {
        authMap.remove(authToken);
    }

    @Override
    public void clear() {
        authMap.clear();
    }
}

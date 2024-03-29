package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO {
    public Map<String, UserData> userMap;

    public MemoryUserDAO() {
        // constructor for a new HashMap
        this.userMap = new HashMap<>();
    }

    public void addUser(String username, String password, String email) throws DataAccessException {
        // add new user
        UserData user = new UserData(username, password, email);
        UserData existingUser = getUser(username);
        if (existingUser == null || existingUser.username() == null) {
            userMap.put(username, user);
        } else {
            throw new DataAccessException("Error: already taken");
        }

    }

    public UserData getUser(String username) {
        // retrieve a user
        return userMap.get(username);
    }

    @Override
    public void clear() {
        // clear users
        userMap.clear();
    }
}

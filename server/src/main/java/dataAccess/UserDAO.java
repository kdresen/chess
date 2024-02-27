package dataAccess;
import model.UserData;

public interface UserDAO {

    public void addUser(String username, String password, String email) throws DataAccessException;
    public UserData getUser(String username);
    public void deleteUser(String username);
    public void clear();
}

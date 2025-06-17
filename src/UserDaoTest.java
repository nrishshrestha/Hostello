import dao.UserDao;
import model.UserData;
import model.ResetPasswordRequest;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDaoTest {
    
    private UserDao userDao = new UserDao();
    
    @Test
    public void testRegister() {
        UserData testUser = new UserData();
        testUser.setUsername("testUser");
        testUser.setEmail("test@test.com");
        testUser.setPassword("password123");
        
        boolean result = userDao.register(testUser);
        assertTrue(result);
    }
}

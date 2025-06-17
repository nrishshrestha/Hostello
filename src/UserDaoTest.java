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
    @Test 
    public void testCheckEmail() {
        String email = "test@test.com";
        boolean exists = userDao.checkEmail(email);
        assertTrue(exists);
        
        String nonExistentEmail = "nonexistent@test.com";
        boolean notExists = userDao.checkEmail(nonExistentEmail);
        assertFalse(notExists);
    }
}

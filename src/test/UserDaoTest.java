package test;

import dao.UserDao;
import model.UserData;
import model.ResetPasswordRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDaoTest {
    
    private UserDao userDao;
    private UserData testUser;

    
    @Test
    public void testRegister() {
        boolean result = userDao.register(testUser);
        assertTrue("Registration should succeed with valid user data", result);
        
        // Test duplicate registration
        boolean duplicateResult = userDao.register(testUser);
        assertFalse("Registration should fail for duplicate email", duplicateResult);
    }
    
    @Test 
    public void testCheckEmail() {
        // First register a user to check
        userDao.register(testUser);
        
        boolean exists = userDao.checkEmail("test@test.com");
        assertTrue("Should find existing email", exists);
        
        boolean notExists = userDao.checkEmail("nonexistent@test.com");
        assertFalse("Should not find non-existent email", notExists);
    }
    
    
    @Test
    public void testLogin() {
        // First register a user
        userDao.register(testUser);
        
        UserData user = userDao.login("test@test.com", "password123");
        assertNotNull("Login should succeed with correct credentials", user);
        assertEquals("Email should match", "test@test.com", user.getEmail());
        
        UserData invalidUser = userDao.login("wrong@email.com", "wrongpass");
        assertNull("Login should fail with incorrect credentials", invalidUser);
    }
}

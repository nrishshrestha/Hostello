//import dao.UserDao;
//import model.UserData;
//import model.ResetPasswordRequest;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//public class UserDaoTest {
//    
//    private UserDao userDao = new UserDao();
//    
//    @Test
//    public void testRegister() {
//        UserData testUser = new UserData();
//        testUser.setUsername("testUser");
//        testUser.setEmail("test@test.com");
//        testUser.setPassword("password123");
//        
//        boolean result = userDao.register(testUser);
//        assertTrue(result);
//    }
//    @Test 
//    public void testCheckEmail() {
//        String email = "test@test.com";
//        boolean exists = userDao.checkEmail(email);
//        assertTrue(exists);
//        
//        String nonExistentEmail = "nonexistent@test.com";
//        boolean notExists = userDao.checkEmail(nonExistentEmail);
//        assertFalse(notExists);
//    }
//    @Test
//    public void testResetPassword() {
//        ResetPasswordRequest request = new ResetPasswordRequest();
//        request.setEmail("test@test.com");
//        request.setPassword("newpassword123");
//        
//        boolean result = userDao.resetPassword(request);
//        assertTrue(result);
//    }
//    
//    @Test
//    public void testLogin() {
//        UserData user = userDao.login("test@test.com", "password123");
//        assertNotNull(user);
//        assertEquals("test@test.com", user.getEmail());
//        
//        UserData invalidUser = userDao.login("wrong@email.com", "wrongpass");
//        assertNull(invalidUser);
//    }
//}

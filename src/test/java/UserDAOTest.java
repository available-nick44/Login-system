import model.IUserDAO;
import model.User;
import model.UserDAO;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserDAOTest {
    private static IUserDAO userDAO;
    private User exampleUser;

    @Before
    public void setUp() {
        userDAO = new UserDAO();
        exampleUser = new User("login1", "passWord#1", "user1@gmail.com", "123456789");
        userDAO.save(exampleUser);
        userDAO.save(new User("login2", "passWord#2", "user2@gmail.com", "123456788"));
        userDAO.save(new User("login3", "passWord#3", "user3@gmail.com", "123456787"));
    }

    @Test
    public void testContainsLogin() {
        assertTrue(userDAO.containsLogin("login1"));
        assertTrue(userDAO.containsLogin("login2"));
        assertTrue(userDAO.containsLogin("login3"));
    }

    @Test
    public void testContainsLoginNonExistentUser() {
        assertFalse(userDAO.containsLogin("login6"));
    }

    @Test
    public void testFindByLogin() {
        assertEquals(userDAO.findByLogin(exampleUser.getLogin()), Optional.of(exampleUser));
        assertEquals(userDAO.findByLogin("login4"), Optional.empty());
    }

    @Test
    public void testCheckPassword() {
        assertTrue(userDAO.checkPassword("login1", "passWord#1"));
        assertTrue(userDAO.checkPassword("login2", "passWord#2"));
        assertTrue(userDAO.checkPassword("login3", "passWord#3"));
        assertFalse(userDAO.checkPassword("login2", "passWord#3"));
        assertFalse(userDAO.checkPassword("login1", "passWord#3"));
    }

    @Test
    public void testCheckPasswordNonExistentUser() {
        assertFalse(userDAO.checkPassword("login4", "passWord#4"));
    }

    @Test
    public void testSaveUser() {
        assertTrue(userDAO.save(new User("login4", "passWord#4", "user4@gmail.com", "123456787")));
        assertFalse(userDAO.save(new User("login4", "passWord#4", "user4@gmail.com", "123456787")));
    }

    @Test
    public void testUpdatePhoneNo() {
        assertTrue(userDAO.updatePhoneNo("login3", "123789456"));
        assertFalse(userDAO.updatePhoneNo("login4", "123789456"));
    }

    @Test
    public void testUpdateEmail() {
        assertTrue(userDAO.updateEmail("login3", "user32@gmail.com"));
        assertFalse(userDAO.updateEmail("login4", "user32@gmail.com"));
    }

    @Test
    public void testUpdatePassword() {
        assertTrue(userDAO.updatePassword("login3", "passWord#4"));
        assertFalse(userDAO.updatePassword("login4", "passWord#4"));
    }

}

package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mudan.ShopApplication;
import ru.mudan.models.User;
import ru.mudan.repositories.UserRepository;
import ru.mudan.services.UserService;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ShopApplication.class)
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    public void clearTable(){
        userRepository.deleteAll();
    }
    @Test
    public void sizeOfTableEquals0(){
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserValid(){
        User testUser = getTestUser();
        userService.create(testUser);
        assertEquals(1,userRepository.findAll().size());
    }
    @Test
    public void createUserUsernameEqualsNull(){
        User testUser = getTestUser();
        testUser.setUsername(null);
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserUsernameIsBlank(){
        User testUser = getTestUser();
        testUser.setUsername("");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserFirstnameEqualsNull(){
        User testUser = getTestUser();
        testUser.setFirstname(null);
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserFirstnameIsBlank(){
        User testUser = getTestUser();
        testUser.setFirstname(null);
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserLastnameEqualsNull(){
        User testUser = getTestUser();
        testUser.setLastname(null);
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserLastnameIsBlank(){
        User testUser = getTestUser();
        testUser.setLastname("");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserEmailEqualsNull(){
        User testUser = getTestUser();
        testUser.setEmail(null);
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserEmailIsBlank(){
        User testUser = getTestUser();
        testUser.setEmail("");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserEmailIncorrectFormat(){
        User testUser = getTestUser();
        testUser.setEmail("test_email");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserPasswordEqualsNull(){
        User testUser = getTestUser();
        testUser.setPassword(null);
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserPasswordIsBlank(){
        User testUser = getTestUser();
        testUser.setPassword("");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserUsernameLength5(){
        User testUser = getTestUser();
        testUser.setUsername("test1");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserUsernameLength16(){
        User testUser = getTestUser();
        testUser.setUsername("test_test_test_1");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserFirstnameLength1(){
        User testUser = getTestUser();
        testUser.setUsername("Т");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserFirstnameLength21(){
        User testUser = getTestUser();
        testUser.setUsername("ТестТестТестТестТестТ");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserLastnameLength1(){
        User testUser = getTestUser();
        testUser.setLastname("Т");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void createUserLastnameLength21(){
        User testUser = getTestUser();
        testUser.setLastname("ТестТестТестТестТестТ");
        assertThrows(RuntimeException.class,()->userService.create(testUser));
        assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void findByUsernameExists(){
        User testUser = getTestUser();
        userService.create(testUser);
        assertEquals(1,userRepository.findAll().size());
        User resultUserByUsername = userService.getByUsername(testUser.getUsername());
        assertNotNull(resultUserByUsername);
        assertEquals(testUser.getUsername(),resultUserByUsername.getUsername());
        assertEquals(testUser.getFirstname(),resultUserByUsername.getFirstname());
        assertEquals(testUser.getLastname(),resultUserByUsername.getLastname());
        assertEquals(testUser.getEmail(),resultUserByUsername.getEmail());
        assertEquals(testUser.getPassword(),resultUserByUsername.getPassword());
    }
    @Test
    public void findByUsernameNotExists(){
        assertThrows(RuntimeException.class,()->userService.getByUsername("test_username"));
    }
    @Test
    public void emailAlreadyExists(){
        User testUser = getTestUser();
        userService.create(testUser);
        assertTrue(userService.emailAlreadyExists(testUser.getEmail()));
    }
    @Test
    public void emailDoesNotExists(){
        assertFalse(userService.emailAlreadyExists("test@mail.ru"));
    }
    @Test
    public void usernameAlreadyExists(){
        User testUser = getTestUser();
        userService.create(testUser);
        assertTrue(userService.usernameAlreadyExists(testUser.getUsername()));
    }
    @Test
    public void usernameDoesNotExists(){
        assertFalse(userService.usernameAlreadyExists("test_username"));
    }
    private User getTestUser(){
        User user = new User();
        user.setFirstname("Тестовое имя");
        user.setLastname("Тестовая фамилия");
        user.setUsername("test username");
        user.setEmail("test@mail.ru");
        user.setPassword("password_test");
        return user;
    }
}

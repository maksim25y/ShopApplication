import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mudan.ShopApplication;
import ru.mudan.payload.request.SignUpRequest;
import ru.mudan.services.AuthenticationService;
import ru.mudan.services.UserService;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ShopApplication.class)
public class AuthenticationServiceTests {
    @Autowired
    private AuthenticationService authenticationService;
    @Mock
    private UserService userService;
    @Test
    public void signUp(){
        SignUpRequest testSignUpRequest = getTestSignUpRequest();
        authenticationService.signUp(testSignUpRequest);
        verify(userService, times(1));
    }
    private SignUpRequest getTestSignUpRequest(){
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstname("Тестовое имя");
        signUpRequest.setLastname("Тестовая фамилия");
        signUpRequest.setUsername("test username");
        signUpRequest.setEmail("test@mail.ru");
        signUpRequest.setPassword("password_test");
        return signUpRequest;
    }

}

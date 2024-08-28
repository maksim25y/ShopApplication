import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.mudan.controllers.AuthController;
import ru.mudan.payload.request.SignUpRequest;
import ru.mudan.services.AuthenticationService;
import ru.mudan.validation.ResponseErrorValidation;
import ru.mudan.validation.SignUpValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {
    private final String AUTH_URL = "/auth";
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private ResponseErrorValidation responseErrorValidation;
    @Mock
    private SignUpValidator signUpValidator;
    @InjectMocks
    private AuthController authController;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }
    @Test
    public void whenRegisterUserWithValidParametersReturnMessageThatRegisterIsSuccessful() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        String requestContent = serializeToJson(signUpRequest);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertEquals("Пользователь успешно зарегистрирован",responseBody);
    }
    private String serializeToJson(SignUpRequest signUpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(signUpRequest);
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

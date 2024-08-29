import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import ru.mudan.controllers.AuthController;
import ru.mudan.payload.request.SignUpRequest;
import ru.mudan.services.AuthenticationService;
import ru.mudan.validation.ResponseErrorValidation;
import ru.mudan.validation.SignUpValidator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
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
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8).build();
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

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "message");

        assertEquals("Пользователь успешно зарегистрирован", message);
    }
    @Test
    public void whenRegisterUserWithUsernameEqualsNullReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setUsername(null);
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("username","username не должен быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "username");

        assertEquals("username не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithUsernameIsBlankReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setUsername("");
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("username","username не должен быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "username");

        assertEquals("username не должен быть пустым или состоять только из пробелов",message);
    }
    @Test
    public void whenRegisterUserWithUsernameThatAlreadyExistsReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("username","Пользователь с данным username уже существует");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "username");

        assertEquals("Пользователь с данным username уже существует",message);
    }
    @Test
    public void whenRegisterUserWithFirstnameEqualsNullReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setFirstname(null);
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("firstname","Имя не должно быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "firstname");

        assertEquals("Имя не должно быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithFirstnameIsBlankReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setFirstname("");
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("firstname","Имя не должно быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "firstname");

        assertEquals("Имя не должно быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithLastnameEqualsNullReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname(null);
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("lastname","Фамилия не должна быть пустой или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "lastname");

        assertEquals("Фамилия не должна быть пустой или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithLastnameIsBlankReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname("");
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("lastname","Фамилия не должна быть пустой или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "lastname");

        assertEquals("Фамилия не должна быть пустой или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithEmailEqualsNullReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setEmail(null);
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("email","email не должен быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "email");

        assertEquals("email не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithEmailIsBlankReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setEmail("");
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("email","email не должен быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "email");

        assertEquals("email не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithEmailThatAlreadyExistsReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("email","Пользователь с данным email уже существует");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "email");

        assertEquals("Пользователь с данным email уже существует", message);
    }
    @Test
    public void whenRegisterUserWithPasswordEqualsNullReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setPassword(null);
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("password","Пароль не должен быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "password");

        assertEquals("Пароль не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithPasswordIsBlankReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setPassword("");
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("password","Пароль не должен быть пустым или состоять только из пробелов");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "password");

        assertEquals("Пароль не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void whenRegisterUserWithUsernameWithLength5ReturnMessageThatRegisterIsBad() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setUsername("Тест");
        String requestContent = serializeToJson(signUpRequest);

        Map<String,String>expectedResponse = Map.of("username","username должен быть от 6 до 15 символов длиной");
        ResponseEntity<Object>responseEntity = new ResponseEntity<>(expectedResponse,HttpStatus.BAD_REQUEST);

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = getResponseBodyAsString(result);
        String message = getMessageFromJSOM(responseBody, "username");

        assertEquals("username должен быть от 6 до 15 символов длиной", message);
    }
    private String getMessageFromJSOM(String responseBody,String path){
        return JsonPath.read(responseBody, String.format("$.%s",path));
    }
    private String getResponseBodyAsString(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString(StandardCharsets.UTF_8);
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

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
    public void registerValidRequest() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        String requestContent = serializeToJson(signUpRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andReturn();

        String message = getResultFromResponseBody(result, "message");

        assertEquals("Пользователь успешно зарегистрирован", message);
    }
    @Test
    public void registerUsernameIsNull() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setUsername(null);
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("username", "username не должен быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "username");

        assertEquals("username не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void registerUsernameIsBlank() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setUsername("");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("username", "username не должен быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "username");

        assertEquals("username не должен быть пустым или состоять только из пробелов",message);
    }
    @Test
    public void registerUsernameAlreadyExists() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("username", "Пользователь с данным username уже существует");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "username");

        assertEquals("Пользователь с данным username уже существует",message);
    }
    @Test
    public void registerFirstnameIsNull() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setFirstname(null);
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("firstname", "Имя не должно быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "firstname");

        assertEquals("Имя не должно быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void registerFirstnameIsBlank() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setFirstname("");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("firstname", "Имя не должно быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "firstname");

        assertEquals("Имя не должно быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void registerLastnameIsNull() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname(null);
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("lastname", "Фамилия не должна быть пустой или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "lastname");

        assertEquals("Фамилия не должна быть пустой или состоять только из пробелов", message);
    }
    @Test
    public void registerLastnameIsBlank() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname("");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("lastname", "Фамилия не должна быть пустой или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "lastname");

        assertEquals("Фамилия не должна быть пустой или состоять только из пробелов", message);
    }
    @Test
    public void registerEmailIsNull() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setEmail(null);
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("email", "email не должен быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "email");

        assertEquals("email не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void registerEmailIsBlank() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setEmail("");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("email", "email не должен быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "email");

        assertEquals("email не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void registerEmailAlreadyExists() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("email", "Пользователь с данным email уже существует");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "email");

        assertEquals("Пользователь с данным email уже существует", message);
    }
    @Test
    public void registerPasswordIsNull() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setPassword(null);
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("password", "Пароль не должен быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "password");

        assertEquals("Пароль не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void registerPasswordIsBlank() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setPassword("");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("password", "Пароль не должен быть пустым или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "password");

        assertEquals("Пароль не должен быть пустым или состоять только из пробелов", message);
    }
    @Test
    public void registerUsernameLengthEquals4() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setUsername("Тест");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("username", "username должен быть от 6 до 15 символов длиной");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "username");

        assertEquals("username должен быть от 6 до 15 символов длиной", message);
    }
    @Test
    public void registerUsernameLengthEquals16() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setUsername("ТестТестТестТест");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("username", "username должен быть от 6 до 15 символов длиной");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "username");

        assertEquals("username должен быть от 6 до 15 символов длиной", message);
    }
    @Test
    public void registerFirstnameLengthEquals1() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setFirstname("Т");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("firstname", "Имя должно быть от 2 до 20 символов длиной");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "firstname");

        assertEquals("Имя должно быть от 2 до 20 символов длиной", message);
    }

    @Test
    public void registerFirstnameLengthEquals21() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setFirstname("ТестТестТестТестТестТ");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("firstname", "Имя должно быть от 2 до 20 символов длиной");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "firstname");

        assertEquals("Имя должно быть от 2 до 20 символов длиной", message);
    }
    @Test
    public void registerLastnameLengthEquals1() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname("Т");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("lastname", "Фамилия не должна быть пустой или состоять только из пробелов");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "lastname");

        assertEquals("Фамилия не должна быть пустой или состоять только из пробелов", message);
    }
    @Test
    public void registerLastnameLengthEquals21() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname("ТестТестТестТестТестТ");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("lastname", "Фамилия должна быть от 2 до 20 символов длиной");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "lastname");

        assertEquals("Фамилия должна быть от 2 до 20 символов длиной", message);
    }
    @Test
    public void registerEmailWithNotCorrectFormat() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setEmail("test");;
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("email", "email должен быть в формате example@test.com");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "email");

        assertEquals("email должен быть в формате example@test.com", message);
    }
    @Test
    public void registerPasswordLengthEquals7() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname("test123");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("password", "Пароль должен быть от 8 до 20 символов длиной");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "password");

        assertEquals("Пароль должен быть от 8 до 20 символов длиной", message);
    }
    @Test
    public void registerPasswordLengthEquals21() throws Exception {
        SignUpRequest signUpRequest = getTestSignUpRequest();
        signUpRequest.setLastname("test_password_test123");
        String requestContent = serializeToJson(signUpRequest);

        ResponseEntity<Object> responseEntity = getExpectedResponseEntity("password", "Пароль должен быть от 8 до 20 символов длиной");

        when(responseErrorValidation.mapValidationService(any(BindingResult.class))).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = getResultFromResponseBody(result, "password");

        assertEquals("Пароль должен быть от 8 до 20 символов длиной", message);
    }

    private String getResultFromResponseBody(MvcResult result, String firstname) throws UnsupportedEncodingException {
        String responseBody = getResponseBodyAsString(result);
        return getMessageFromJSOM(responseBody, firstname);
    }

    private static ResponseEntity<Object> getExpectedResponseEntity(String firstname, String v1) {
        Map<String, String> expectedResponse = Map.of(firstname, v1);
        return new ResponseEntity<>(expectedResponse, HttpStatus.BAD_REQUEST);
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

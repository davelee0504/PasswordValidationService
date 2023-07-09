package idv.dave.passwordvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.dave.passwordvalidation.controller.PasswordValidationController;
import idv.dave.passwordvalidation.model.Credential;
import idv.dave.passwordvalidation.model.ValidationResult;
import idv.dave.passwordvalidation.service.PasswordValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PasswordValidationControllerTest {

    private MockMvc mockMvc;

    @Mock
    PasswordValidationServiceImpl mockedPasswordValidationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        PasswordValidationController passwordValidationController = new PasswordValidationController(mockedPasswordValidationService);
        mockMvc = MockMvcBuilders.standaloneSetup(passwordValidationController).build();
    }

    Credential credential = new Credential("abc123");

    @Test
    public void expectBadRequestWhenInvalidPassword() throws Exception {
        when(mockedPasswordValidationService.validatePassword(credential.getPassword()))
                .thenReturn(new ValidationResult(false));
        String url = "/password/validate";
        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(credential))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void expectOkWhenValidaPassword() throws Exception {
        when(mockedPasswordValidationService.validatePassword(credential.getPassword()))
                .thenReturn(new ValidationResult(true));
        String url = "/password/validate";
        this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(credential))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void expectMethodNotAllowedWhenPerformGet() throws Exception {
        // try the other http methods other than POST
        String url = "/password/validate";
        this.mockMvc.perform(get(url))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void expectBadRequestForInvalidJsonParse() throws Exception {
        String url = "/password/validate";
        String invalidJsonString = "{\"password\": null01}";
        this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(invalidJsonString)
                )
                .andExpect(status().isBadRequest());
    }
}

package idv.dave.passwordvalidation;

import idv.dave.passwordvalidation.controller.PasswordValidationController;
import idv.dave.passwordvalidation.model.ValidationResult;
import idv.dave.passwordvalidation.service.PasswordValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class PasswordValidationControllerTest {

    private MockMvc mockMvc;

    @Mock
    PasswordValidationServiceImpl mockedPasswordValidationService;

    @BeforeEach
    public void init() {
        PasswordValidationController passwordValidationController = new PasswordValidationController(mockedPasswordValidationService);
        mockMvc = MockMvcBuilders.standaloneSetup(passwordValidationController).build();
    }

    String randomPassword = "abc123";

    @Test
    public void shouldReturnNotAcceptable() throws Exception {
        when(mockedPasswordValidationService.validatePassword(randomPassword))
                .thenReturn(new ValidationResult(false));
        String url = "/password/validate?s=";
        this.mockMvc.perform(get(url + randomPassword)).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnOk() throws Exception {
        when(mockedPasswordValidationService.validatePassword(randomPassword))
                .thenReturn(new ValidationResult(true));
        String url = "/password/validate?s=";
        this.mockMvc.perform(get(url + randomPassword)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequest() throws Exception {
        // remove the mandatory parameter s
        String url = "/password/validate";
        this.mockMvc.perform(get(url))
                .andExpect(status().isBadRequest());
    }
}

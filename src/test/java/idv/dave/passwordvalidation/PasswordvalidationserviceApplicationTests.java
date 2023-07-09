package idv.dave.passwordvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.dave.passwordvalidation.controller.PasswordValidationController;
import idv.dave.passwordvalidation.model.CharacterLimitRule;
import idv.dave.passwordvalidation.model.Credential;
import idv.dave.passwordvalidation.model.PasswordLengthRule;
import idv.dave.passwordvalidation.model.RepeatedCharacterSequenceRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

@SpringBootTest
class PasswordValidationServiceApplicationTest {

    @Autowired
    PasswordValidationController passwordValidationController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void validPasswordIntegrationTest() throws Exception {
        Credential credential = new Credential("abc123");
        String url = "/password/validate";
        this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(credential))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid").value("true"))
                .andExpect(jsonPath("$.messages.length()").value("0"));
    }

    @Test
    public void invalidPasswordIntegrationTest() throws Exception {
        Credential credential = new Credential("cBcB");
        String url = "/password/validate";
        this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(credential))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isValid").value("false"))
                .andExpect(jsonPath("$.messages.length()").value("3"))
                .andExpect(jsonPath("$.messages[*]").value(hasItem(CharacterLimitRule.errorMessage)))
                .andExpect(jsonPath("$.messages[*]").value(hasItem(PasswordLengthRule.errorMessage)))
                .andExpect(jsonPath("$.messages[*]").value(hasItem(RepeatedCharacterSequenceRule.errorMessage)));
    }


}

package idv.dave.passwordvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.dave.passwordvalidation.model.CharacterLimitRule;
import idv.dave.passwordvalidation.model.Credential;
import idv.dave.passwordvalidation.model.PasswordLengthRule;
import idv.dave.passwordvalidation.model.RepeatedCharacterSequenceRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordValidationServiceApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void validPasswordIntegrationTest() throws Exception {
        Credential credential = new Credential("abc123");
        String url = "/password/validate";
        this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(credential))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid").value(is(true)))
                .andExpect(jsonPath("$.messages.length()").value(is(0)));
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
                .andExpect(jsonPath("$.isValid").value(is(false)))
                .andExpect(jsonPath("$.messages.length()").value(is(3)))
                .andExpect(jsonPath("$.messages[*]").value(hasItem(CharacterLimitRule.errorMessage)))
                .andExpect(jsonPath("$.messages[*]").value(hasItem(PasswordLengthRule.errorMessage)))
                .andExpect(jsonPath("$.messages[*]").value(hasItem(RepeatedCharacterSequenceRule.errorMessage)));
    }


}

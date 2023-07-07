package idv.dave.passwordvalidation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@JsonPropertyOrder({"isValid", "messages"})
public class ValidationResult {

    private boolean isValid = true;

    @JsonProperty("messages")
    List<String> messages = new ArrayList<>();

    public ValidationResult(boolean isValid) {
        this.isValid = isValid;
    }

    public ValidationResult(boolean isValid, List<String> messages) {
        this.isValid = isValid;
        this.messages = messages;

        if(messages != null && !messages.isEmpty())
            this.isValid = false;
    }

    public void addMessage(String message) {
        this.isValid = false;
        this.messages.add(message);
    }

    public void addAllMessages(List<String> messages) {
        this.isValid = false;
        this.messages.addAll(messages);
    }

    @JsonProperty("isValid")
    public boolean isValid() {
        return isValid;
    }
}

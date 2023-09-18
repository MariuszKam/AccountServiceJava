package account.businesslayer.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PasswordChanger(@NotBlank
                              @JsonProperty("new_password")
                              String newPassword) {
}

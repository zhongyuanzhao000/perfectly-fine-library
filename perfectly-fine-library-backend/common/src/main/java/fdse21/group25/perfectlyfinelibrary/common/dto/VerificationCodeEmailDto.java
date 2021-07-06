package fdse21.group25.perfectlyfinelibrary.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeEmailDto {
    /**
     * Email address to send.
     */
    private String email;
    /**
     * Verification code. Must not be {@code null};
     */
    private String verificationCode;
}

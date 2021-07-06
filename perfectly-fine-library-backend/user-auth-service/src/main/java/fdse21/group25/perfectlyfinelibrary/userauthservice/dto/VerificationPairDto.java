package fdse21.group25.perfectlyfinelibrary.userauthservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationPairDto {
    private String verificationId;
    private String verificationCode;
}

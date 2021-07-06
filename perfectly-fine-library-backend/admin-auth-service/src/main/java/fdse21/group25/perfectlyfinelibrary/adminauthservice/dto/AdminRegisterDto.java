package fdse21.group25.perfectlyfinelibrary.adminauthservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterDto {
    private String username;
    private String password;
}

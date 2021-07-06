package fdse21.group25.perfectlyfinelibrary.userauthservice.dto;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    private String username;
    private String password;
    private Role role;
}

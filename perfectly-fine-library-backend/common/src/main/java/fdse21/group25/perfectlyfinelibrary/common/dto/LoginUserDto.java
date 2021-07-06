package fdse21.group25.perfectlyfinelibrary.common.dto;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Current user info stored in JWT. This class should always request from user
 * auth service.
 * 
 * @see {@link fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    private String username;
    private Role role;
}

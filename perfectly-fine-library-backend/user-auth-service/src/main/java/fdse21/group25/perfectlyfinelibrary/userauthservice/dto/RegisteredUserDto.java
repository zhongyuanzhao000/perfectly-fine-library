package fdse21.group25.perfectlyfinelibrary.userauthservice.dto;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUserDto {
    private String username;
    private Role role;
}

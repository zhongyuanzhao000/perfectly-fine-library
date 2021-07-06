package fdse21.group25.perfectlyfinelibrary.adminauthservice.dto;

import fdse21.group25.perfectlyfinelibrary.common.domain.admin.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredAdminDto {
    private String username;
    private Role role;
}

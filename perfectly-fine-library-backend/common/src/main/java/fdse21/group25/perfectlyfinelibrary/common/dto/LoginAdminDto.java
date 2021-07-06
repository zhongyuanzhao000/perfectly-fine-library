package fdse21.group25.perfectlyfinelibrary.common.dto;

import fdse21.group25.perfectlyfinelibrary.common.domain.Library;
import fdse21.group25.perfectlyfinelibrary.common.domain.admin.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see {@link fdse21.group25.perfectlyfinelibrary.common.client.AdminAuthServiceClient}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAdminDto {
    private String username;
    private Role role;
    private Library library;
}

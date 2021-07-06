package fdse21.group25.perfectlyfinelibrary.common.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Id
    private String username;
    private Role role;
    private Double fine;

    public UserDto(String username, Role role) {
        this(username, role, null);
    }
}

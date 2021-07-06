package fdse21.group25.perfectlyfinelibrary.userauthservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Verification {
    @Id
    private String id;
    private String code;
    private String username;
    private String encodedPassword;
    private Role role;
}

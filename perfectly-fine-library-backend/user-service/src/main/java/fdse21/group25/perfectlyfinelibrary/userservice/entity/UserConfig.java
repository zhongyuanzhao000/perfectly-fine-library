package fdse21.group25.perfectlyfinelibrary.userservice.entity;

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
public class UserConfig {
    @Id
    private Role role;
    private int maxBorrowNumber;

    public static final UserConfig DEFAULT_CONFIG = new UserConfig(null, 1);
}

package fdse21.group25.perfectlyfinelibrary.copyservice.entity;

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
public class CopyConfig {
    @Id
    private Role role;

    private long borrowExpiration;
    private long reserveExpiration;

    public static final CopyConfig DEFAULT_CONFIG = new CopyConfig(null, 1000 * 60, 1000 * 60);
}

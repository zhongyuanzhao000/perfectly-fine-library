package fdse21.group25.perfectlyfinelibrary.adminservice.entity;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {
    @Id
    private Role role;
    private int maxBorrowNumber;
    private long borrowExpiration;
    private long reserveExpiration;
}

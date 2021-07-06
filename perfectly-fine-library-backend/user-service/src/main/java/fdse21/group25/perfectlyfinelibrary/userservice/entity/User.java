package fdse21.group25.perfectlyfinelibrary.userservice.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Version;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Version
    private Long version;
    @Id
    private String username;
    private Role role;
    private double fine;
    private int credit;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> borrowedCopyIds;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> reservedCopyIds;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> orderIds;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> borrowedBookIds;

    public User(String username, Role role) {
        this(null, username, role, 0, 100, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
}

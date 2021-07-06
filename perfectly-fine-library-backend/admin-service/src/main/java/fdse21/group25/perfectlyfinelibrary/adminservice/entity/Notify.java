package fdse21.group25.perfectlyfinelibrary.adminservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notify {
    @Id
    @GeneratedValue
    private Long notifyId;

    public enum State {
        Creating, QueryingCopy, QueryingUser, Finished
    }

    private State state;
}
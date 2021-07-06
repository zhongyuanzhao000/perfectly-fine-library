package fdse21.group25.perfectlyfinelibrary.common.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import fdse21.group25.perfectlyfinelibrary.common.domain.copy.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyDto {
    @Id
    private Long copyId;
    private Long bookId;
    private Status status;
    /**
     * User who operates the copy. {@code null} if no such user,
     * {@code Status.Available} for example.
     */
    private String username;
}

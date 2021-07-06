package fdse21.group25.perfectlyfinelibrary.common.dto;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDto {
    private Role role;
    private Integer maxBorrowNumber;
    private Long borrowExpiration;
    private Long reserveExpiration;
}

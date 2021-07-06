package fdse21.group25.perfectlyfinelibrary.copyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddedCopyDto {
    private Long bookId;
    private int number;
}

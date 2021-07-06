package fdse21.group25.perfectlyfinelibrary.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDto {
    private String message;

    public ErrorMessageDto(Exception e) {
        this(e.getMessage());
    }
}

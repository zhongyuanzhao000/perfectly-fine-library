package fdse21.group25.perfectlyfinelibrary.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private String invoke_id;
    private String uid;
    private double amount;
}

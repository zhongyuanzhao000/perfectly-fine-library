package fdse21.group25.perfectlyfinelibrary.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeEmailDto {
    /**
     * Email address to send.
     */
    private String email;
    /**
     * Fine to pay. {@code null} if no fine.
     */
    private Double fine;
    /**
     * Reserved copies have expired. {@code null} if none.
     */
    private List<String> expiredReserve;
    /**
     * Borrowed copies have expired. {@code null} if none.
     */
    private List<String> expiredBorrow;
}

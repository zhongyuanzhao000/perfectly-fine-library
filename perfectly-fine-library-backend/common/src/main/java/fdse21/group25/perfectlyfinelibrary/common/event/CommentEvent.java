package fdse21.group25.perfectlyfinelibrary.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEvent {
    private Long commentId;
    private Long bookId;
    private String username;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserReply {
        public enum Type {
            Approved, Rejected
        }

        private Type type;
        private Long commentId;
        private String message;
    }
}
package fdse21.group25.perfectlyfinelibrary.common.event;

import java.util.Date;

import fdse21.group25.perfectlyfinelibrary.common.domain.Library;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    public enum Type {
        Creating, Rejecting, Approving
    }

    public enum Operation {
        Reserve, Borrow, Return, Damage, Lose
    }

    private Type eventType;
    private Date timestamp;
    private Long orderId;
    private Long copyId;
    private Long bookId;
    private String username;
    private Role role;
    private Library library;
    private Operation operation;
    /**
     * <code>null</code> for <code>ORDER_CREATING</code> and
     * <code>ORDER_REJECTED</code>
     */
    private Double fine;
    /**
     * <code>null</code> for <code>ORDER_CREATING</code> and
     * <code>ORDER_REJECTED</code>
     */
    private Integer credit;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CopyReply {
        public enum Type {
            Approved, Rejected
        }

        private Type eventType;
        private Long orderId;
        private Long bookId;
        /**
         * Not <code>null</code> only when <code>COPY_APPROVED</code> and
         * <code>OrderEvent.operation</code> is <code>Return</code>
         */
        private Boolean expired;
        private String username;
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserReply {
        public enum Type {
            Approved, Rejected
        }

        private Type eventType;
        private Long orderId;
        private Role role;
        private String message;
    }

}

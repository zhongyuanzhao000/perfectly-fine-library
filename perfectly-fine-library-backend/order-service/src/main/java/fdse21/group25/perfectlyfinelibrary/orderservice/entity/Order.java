package fdse21.group25.perfectlyfinelibrary.orderservice.entity;

import fdse21.group25.perfectlyfinelibrary.common.domain.Library;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.Operation;
import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    public enum State {
        Creating, ApprovingCopy, ApprovingUser, Approved, Rejected
    }

    @Id
    @GeneratedValue
    private Long orderId;
    private State state;
    private Date timestamp;
    private Long copyId;
    private Long bookId;
    private String adminUsername;
    private String username;
    private Role role;
    private Library library;
    private Operation operation;
    private Double fine;
    private Integer credit;

    public OrderEvent asEvent() {
        OrderEvent event = new OrderEvent(null, timestamp, orderId, copyId, bookId, username, role, library, operation,
                fine, credit);
        switch (state) {
            case Approved:
                event.setEventType(OrderEvent.Type.Approving);
                break;
            case ApprovingCopy:
            case ApprovingUser:
                event = null;
                break;
            case Creating:
                event.setEventType(OrderEvent.Type.Creating);
                break;
            case Rejected:
                event.setEventType(OrderEvent.Type.Rejecting);
                break;
            default:
                break;
        }
        return event;
    }

    public Order formatRequest() throws BadRequestException {
        orderId = null;
        state = null;
        timestamp = new Date(System.currentTimeMillis());
        Assert.notNull(copyId, () -> new BadRequestException("copy id not found"));
        Assert.notNull(operation, () -> new BadRequestException("operation not found"));
        fine = null;
        credit = null;
        switch (operation) {
            case Borrow:
                Assert.notNull(username, () -> new BadRequestException("username not found"));
                break;
            case Damage:
                username = null;
                break;
            case Lose:
                username = null;
                break;
            case Reserve:
                adminUsername = null;
                library = null;
                break;
            case Return:
                username = null;
                break;
            default:
                break;
        }
        return this;
    }

}
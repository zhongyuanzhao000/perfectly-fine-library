package fdse21.group25.perfectlyfinelibrary.userservice.service;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.UserReply;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.UserReply.Type;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.UserConfig;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserConfigRepository;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserRepository;

@Service
public class OrderEventServiceImpl implements OrderEventService {
    private final UserRepository userRepository;
    private final UserConfigRepository userConfigRepository;

    public OrderEventServiceImpl(UserRepository userRepository, UserConfigRepository userConfigRepository) {
        this.userRepository = userRepository;
        this.userConfigRepository = userConfigRepository;
    }

    @Override
    public UserReply handleOrderEvent(OrderEvent event) {
        UserReply reply = null;
        User user = null;
        if (null != event.getUsername())
            user = userRepository.findById(event.getUsername()).orElse(null);
        switch (event.getEventType()) {
            case Approving:
                handleApprovingEvent(event, user);
                break;
            case Creating:
                reply = handleCreatingEvent(event, user);
                break;
            case Rejecting:
                handleRejectingEvent(event, user);
                break;
            default:
                break;
        }
        if (user != null)
            userRepository.save(user);
        return reply;
    }

    private void handleApprovingEvent(OrderEvent event, User user) {
        user.getOrderIds().add(event.getOrderId());
        if (event.getCredit() != null)
            user.setCredit(user.getCredit() - event.getCredit());
        if (event.getFine() != null)
            user.setFine(user.getFine() + event.getFine());
        switch (event.getOperation()) {
            case Reserve:
                break;
            case Borrow:
                user.getReservedCopyIds().remove(event.getCopyId());
                break;
            case Damage:
            case Lose:
            case Return:
                user.getBorrowedCopyIds().remove(event.getCopyId());
                user.getBorrowedBookIds().add(event.getBookId());
                break;
        }
    }

    private UserReply handleCreatingEvent(OrderEvent event, User user) {
        try {
            switch (event.getOperation()) {
                case Borrow:
                    Assert.notNull(user, () -> new NotFoundException("User not found"));
                    Assert.isTrue(user.getReservedCopyIds().contains(event.getCopyId()) || !isBorrowOutOfRange(user),
                            () -> new ConflictException("Borrow out of range"));
                    Assert.isTrue(!isLackofCredit(user, 0), () -> new ConflictException("Lack of credit"));
                    user.getBorrowedCopyIds().add(event.getCopyId());
                    break;
                case Damage:
                    break;
                case Lose:
                    break;
                case Reserve:
                    Assert.notNull(user, () -> new NotFoundException("User not found"));
                    Assert.isTrue(!isReserveOutOfRange(user), () -> new ConflictException("Reserve out of range"));
                    Assert.isTrue(!isLackofCredit(user, 50), () -> new ConflictException("Lack of credit"));
                    user.getReservedCopyIds().add(event.getCopyId());
                    break;
                case Return:
                    break;
                default:
                    break;
            }
            return new UserReply(Type.Approved, event.getOrderId(), user.getRole(), null);
        } catch (Exception e) {
            if (user == null) {
                return new UserReply(Type.Rejected, event.getOrderId(), null, e.getMessage());
            } else {
                return new UserReply(Type.Rejected, event.getOrderId(), user.getRole(), e.getMessage());
            }
        }
    }

    private boolean isReserveOutOfRange(User user) {
        return isBorrowOutOfRange(user);
    }

    private boolean isBorrowOutOfRange(User user) {
        return user.getBorrowedCopyIds().size() + user.getReservedCopyIds().size() >= userConfigRepository
                .findById(user.getRole()).orElse(UserConfig.DEFAULT_CONFIG).getMaxBorrowNumber();
    }

    private boolean isLackofCredit(User user, int credit) {
        return user.getCredit() < credit;
    }

    private void handleRejectingEvent(OrderEvent event, User user) {
        switch (event.getOperation()) {
            case Borrow:
                user.getBorrowedCopyIds().remove(event.getCopyId());
                break;
            case Damage:
                break;
            case Lose:
                break;
            case Reserve:
                user.getReservedCopyIds().remove(event.getCopyId());
                break;
            case Return:
                break;
            default:
                break;
        }
    }
}

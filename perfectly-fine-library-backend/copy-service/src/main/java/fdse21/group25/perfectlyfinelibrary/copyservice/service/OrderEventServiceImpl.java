package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.domain.copy.Status;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.CopyReply;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.CopyReply.Type;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;
import fdse21.group25.perfectlyfinelibrary.copyservice.entity.Copy;
import fdse21.group25.perfectlyfinelibrary.copyservice.entity.CopyConfig;
import fdse21.group25.perfectlyfinelibrary.copyservice.repository.CopyConfigRepository;
import fdse21.group25.perfectlyfinelibrary.copyservice.repository.CopyRepository;

@Service
public class OrderEventServiceImpl implements OrderEventService {
    private final CopyRepository copyRepository;
    private final CopyConfigRepository copyConfigRepository;

    public OrderEventServiceImpl(CopyRepository copyRepository, CopyConfigRepository copyConfigRepository) {
        this.copyRepository = copyRepository;
        this.copyConfigRepository = copyConfigRepository;
    }

    @Override
    public CopyReply handleOrderEvent(OrderEvent event) {
        CopyReply reply = null;
        Copy copy = copyRepository.findById(event.getCopyId()).orElse(null);
        switch (event.getEventType()) {
            case Approving:
                handleApprovingEvent(event, copy);
                break;
            case Creating:
                reply = handleCreatingEvent(event, copy);
                break;
            case Rejecting:
                handleRejectingEvent(event, copy);
                break;
            default:
                break;
        }
        copyRepository.save(copy);
        return reply;
    }

    private void handleApprovingEvent(OrderEvent event, Copy copy) {
        switch (event.getOperation()) {
            case Borrow:
                copy.setStatus(Status.Borrowed);
                copy.setLibrary(null);
                copy.setUsername(event.getUsername());
                copy.setExpiration(new Date(event.getTimestamp().getTime() + copyConfigRepository
                        .findById(event.getRole()).orElse(CopyConfig.DEFAULT_CONFIG).getBorrowExpiration()));
                break;
            case Damage:
                copy.setStatus(Status.Damaged);
                copy.setLibrary(event.getLibrary());
                copy.setUsername(null);
                copy.setExpiration(null);
                break;
            case Lose:
                copy.setStatus(Status.Lost);
                copy.setLibrary(event.getLibrary());
                copy.setUsername(null);
                copy.setExpiration(null);
                break;
            case Reserve:
                copy.setStatus(Status.Reserved);
                copy.setUsername(event.getUsername());
                copy.setExpiration(new Date(event.getTimestamp().getTime() + copyConfigRepository
                        .findById(event.getRole()).orElse(CopyConfig.DEFAULT_CONFIG).getReserveExpiration()));
                break;
            case Return:
                copy.setStatus(Status.Available);
                copy.setLibrary(event.getLibrary());
                copy.setUsername(null);
                copy.setExpiration(null);
                break;
            default:
                break;
        }
        copy.setOrderId(null);
    }

    private CopyReply handleCreatingEvent(OrderEvent event, Copy copy) {
        Boolean expired = null;
        copy.setOrderId(event.getOrderId());
        try {
            Assert.notNull(copy, () -> new NotFoundException("Copy not found!"));
            switch (event.getOperation()) {
                case Borrow:
                    Assert.isTrue(event.getLibrary().equals(copy.getLibrary()),
                            () -> new ConflictException("Copy not in this library!"));
                    switch (copy.getStatus()) {
                        case Available:
                            break;
                        case Reserved:
                            Assert.isTrue(copy.getUsername().equals(event.getUsername()),
                                    () -> new ConflictException("Reserved by other"));
                            expired = event.getTimestamp().after(copy.getExpiration());
                            Assert.isTrue(!expired, () -> new ConflictException("Reserved expired"));
                            break;
                        default:
                            throw new ConflictException("Copy cannot be borrowed");
                    }
                    break;
                case Damage:
                case Lose:
                    Assert.isTrue(copy.getStatus().equals(Status.Borrowed),
                            () -> new ConflictException("Copy not be borrowed!"));
                    break;
                case Return:
                    Assert.isTrue(copy.getStatus().equals(Status.Borrowed),
                            () -> new ConflictException("Copy not be borrowed!"));
                    expired = event.getTimestamp().after(copy.getExpiration());
                    break;
                case Reserve:
                    Assert.isTrue(copy.getStatus().equals(Status.Available),
                            () -> new ConflictException("Copy not be available!"));
                    break;
                default:
                    break;
            }
            return new CopyReply(Type.Approved, event.getOrderId(), copy.getBookId(), expired, copy.getUsername(),
                    null);
        } catch (Exception e) {
            return new CopyReply(Type.Rejected, event.getOrderId(), copy.getBookId(), expired, copy.getUsername(),
                    e.getMessage());
        }
    }

    private void handleRejectingEvent(OrderEvent event, Copy copy) {
        if (event.getOrderId().equals(copy.getOrderId()))
            copy.setOrderId(null);
    }
}

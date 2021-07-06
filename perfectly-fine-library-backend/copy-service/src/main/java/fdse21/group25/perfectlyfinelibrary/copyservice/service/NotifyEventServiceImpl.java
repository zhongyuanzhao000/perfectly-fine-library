package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.domain.copy.Status;
import fdse21.group25.perfectlyfinelibrary.common.dto.CopyDto;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent.CopyReply;
import fdse21.group25.perfectlyfinelibrary.copyservice.entity.Copy;
import fdse21.group25.perfectlyfinelibrary.copyservice.repository.CopyRepository;

@Service
public class NotifyEventServiceImpl implements NotifyEventService {
    final private CopyService copyService;
    final private CopyRepository copyRepository;

    public NotifyEventServiceImpl(CopyService copyService, CopyRepository copyRepository) {
        this.copyService = copyService;
        this.copyRepository = copyRepository;
    }

    private NotifyEvent.CopyReply handleApprovingNotifyEvent(NotifyEvent event) {
        event.getReservedCopiesToRevert().forEach(copyDto -> {
            Copy copy = copyRepository.findById(copyDto.getCopyId()).get();
            copy.setStatus(Status.Available);
            copy.setUsername(null);
            copy.setExpiration(null);
            copyRepository.save(copy);
        });
        return null;
    }

    private NotifyEvent.CopyReply handleCreatingNotifyEvent(NotifyEvent event) {
        Date now = new Date(System.currentTimeMillis());
        List<Copy> reservedCopies = copyService
                .findCopies(new Copy(null, null, null, Status.Reserved, null, null, null, null, null));
        List<Copy> borrowedCopies = copyService
                .findCopies(new Copy(null, null, null, Status.Borrowed, null, null, null, null, null));
        reservedCopies.removeIf(copy -> now.before(copy.getExpiration()));
        borrowedCopies.removeIf(copy -> now.before(copy.getExpiration()));
        CopyReply reply = new CopyReply(event.getNotifyId(), new ArrayList<>(reservedCopies.size()),
                new ArrayList<>(borrowedCopies.size()));
        reservedCopies.forEach(copy -> {
            reply.getReservedExpiredCopies()
                    .add(new CopyDto(copy.getCopyId(), copy.getBookId(), copy.getStatus(), copy.getUsername()));
        });
        borrowedCopies.forEach(copy -> {
            reply.getBorrowedExpiredCopies()
                    .add(new CopyDto(copy.getCopyId(), copy.getBookId(), copy.getStatus(), copy.getUsername()));
        });
        return reply;
    }

    @Override
    public CopyReply handleNotifyEvent(NotifyEvent event) {
        switch (event.getType()) {
            case Approving:
                return handleApprovingNotifyEvent(event);
            case Creating:
                return handleCreatingNotifyEvent(event);
            default:
                return null;
        }
    }
}

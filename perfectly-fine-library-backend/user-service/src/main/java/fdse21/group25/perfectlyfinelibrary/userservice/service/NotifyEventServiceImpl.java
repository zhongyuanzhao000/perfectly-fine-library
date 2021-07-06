package fdse21.group25.perfectlyfinelibrary.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.dto.CopyDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent.UserReply;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserRepository;

@Service
public class NotifyEventServiceImpl implements NotifyEventService {
    final private UserRepository userRepository;

    public NotifyEventServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserReply handleNotifyEvent(NotifyEvent event) {
        switch (event.getType()) {
            case Approving:
                handleApprovingNotifyEvent(event);
                return null;
            case Creating:
                return handleCreatingNotifyEvent(event);
            default:
                return null;
        }
    }

    private void handleApprovingNotifyEvent(NotifyEvent event) {
        List<CopyDto> reservedCopiesToRevert = event.getReservedCopiesToRevert();
        for (CopyDto copy : reservedCopiesToRevert) {
            User user = userRepository.findById(copy.getUsername()).get();
            user.getReservedCopyIds().remove(copy.getCopyId());
            userRepository.save(user);
        }
    }

    private UserReply handleCreatingNotifyEvent(NotifyEvent event) {
        List<User> finedUsers = userRepository.findAllByFineGreaterThan(0);
        List<UserDto> finedUserDtos = new ArrayList<>(finedUsers.size());
        for (User user : finedUsers) {
            finedUserDtos.add(new UserDto(user.getUsername(), user.getRole(), user.getFine()));
        }
        return new NotifyEvent.UserReply(event.getNotifyId(), finedUserDtos);
    }
}

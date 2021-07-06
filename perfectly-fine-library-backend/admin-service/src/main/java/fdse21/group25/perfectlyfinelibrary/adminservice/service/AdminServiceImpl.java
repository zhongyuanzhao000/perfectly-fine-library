package fdse21.group25.perfectlyfinelibrary.adminservice.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.adminservice.entity.Config;
import fdse21.group25.perfectlyfinelibrary.adminservice.repository.ConfigRepository;
import fdse21.group25.perfectlyfinelibrary.common.client.CopyServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.EmailServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.ConfigDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.NoticeEmailDto;
import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent.Type;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserServiceClient userServiceClient;
    private final CopyServiceClient copyServiceClient;
    private final EmailServiceClient emailServiceClient;
    private final ConfigRepository configRepository;

    public AdminServiceImpl(UserServiceClient userServiceClient, CopyServiceClient copyServiceClient,
            EmailServiceClient emailServiceClient, ConfigRepository configRepository) {
        this.userServiceClient = userServiceClient;
        this.copyServiceClient = copyServiceClient;
        this.emailServiceClient = emailServiceClient;
        this.configRepository = configRepository;
    }

    @Override
    public void updateConfigs(List<Config> configs) {
        Map<Role, ConfigDto> configDtos = new HashMap<>();
        configs.forEach(config -> {
            configRepository.save(config);
            configDtos.put(config.getRole(), new ConfigDto(config.getRole(), config.getMaxBorrowNumber(),
                    config.getBorrowExpiration(), config.getReserveExpiration()));
        });
        userServiceClient.sendConfigEvent(new ConfigEvent(configDtos));
        copyServiceClient.sendConfigEvent(new ConfigEvent(configDtos));
    }

    @Override
    public List<Config> findConfigs() {
        return configRepository.findAll();
    }

    @Override
    @Async
    public void notify(LoginAdminDto admin) throws UnauthorizedException {
        NotifyEvent event = new NotifyEvent(null, Type.Creating, null);
        NotifyEvent.CopyReply copyReply = copyServiceClient.sendNotifyEvent(event);
        NotifyEvent.UserReply userReply = userServiceClient.sendNotifyEvent(event);

        Map<String, NoticeEmailDto> emails = new HashMap<>();

        copyReply.getBorrowedExpiredCopies().forEach(copy -> {
            NoticeEmailDto email = emails.get(copy.getUsername());
            if (email == null) {
                email = new NoticeEmailDto(copy.getUsername().concat("@fudan.edu.cn"), null, null, new LinkedList<>());
            }
            List<String> expiredBorrow = email.getExpiredBorrow();
            if (expiredBorrow == null) {
                email.setExpiredBorrow(expiredBorrow = new LinkedList<>());
            }
            expiredBorrow.add(copy.getCopyId().toString());
            emails.put(copy.getUsername(), email);
        });

        copyReply.getReservedExpiredCopies().forEach(copy -> {
            NoticeEmailDto email = emails.get(copy.getUsername());
            if (email == null) {
                email = new NoticeEmailDto(copy.getUsername().concat("@fudan.edu.cn"), null, new LinkedList<>(), null);
            }
            List<String> expiredReserve = email.getExpiredReserve();
            if (expiredReserve == null) {
                email.setExpiredReserve(expiredReserve = new LinkedList<>());
            }
            expiredReserve.add(copy.getCopyId().toString());
            emails.put(copy.getUsername(), email);
        });

        userReply.getFinedUsers().forEach(user -> {
            NoticeEmailDto email = emails.get(user.getUsername());
            if (email == null) {
                email = new NoticeEmailDto(user.getUsername().concat("@fudan.edu.cn"), null, null, null);
            }
            email.setFine(user.getFine());
            emails.put(user.getUsername(), email);
        });

        userServiceClient.sendNotifyEvent(new NotifyEvent(null, Type.Approving, copyReply.getReservedExpiredCopies()));
        copyServiceClient.sendNotifyEvent(new NotifyEvent(null, Type.Approving, copyReply.getReservedExpiredCopies()));
        emailServiceClient.sendNoticeEmails(List.copyOf(emails.values()));
    }
}

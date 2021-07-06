package fdse21.group25.perfectlyfinelibrary.userservice.service;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.ConfigDto;
import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.UserConfig;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserConfigRepository;

@Service
public class UserConfigServiceImpl implements UserConfigService {
    private final UserConfigRepository orderConfigRepository;

    public UserConfigServiceImpl(UserConfigRepository orderConfigRepository) {
        this.orderConfigRepository = orderConfigRepository;
    }

    @Override
    public void handleConfigEvent(ConfigEvent event) {
        ConfigDto postgraduate = event.getConfigs().get(Role.POSTGRADUATE);
        ConfigDto teacher = event.getConfigs().get(Role.TEACHER);
        ConfigDto undergraduate = event.getConfigs().get(Role.UNDERGRADUATE);
        orderConfigRepository.save(new UserConfig(Role.POSTGRADUATE, postgraduate.getMaxBorrowNumber()));
        orderConfigRepository.save(new UserConfig(Role.TEACHER, teacher.getMaxBorrowNumber()));
        orderConfigRepository.save(new UserConfig(Role.UNDERGRADUATE, undergraduate.getMaxBorrowNumber()));
    }
}
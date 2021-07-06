package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.ConfigDto;
import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;
import fdse21.group25.perfectlyfinelibrary.copyservice.entity.CopyConfig;
import fdse21.group25.perfectlyfinelibrary.copyservice.repository.CopyConfigRepository;

@Service
public class CopyConfigServiceImpl implements CopyConfigService {
    private final CopyConfigRepository orderConfigRepository;

    public CopyConfigServiceImpl(CopyConfigRepository orderConfigRepository) {
        this.orderConfigRepository = orderConfigRepository;
    }

    @Override
    public void handleConfigEvent(ConfigEvent event) {
        ConfigDto postgraduate = event.getConfigs().get(Role.POSTGRADUATE);
        ConfigDto teacher = event.getConfigs().get(Role.TEACHER);
        ConfigDto undergraduate = event.getConfigs().get(Role.UNDERGRADUATE);
        orderConfigRepository.save(new CopyConfig(Role.POSTGRADUATE, postgraduate.getBorrowExpiration(),
                postgraduate.getBorrowExpiration()));
        orderConfigRepository
                .save(new CopyConfig(Role.TEACHER, teacher.getBorrowExpiration(), teacher.getReserveExpiration()));
        orderConfigRepository.save(new CopyConfig(Role.UNDERGRADUATE, undergraduate.getBorrowExpiration(),
                undergraduate.getReserveExpiration()));
    }
}
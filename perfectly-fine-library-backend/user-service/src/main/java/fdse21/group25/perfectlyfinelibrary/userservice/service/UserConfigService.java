package fdse21.group25.perfectlyfinelibrary.userservice.service;

import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;

public interface UserConfigService {
    void handleConfigEvent(ConfigEvent event);
}

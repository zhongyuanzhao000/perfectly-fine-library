package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;

public interface CopyConfigService {
    void handleConfigEvent(ConfigEvent event);
}

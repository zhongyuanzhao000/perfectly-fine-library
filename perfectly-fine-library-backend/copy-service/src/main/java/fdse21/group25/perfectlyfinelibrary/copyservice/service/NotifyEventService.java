package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;

public interface NotifyEventService {
	NotifyEvent.CopyReply handleNotifyEvent(NotifyEvent event);
}

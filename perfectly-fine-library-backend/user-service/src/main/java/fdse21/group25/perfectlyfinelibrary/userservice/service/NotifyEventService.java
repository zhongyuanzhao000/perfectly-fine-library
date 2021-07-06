package fdse21.group25.perfectlyfinelibrary.userservice.service;

import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;

public interface NotifyEventService {
	NotifyEvent.UserReply handleNotifyEvent(NotifyEvent event);
}

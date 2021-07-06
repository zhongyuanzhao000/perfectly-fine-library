package fdse21.group25.perfectlyfinelibrary.userservice.service;

import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;

public interface OrderEventService {
    OrderEvent.UserReply handleOrderEvent(OrderEvent event);
}

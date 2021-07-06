package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;

public interface OrderEventService {
    OrderEvent.CopyReply handleOrderEvent(OrderEvent event);
}

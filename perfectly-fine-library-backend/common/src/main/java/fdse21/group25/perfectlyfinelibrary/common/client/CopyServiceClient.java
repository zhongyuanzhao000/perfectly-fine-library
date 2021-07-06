package fdse21.group25.perfectlyfinelibrary.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fdse21.group25.perfectlyfinelibrary.common.dto.CopyDto;
import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;

@FeignClient("copy-service")
@RequestMapping("/copies")
public interface CopyServiceClient {

    @GetMapping
    List<CopyDto> findCopies(@SpringQueryMap CopyDto example);

    @PostMapping("/events/order")
    OrderEvent.CopyReply sendOrderEvent(@RequestBody OrderEvent event);

    @PostMapping("/evnets/config")
    void sendConfigEvent(@RequestBody ConfigEvent event);

    @PostMapping("/events/notify")
    NotifyEvent.CopyReply sendNotifyEvent(@RequestBody NotifyEvent event);
}

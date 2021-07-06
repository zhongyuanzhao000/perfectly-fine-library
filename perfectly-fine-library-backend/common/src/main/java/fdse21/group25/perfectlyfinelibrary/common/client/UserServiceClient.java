package fdse21.group25.perfectlyfinelibrary.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;

@FeignClient("user-service")
@RequestMapping("/users")
public interface UserServiceClient {
    @PostMapping
    UserDto addNewUser(@RequestBody UserDto user);

    @GetMapping
    List<UserDto> findUsers(@RequestParam("is_fined") boolean isFined);

    @PostMapping("/events/order")
    OrderEvent.UserReply sendOrderEvent(@RequestBody OrderEvent event);

    @PostMapping("/events/config")
    void sendConfigEvent(@RequestBody ConfigEvent event);

    @PostMapping("/events/comment")
    CommentEvent.UserReply sendCommentEvent(@RequestBody CommentEvent event);

    @PostMapping("/events/notify")
    NotifyEvent.UserReply sendNotifyEvent(@RequestBody NotifyEvent event);
}

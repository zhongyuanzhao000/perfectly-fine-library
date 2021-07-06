package fdse21.group25.perfectlyfinelibrary.userservice.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userservice.service.CommentEventService;
import fdse21.group25.perfectlyfinelibrary.userservice.service.NotifyEventService;
import fdse21.group25.perfectlyfinelibrary.userservice.service.OrderEventService;
import fdse21.group25.perfectlyfinelibrary.userservice.service.UserConfigService;
import fdse21.group25.perfectlyfinelibrary.userservice.service.UserService;
import feign.FeignException.Unauthorized;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CommentEventService commentEventService;
    private final NotifyEventService notifyEventService;
    private final OrderEventService orderEventService;
    private final UserConfigService userConfigService;
    private final UserAuthServiceClient userAuthServiceClient;
    private final UserService userService;

    public UserController(CommentEventService commentEventService, NotifyEventService notifyEventService,
            OrderEventService orderEventService, UserConfigService userConfigService,
            UserAuthServiceClient userAuthServiceClient, UserService userService) {
        this.commentEventService = commentEventService;
        this.notifyEventService = notifyEventService;
        this.orderEventService = orderEventService;
        this.userConfigService = userConfigService;
        this.userAuthServiceClient = userAuthServiceClient;
        this.userService = userService;
    }

    @GetMapping("/my")
    public User getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws Unauthorized, NotFoundException {
        return userService.findUserByUsername(userAuthServiceClient.checkToken(token).getUsername());
    }

    @GetMapping("/pay_fine")
    public User payFine(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws Unauthorized, NotFoundException {
        return userService.payFine(userAuthServiceClient.checkToken(token).getUsername());
    }

    @PostMapping
    public User addNewUser(@RequestBody UserDto user) {
        return userService.addNewUser(user);
    }

    @GetMapping("/{username}")
    public User findUserByUsername(@PathVariable("username") String username) throws NotFoundException {
        return userService.findUserByUsername(username);
    }

    @GetMapping
    public List<User> findUsers(@RequestParam("is_fined") boolean isFined) {
        return userService.findUsers(isFined);
    }

    @GetMapping("/{username}/reset/credit")
    public User resetCredit(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String username)
            throws Unauthorized, NotFoundException {
        return userService.resetCredit(username);
    }

    @PostMapping("/events/order")
    OrderEvent.UserReply handleOrderEvent(@RequestBody OrderEvent event) {
        return orderEventService.handleOrderEvent(event);
    }

    @PostMapping("/events/config")
    void handleConfigEvent(@RequestBody ConfigEvent event) {
        userConfigService.handleConfigEvent(event);
    }

    @PostMapping("/events/comment")
    CommentEvent.UserReply handleCommentEvent(@RequestBody CommentEvent event) {
        return commentEventService.handleCommentEvent(event);
    }

    @PostMapping("/events/notify")
    NotifyEvent.UserReply handleNotifyEvent(@RequestBody NotifyEvent event) {
        return notifyEventService.handleNotifyEvent(event);
    }
}
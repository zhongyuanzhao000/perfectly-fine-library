package fdse21.group25.perfectlyfinelibrary.orderservice.controller;

import java.util.List;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.common.client.AdminAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;
import fdse21.group25.perfectlyfinelibrary.orderservice.entity.Order;
import fdse21.group25.perfectlyfinelibrary.orderservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final AdminAuthServiceClient adminAuthServiceClient;
    private final UserAuthServiceClient userAuthServiceClient;
    private final OrderService orderService;

    public OrderController(AdminAuthServiceClient adminAuthServiceClient, UserAuthServiceClient userAuthServiceClient,
            OrderService orderService) {
        this.adminAuthServiceClient = adminAuthServiceClient;
        this.userAuthServiceClient = userAuthServiceClient;
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Order creatingOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @SpringQueryMap Order order)
            throws BadRequestException {
        Assert.notNull(order.getOperation(), () -> new BadRequestException("operation not found"));
        switch (order.getOperation()) {
            case Reserve:
                LoginUserDto user = userAuthServiceClient.checkToken(token);
                order.setUsername(user.getUsername());
                break;
            default:
                LoginAdminDto admin = adminAuthServiceClient.checkToken(token);
                order.setAdminUsername(admin.getUsername());
                order.setLibrary(admin.getLibrary());
                break;
        }
        return orderService.createOrder(order.formatRequest());
    }

    @GetMapping("/{orderId}")
    public Order findOrder(@PathVariable("orderId") Long orderId) throws NotFoundException {
        return orderService.findOrder(orderId);
    }

    @GetMapping
    public List<Order> findOrders(@SpringQueryMap Order example) {
        return orderService.findOrders(example);
    }
}

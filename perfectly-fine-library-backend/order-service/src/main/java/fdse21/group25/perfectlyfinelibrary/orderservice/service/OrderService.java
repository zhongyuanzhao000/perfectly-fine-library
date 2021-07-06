package fdse21.group25.perfectlyfinelibrary.orderservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.orderservice.entity.Order;

public interface OrderService {
    Order createOrder(Order order) throws BadRequestException;

    List<Order> findOrders(Order example);

    Order findOrder(Long orderId) throws NotFoundException;
}
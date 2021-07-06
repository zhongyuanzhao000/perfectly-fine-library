package fdse21.group25.perfectlyfinelibrary.orderservice.service;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.client.BookServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.CopyServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.CopyReply;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.Operation;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent.UserReply;
import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.orderservice.entity.Order;
import fdse21.group25.perfectlyfinelibrary.orderservice.entity.Order.State;
import fdse21.group25.perfectlyfinelibrary.orderservice.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    private final BookServiceClient bookServiceClient;
    private final CopyServiceClient copyServiceClient;
    private final UserServiceClient userServiceClient;
    private final OrderRepository orderRepository;
    private final ExampleMatcher orderExampleMatcher;

    public OrderServiceImpl(BookServiceClient bookServiceClient, CopyServiceClient copyServiceClient,
            UserServiceClient userServiceClient, OrderRepository orderRepository) {
        this.bookServiceClient = bookServiceClient;
        this.copyServiceClient = copyServiceClient;
        this.userServiceClient = userServiceClient;
        this.orderRepository = orderRepository;
        {
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();
            Field[] fields = Order.class.getDeclaredFields();
            for (Field field : fields) {
                exampleMatcher = exampleMatcher.withMatcher(field.getName(), GenericPropertyMatchers.contains());
            }
            orderExampleMatcher = exampleMatcher;
        }
    }

    @Override
    public List<Order> findOrders(Order example) {
        return orderRepository.findAll(Example.of(example, orderExampleMatcher));
    }

    @Override
    public Order findOrder(Long orderId) throws NotFoundException {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(null));
    }

    @Override
    public Order createOrder(Order order) throws BadRequestException {
        order.setState(State.Creating);
        order = orderRepository.save(order);

        CopyReply copyReply = copyServiceClient.sendOrderEvent(order.asEvent());
        order.setBookId(copyReply.getBookId());
        if (order.getUsername() == null)
            order.setUsername(copyReply.getUsername());

        UserReply userReply = userServiceClient.sendOrderEvent(order.asEvent());
        order.setRole(userReply.getRole());

        if (copyReply.getEventType().equals(CopyReply.Type.Approved)
                && userReply.getEventType().equals(UserReply.Type.Approved)) {
            order.setState(State.Approved);
            order.setFine(getFine(copyReply, order));
            order.setCredit(getDecreasedCredit(copyReply, order));
        } else {
            order.setState(State.Rejected);
        }
        copyServiceClient.sendOrderEvent(order.asEvent());
        userServiceClient.sendOrderEvent(order.asEvent());
        return orderRepository.save(order);
    }

    private Double getFine(CopyReply reply, Order order) {
        Double fine = null;
        switch (order.getOperation()) {
            case Borrow:
            case Return:
                if (Boolean.TRUE.equals(reply.getExpired())) {
                    fine = getBookPrice(reply) * getFineRate(order.getOperation());
                }
                break;
            case Damage:
            case Lose:
                fine = getBookPrice(reply) * getFineRate(order.getOperation());
                break;
            default:
                break;
        }
        return fine;
    }

    private Double getBookPrice(CopyReply reply) {
        try {
            return bookServiceClient.findBookByBookId(reply.getBookId()).getPrice();
        } catch (Exception e) {
            return null;
        }
    }

    private Double getFineRate(Operation operation) {
        Double rate = null;
        switch (operation) {
            case Borrow:
                rate = 0.0;
                break;
            case Damage:
                rate = 0.5;
                break;
            case Lose:
                rate = 1.0;
                break;
            case Return:
                rate = 0.25;
                break;
            default:
                break;
        }
        return rate;
    }

    private Integer getDecreasedCredit(CopyReply reply, Order order) {
        Integer credit = null;
        switch (order.getOperation()) {
            case Borrow:
                if (Boolean.TRUE.equals(reply.getExpired())) {
                    credit = 10;
                }
                break;
            case Damage:
                credit = 30;
                break;
            case Lose:
                credit = 40;
                break;
            case Reserve:
                break;
            case Return:
                if (Boolean.TRUE.equals(reply.getExpired())) {
                    credit = 20;
                }
                break;
            default:
                break;
        }
        return credit;
    }

}

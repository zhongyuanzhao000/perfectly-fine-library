package fdse21.group25.perfectlyfinelibrary.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fdse21.group25.perfectlyfinelibrary.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
}
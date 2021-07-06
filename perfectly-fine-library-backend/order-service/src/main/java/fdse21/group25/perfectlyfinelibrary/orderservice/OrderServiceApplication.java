package fdse21.group25.perfectlyfinelibrary.orderservice;

import fdse21.group25.perfectlyfinelibrary.common.client.*;
import fdse21.group25.perfectlyfinelibrary.common.component.DefaultControllerAdvice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DefaultControllerAdvice.class)
@EnableFeignClients(clients = { AdminAuthServiceClient.class, UserServiceClient.class, UserAuthServiceClient.class,
        CopyServiceClient.class, BookServiceClient.class })
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}

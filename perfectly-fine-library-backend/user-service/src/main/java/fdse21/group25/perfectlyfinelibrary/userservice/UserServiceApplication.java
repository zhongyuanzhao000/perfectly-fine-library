package fdse21.group25.perfectlyfinelibrary.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.component.DefaultControllerAdvice;
import fdse21.group25.perfectlyfinelibrary.userservice.client.PaymentClient;

@SpringBootApplication
@Import(DefaultControllerAdvice.class)
@EnableFeignClients(clients = { UserAuthServiceClient.class, PaymentClient.class })
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}

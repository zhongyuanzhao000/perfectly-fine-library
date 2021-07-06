package fdse21.group25.perfectlyfinelibrary.userauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import fdse21.group25.perfectlyfinelibrary.common.client.EmailServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.component.DefaultControllerAdvice;

@SpringBootApplication
@Import(DefaultControllerAdvice.class)
@EnableFeignClients(clients = { EmailServiceClient.class, UserServiceClient.class })
public class UserAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAuthServiceApplication.class, args);
    }

}

package fdse21.group25.perfectlyfinelibrary.commentservice;

import fdse21.group25.perfectlyfinelibrary.common.client.*;
import fdse21.group25.perfectlyfinelibrary.common.component.DefaultControllerAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DefaultControllerAdvice.class)
@EnableEurekaClient
@EnableFeignClients(clients = { UserAuthServiceClient.class, AdminAuthServiceClient.class, UserServiceClient.class })
public class CommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class, args);
    }

}

package fdse21.group25.perfectlyfinelibrary.copyservice;

import fdse21.group25.perfectlyfinelibrary.common.client.EmailServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import fdse21.group25.perfectlyfinelibrary.common.client.AdminAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.BookServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.component.DefaultControllerAdvice;

@SpringBootApplication
@Import(DefaultControllerAdvice.class)
@EnableEurekaClient
@EnableFeignClients(clients = {BookServiceClient.class, AdminAuthServiceClient.class, UserServiceClient.class, EmailServiceClient.class})
public class CopyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CopyServiceApplication.class, args);
    }

}

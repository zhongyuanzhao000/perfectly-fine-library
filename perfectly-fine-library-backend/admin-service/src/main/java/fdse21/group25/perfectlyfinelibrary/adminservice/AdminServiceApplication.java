package fdse21.group25.perfectlyfinelibrary.adminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import fdse21.group25.perfectlyfinelibrary.adminservice.entity.Config;
import fdse21.group25.perfectlyfinelibrary.adminservice.entity.Notify;
import fdse21.group25.perfectlyfinelibrary.common.client.AdminAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.BookServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.CopyServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.EmailServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.component.DefaultControllerAdvice;
import fdse21.group25.perfectlyfinelibrary.common.dto.CopyDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;

@SpringBootApplication
@Import(DefaultControllerAdvice.class)
@EnableEurekaClient
@EnableFeignClients(clients = { AdminAuthServiceClient.class, UserServiceClient.class, UserAuthServiceClient.class,
        EmailServiceClient.class, CopyServiceClient.class, BookServiceClient.class })
@EntityScan(basePackageClasses = { CopyDto.class, UserDto.class, Notify.class, Config.class })
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

}

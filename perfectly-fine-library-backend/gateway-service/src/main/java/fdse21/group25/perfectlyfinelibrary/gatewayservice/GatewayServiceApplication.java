package fdse21.group25.perfectlyfinelibrary.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {

        public static void main(String[] args) {
                SpringApplication.run(GatewayServiceApplication.class, args);
        }

        @Bean
        public RouteLocator routes(RouteLocatorBuilder builder) {
                return builder.routes()
                                .route(p -> p.path("/auth/admins", "/auth/admins/**").uri("lb://admin-auth-service"))
                                .route(p -> p.path("/admin/**").uri("lb://admin-service"))
                                .route(p -> p.path("/books", "/books/**").uri("lb://book-service"))
                                .route(p -> p.path("/comments", "/comments/**").uri("lb://comment-service"))
                                .route(p -> p.path("/copies", "/copies/**").uri("lb://copy-service"))
                                .route(p -> p.path("/orders", "/orders/**").uri("lb://order-service"))
                                .route(p -> p.path("/auth/users", "/auth/users/**").uri("lb://user-auth-service"))
                                .route(p -> p.path("/users", "/users/**").uri("lb://user-service")).build();
        }
}

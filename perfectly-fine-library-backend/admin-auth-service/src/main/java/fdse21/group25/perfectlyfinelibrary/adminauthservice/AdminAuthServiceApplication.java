package fdse21.group25.perfectlyfinelibrary.adminauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class AdminAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminAuthServiceApplication.class, args);
    }
}

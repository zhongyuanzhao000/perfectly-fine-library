package fdse21.group25.perfectlyfinelibrary.bookservice;

import fdse21.group25.perfectlyfinelibrary.common.component.DefaultControllerAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DefaultControllerAdvice.class)
public class BookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

}

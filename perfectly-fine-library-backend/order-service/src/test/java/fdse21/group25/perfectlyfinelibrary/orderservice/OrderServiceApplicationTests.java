package fdse21.group25.perfectlyfinelibrary.orderservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void propertyTest(@Value("${spring.cloud.stream.bindings.order-event.destination}") String d) {
        assertEquals(d, "order-event");
    }
}

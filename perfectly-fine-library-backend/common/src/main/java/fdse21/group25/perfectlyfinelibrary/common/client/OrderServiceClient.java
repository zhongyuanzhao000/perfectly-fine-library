package fdse21.group25.perfectlyfinelibrary.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("order-service")
@RequestMapping("/orders")
public interface OrderServiceClient {
}

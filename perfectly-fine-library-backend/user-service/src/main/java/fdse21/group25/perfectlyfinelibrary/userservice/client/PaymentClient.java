package fdse21.group25.perfectlyfinelibrary.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fdse21.group25.perfectlyfinelibrary.userservice.dto.PaymentRequestDto;
import fdse21.group25.perfectlyfinelibrary.userservice.dto.PaymentResponseDto;
import feign.FeignException;

@FeignClient(name = "payment-client", url = "http://47.103.205.96:8080")
@RequestMapping("/api")
public interface PaymentClient {
    @PostMapping("/payment")
    public PaymentResponseDto pay(@RequestBody PaymentRequestDto payment)
            throws FeignException.Conflict, FeignException.InternalServerError;
}

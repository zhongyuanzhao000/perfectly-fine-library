package fdse21.group25.perfectlyfinelibrary.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import feign.FeignException;

@FeignClient("user-auth-service")
@RequestMapping("/auth/users")
public interface UserAuthServiceClient {
    @GetMapping("/token_check")
    LoginUserDto checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws FeignException.Unauthorized;
}

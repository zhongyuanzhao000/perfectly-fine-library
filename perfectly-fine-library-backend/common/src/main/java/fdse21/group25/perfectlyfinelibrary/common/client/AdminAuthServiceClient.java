package fdse21.group25.perfectlyfinelibrary.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import feign.FeignException;

@FeignClient("admin-auth-service")
@RequestMapping("/auth/admins")
public interface AdminAuthServiceClient {
    /**
     * 
     * @param token in request header
     * @return Admin info payload stored in token
     */
    @GetMapping("/token_check")
    LoginAdminDto checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws FeignException.Unauthorized;
}

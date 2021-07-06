package fdse21.group25.perfectlyfinelibrary.adminservice.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.adminservice.entity.Config;
import fdse21.group25.perfectlyfinelibrary.adminservice.service.AdminService;
import fdse21.group25.perfectlyfinelibrary.common.client.AdminAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.domain.admin.Role;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;
import feign.FeignException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminAuthServiceClient adminAuthServiceClient;
    private final AdminService adminService;

    public AdminController(AdminAuthServiceClient adminAuthServiceClient, AdminService adminService) {
        this.adminAuthServiceClient = adminAuthServiceClient;
        this.adminService = adminService;

    }

    @GetMapping("/configs")
    public List<Config> findConfigs()
            throws UnauthorizedException, UnauthorizedException {

        return adminService.findConfigs();
    }

    @PutMapping("/configs")
    public void updateConfigs(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody List<Config> configs)
            throws FeignException.Unauthorized, UnauthorizedException {
        Assert.isTrue(adminAuthServiceClient.checkToken(token).getRole().equals(Role.SUPERADMIN),
                () -> new UnauthorizedException("only super admin"));
        adminService.updateConfigs(configs);
    }

    @GetMapping("/notify")
    void notify(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws UnauthorizedException {
        adminService.notify(adminAuthServiceClient.checkToken(token));
    }

    // void updateConfigs(@RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    // throws UnauthorizedException, UnauthorizedException {
    // Assert.isTrue(adminAuthServiceClient.checkToken(token).getRole().equals(Role.SUPERADMIN),
    // () -> new UnauthorizedException("only super admin"));
    // adminService.updateConfigs();
    // }

}

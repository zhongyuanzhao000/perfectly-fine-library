package fdse21.group25.perfectlyfinelibrary.adminauthservice.controller;

import javax.servlet.http.HttpServletResponse;

import com.nimbusds.jose.JOSEException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.adminauthservice.dto.AdminRegisterDto;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.dto.RegisteredAdminDto;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.entity.Admin;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.service.AdminService;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.service.JwtService;
import fdse21.group25.perfectlyfinelibrary.common.domain.Library;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;

@RestController
@RequestMapping("/auth/admins")
public class AdminAuthController {
    private final AdminService adminService;
    private final JwtService jwtService;

    public AdminAuthController(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }

    @GetMapping("/token_check")
    LoginAdminDto checkToken(@AuthenticationPrincipal LoginAdminDto loginAdmin) {
        return loginAdmin;
    }

    @GetMapping("/token")
    LoginAdminDto getToken(HttpServletResponse response, @AuthenticationPrincipal Admin admin,
            @RequestParam("library") Library library) throws JOSEException {
        LoginAdminDto loginAdmin = new LoginAdminDto(admin.getUsername(), admin.getRole(), library);
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtService.generateToken(loginAdmin));
        return loginAdmin;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RegisteredAdminDto addNewAdmin(@RequestBody AdminRegisterDto adminRegisterDto) {
        Admin newAdmin = adminService.addNewAdmin(adminRegisterDto);
        return new RegisteredAdminDto(newAdmin.getUsername(), newAdmin.getRole());
    }
}

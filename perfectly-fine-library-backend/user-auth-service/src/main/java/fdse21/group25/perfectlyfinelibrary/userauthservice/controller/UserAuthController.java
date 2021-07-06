package fdse21.group25.perfectlyfinelibrary.userauthservice.controller;

import javax.servlet.http.HttpServletResponse;

import com.nimbusds.jose.JOSEException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.RegisteredUserDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.UserPasswordDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.UserRegisterDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationIdDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationPairDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userauthservice.service.JwtService;
import fdse21.group25.perfectlyfinelibrary.userauthservice.service.UserService;

@RestController
@RequestMapping("/auth/users")
public class UserAuthController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserAuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/verification_id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public VerificationIdDto registerUser(@RequestBody UserRegisterDto userRegisterDto) throws ConflictException {
        return userService.registerUser(userRegisterDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegisteredUserDto verifyUser(@RequestBody VerificationPairDto verificationPairDto)
            throws UnauthorizedException, NotFoundException, ConflictException {
        return userService.verifyUser(verificationPairDto);
    }

    @PutMapping
    public RegisteredUserDto changePassword(@AuthenticationPrincipal LoginUserDto user,
            @RequestBody UserPasswordDto newPassword) throws NotFoundException {
        return userService.changePassword(user, newPassword);
    }

    @GetMapping("/token")
    public LoginUserDto getToken(HttpServletResponse response, @AuthenticationPrincipal User user)
            throws JOSEException {
        response.addHeader(HttpHeaders.AUTHORIZATION, jwtService.generateToken(user));
        return new LoginUserDto(user.getUsername(), user.getRole());
    }

    @GetMapping("/token_check")
    public LoginUserDto checkToken(@AuthenticationPrincipal LoginUserDto user) {
        return user;
    }

}

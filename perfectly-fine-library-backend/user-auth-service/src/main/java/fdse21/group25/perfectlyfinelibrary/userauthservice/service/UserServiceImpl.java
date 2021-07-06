package fdse21.group25.perfectlyfinelibrary.userauthservice.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.client.EmailServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.VerificationCodeEmailDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.RegisteredUserDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.UserPasswordDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.UserRegisterDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationIdDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationPairDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.Verification;
import fdse21.group25.perfectlyfinelibrary.userauthservice.repository.UserRepository;
import fdse21.group25.perfectlyfinelibrary.userauthservice.repository.VerificationRepository;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailServiceClient emailServiceClient;
    private final UserServiceClient userServiceClient;
    private final VerificationRepository verificationRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
            EmailServiceClient emailServiceClient, UserServiceClient userServiceClient,
            VerificationRepository verificationRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailServiceClient = emailServiceClient;
        this.userServiceClient = userServiceClient;
        this.verificationRepository = verificationRepository;
    }

    @Override
    public VerificationIdDto registerUser(UserRegisterDto userRegisterDto) throws ConflictException {
        Assert.isTrue(userRepository.findById(userRegisterDto.getUsername()).isEmpty(),
                () -> new ConflictException("user has registered"));
        String id = UUID.randomUUID().toString();
        String code = UUID.randomUUID().toString().substring(0, 6);
        Verification verification = new Verification(id, code, userRegisterDto.getUsername(),
                passwordEncoder.encode(userRegisterDto.getPassword()), userRegisterDto.getRole());
        verificationRepository.save(verification);
        String email = verification.getUsername().concat("@fudan.edu.cn");
        emailServiceClient.sendVerificationCodeEmail(new VerificationCodeEmailDto(email, verification.getCode()));
        return new VerificationIdDto(id, email);
    }

    @Override
    public RegisteredUserDto verifyUser(VerificationPairDto verificationPairDto)
            throws UnauthorizedException, NotFoundException, ConflictException {
        Optional<Verification> optionalVerification = verificationRepository
                .findById(verificationPairDto.getVerificationId());
        if (!optionalVerification.isPresent()) {
            throw new NotFoundException("Verification id not found");
        }
        Verification verification = optionalVerification.get();
        if (!verification.getCode().equals(verificationPairDto.getVerificationCode())) {
            throw new UnauthorizedException("Verification id and code do not match");
        }
        Assert.isTrue(userRepository.findById(verification.getUsername()).isEmpty(),
                () -> new ConflictException("user has registered"));
        User user = new User(verification.getUsername(), verification.getEncodedPassword(), verification.getRole());
        userRepository.save(user);
        userServiceClient.addNewUser(new UserDto(user.getUsername(), user.getRole()));
        return new RegisteredUserDto(user.getUsername(), user.getRole());
    }

    @Override
    public RegisteredUserDto changePassword(LoginUserDto loginUser, UserPasswordDto newPassword)
            throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(loginUser.getUsername());
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        return new RegisteredUserDto(user.getUsername(), user.getRole());
    }

}

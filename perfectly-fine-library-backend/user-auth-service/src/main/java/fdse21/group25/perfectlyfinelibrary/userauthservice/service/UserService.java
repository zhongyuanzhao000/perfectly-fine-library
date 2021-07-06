package fdse21.group25.perfectlyfinelibrary.userauthservice.service;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.RegisteredUserDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.UserPasswordDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.UserRegisterDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationIdDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationPairDto;

public interface UserService {
    VerificationIdDto registerUser(UserRegisterDto userRegisterDto) throws ConflictException;

    RegisteredUserDto verifyUser(VerificationPairDto verificationPairDto)
            throws UnauthorizedException, NotFoundException, ConflictException;

    RegisteredUserDto changePassword(LoginUserDto user, UserPasswordDto newPassword) throws NotFoundException;
}

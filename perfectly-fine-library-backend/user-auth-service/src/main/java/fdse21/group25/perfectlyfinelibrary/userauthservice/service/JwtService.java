package fdse21.group25.perfectlyfinelibrary.userauthservice.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;

public interface JwtService {
    String generateToken(User user) throws JOSEException;

    SignedJWT convert(String token) throws ParseException;

    LoginUserDto verify(SignedJWT token) throws JOSEException, ParseException;
}

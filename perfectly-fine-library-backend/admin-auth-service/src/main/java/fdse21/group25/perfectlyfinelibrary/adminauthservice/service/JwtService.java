package fdse21.group25.perfectlyfinelibrary.adminauthservice.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;

public interface JwtService {
    String generateToken(LoginAdminDto admin) throws JOSEException;

    SignedJWT convert(String token) throws ParseException;

    LoginAdminDto verify(SignedJWT token) throws JOSEException, ParseException;
}

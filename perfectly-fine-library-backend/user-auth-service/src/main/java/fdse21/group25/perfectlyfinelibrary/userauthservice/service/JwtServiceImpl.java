package fdse21.group25.perfectlyfinelibrary.userauthservice.service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String AUTHENTICATION_SCHEME_JWT = "Bearer";
    private static final String ISSUER = "fdse21.group25.perfectlyfinelibrary";
    private static final long EXPIRATION = 30 * 60 * 1000;
    private static final JWTClaimsSet PUBLIC_CLAIMS_SET = new JWTClaimsSet.Builder().issuer(ISSUER).build();
    private static final String USERNAME_CLAIM = "username", ROLE_CLAIM = "role";

    private final RSAKey rsaKey;
    private final JWSSigner signer;
    private final JWSVerifier verifier;

    public JwtServiceImpl() throws JOSEException {
        this.rsaKey = generateRsaKey();
        this.signer = new RSASSASigner(rsaKey);
        this.verifier = new RSASSAVerifier(rsaKey);
    }

    private static RSAKey generateRsaKey() throws JOSEException {
        return new RSAKeyGenerator(2048).keyID(UUID.randomUUID().toString()).generate();
    }

    @Override
    public String generateToken(User user) throws JOSEException {
        Date issueTime = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(issueTime.getTime() + EXPIRATION);
        JWTClaimsSet claims = new JWTClaimsSet.Builder(PUBLIC_CLAIMS_SET).issueTime(issueTime)
                .expirationTime(expirationTime).claim(USERNAME_CLAIM, user.getUsername())
                .claim(ROLE_CLAIM, user.getRole()).build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(UUID.randomUUID().toString()).build(), claims);

        signedJWT.sign(signer);
        return String.format("%s %s", AUTHENTICATION_SCHEME_JWT, signedJWT.serialize());
    }

    @Override
    public SignedJWT convert(String token) throws ParseException {
        Assert.isTrue(StringUtils.startsWithIgnoreCase(token, AUTHENTICATION_SCHEME_JWT), "Bearer token not found");
        Assert.isTrue(!token.equalsIgnoreCase(AUTHENTICATION_SCHEME_JWT), "Bearer token cannot be empty");
        token = token.substring(AUTHENTICATION_SCHEME_JWT.length() + 1);
        return SignedJWT.parse(token);
    }

    @Override
    public LoginUserDto verify(SignedJWT token) throws JOSEException, ParseException {
        Assert.isTrue(token.verify(verifier), "Token cannot be verified");
        JWTClaimsSet claims = token.getJWTClaimsSet();
        Date expiration = claims.getExpirationTime();
        Assert.notNull(expiration, "No expiration time specified");
        Assert.isTrue(expiration.after(new Date(System.currentTimeMillis())), "Token has expired");
        LoginUserDto user = new LoginUserDto((String) claims.getClaim(USERNAME_CLAIM),
                Role.valueOf((String) claims.getClaim(ROLE_CLAIM)));
        return user;
    }
}

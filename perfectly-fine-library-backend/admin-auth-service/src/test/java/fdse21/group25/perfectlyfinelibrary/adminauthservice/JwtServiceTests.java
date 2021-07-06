package fdse21.group25.perfectlyfinelibrary.adminauthservice;

import com.nimbusds.jose.JOSEException;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.service.JwtService;
import fdse21.group25.perfectlyfinelibrary.common.domain.Library;
import fdse21.group25.perfectlyfinelibrary.common.domain.admin.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTests {
    @Autowired
    private JwtService jwtService;

    @Test
    public void testGenerateToken() throws JOSEException {
        LoginAdminDto dto = new LoginAdminDto("20200200020", Role.ADMIN, Library.HD);
        String token = jwtService.generateToken(dto);
        Assertions.assertThat(token.startsWith("Bearer ")).isTrue();
    }

    @Test
    public void generatedTokenShouldBeChecked() throws Exception {
        LoginAdminDto dto = new LoginAdminDto("20200200020", Role.ADMIN, Library.HD);
        String token = jwtService.generateToken(dto);
        LoginAdminDto checkedDto = jwtService.verify(jwtService.convert(token));
        Assertions.assertThat(checkedDto.getUsername()).isEqualTo(dto.getUsername());
        Assertions.assertThat(checkedDto.getLibrary()).isEqualTo(dto.getLibrary());
        Assertions.assertThat(checkedDto.getRole()).isEqualTo(dto.getRole());
    }
}

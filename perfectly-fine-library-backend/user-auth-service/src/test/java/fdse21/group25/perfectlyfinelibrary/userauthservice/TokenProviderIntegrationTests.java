package fdse21.group25.perfectlyfinelibrary.userauthservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fdse21.group25.perfectlyfinelibrary.common.client.EmailServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.UserRegisterDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationIdDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.dto.VerificationPairDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.Verification;
import fdse21.group25.perfectlyfinelibrary.userauthservice.repository.UserRepository;
import fdse21.group25.perfectlyfinelibrary.userauthservice.repository.VerificationRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenProviderIntegrationTests {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private EmailServiceClient emailServiceClient;
        @MockBean
        private UserServiceClient userServiceClient;

        private final String username = "20200200020", password = "kekekeke520", errorPassword = "kekekeke555";
        private final Role role = Role.UNDERGRADUATE;

        @Test
        public void getVerificationId() throws Exception {
                VerificationIdDto code = JSON
                                .parseObject(mockMvc
                                                .perform(get("/auth/users/verification_id")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(JSON.toJSONString(new UserRegisterDto(username,
                                                                                password, role))))
                                                .andDo(print()).andExpect(status().isAccepted())
                                                .andExpect(jsonPath("email").value(username + "@fudan.edu.cn"))
                                                .andExpect(jsonPath("verificationId").exists()).andReturn()
                                                .getResponse().getContentAsString(), VerificationIdDto.class);
                Assertions.assertNotNull(code.getEmail());
                Assertions.assertNotNull(code.getVerificationId());

                mockMvc.perform(post("/auth/users").contentType(MediaType.APPLICATION_JSON)
                                .content(JSON.toJSONString(new VerificationPairDto(code.getVerificationId(), ""))))
                                .andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        public void addNewUser(@Autowired VerificationRepository verificationRepository,
                        @Autowired PasswordEncoder passwordEncoder) throws Exception {
                final String code = UUID.randomUUID().toString(), id = UUID.randomUUID().toString();
                verificationRepository
                                .save(new Verification(id, code, username, passwordEncoder.encode(password), role));

                mockMvc.perform(post("/auth/users").contentType(MediaType.APPLICATION_JSON)
                                .content(JSON.toJSONString(new VerificationPairDto(id, code)))).andDo(print())
                                .andExpect(status().isCreated()).andExpect(jsonPath("username").value(username))
                                .andExpect(jsonPath("role").value(role.name()));
        }

        @Test
        public void testLogin(@Autowired UserRepository userRepository, @Autowired PasswordEncoder passwordEncoder)
                        throws Exception {
                userRepository.save(new User(username, passwordEncoder.encode(password), role));

                HttpHeaders header = new HttpHeaders();
                header.setBasicAuth(username, password);
                String token = mockMvc.perform(get("/auth/users/token").headers(header)).andDo(print())
                                .andExpect(status().isOk()).andExpect(jsonPath("role").exists())
                                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION)).andReturn()
                                .getResponse().getHeader(HttpHeaders.AUTHORIZATION);

                mockMvc.perform(get("/auth/users/token_check").header(HttpHeaders.AUTHORIZATION, token)).andDo(print())
                                .andExpect(status().isOk()).andExpect(jsonPath("role").exists())
                                .andExpect(jsonPath("username").exists());

                mockMvc.perform(get("/auth/users/token_check").header(HttpHeaders.AUTHORIZATION, token + "0"))
                                .andDo(print()).andExpect(status().isUnauthorized());

        }
}

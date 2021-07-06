package fdse21.group25.perfectlyfinelibrary.userservice;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSON;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTests {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private UserAuthServiceClient userAuthServiceClient;

        @Test
        public void testAddUser() throws Exception {
                final String username = "keke0";
                final Role role = Role.POSTGRADUATE;
                mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                                .content(JSON.toJSONString(new UserDto(username, role)))).andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        public void testGetUser() throws Exception {
                final String username = "keke";
                final Role role = Role.POSTGRADUATE;
                BDDMockito.given(userAuthServiceClient.checkToken(anyString()))
                                .willReturn(new LoginUserDto(username, role));
                mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                                .content(JSON.toJSONString(new UserDto(username, role)))).andDo(print())
                                .andExpect(status().isOk());
                mockMvc.perform(MockMvcRequestBuilders.get("/users/my").header(HttpHeaders.AUTHORIZATION, "token"))
                                .andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void testPayFine(@Autowired UserRepository userRepository) throws Exception {
                final String username = "keke1";
                final Role role = Role.POSTGRADUATE;
                userRepository.save(new User(null, username, role, 5.30, 100, null, null, null,null));
                BDDMockito.given(userAuthServiceClient.checkToken(anyString()))
                                .willReturn(new LoginUserDto(username, role));
                mockMvc.perform(MockMvcRequestBuilders.get("/users/my").header(HttpHeaders.AUTHORIZATION, "token"))
                                .andDo(print()).andExpect(status().isOk());
                mockMvc.perform(MockMvcRequestBuilders.get("/users/pay_fine").header(HttpHeaders.AUTHORIZATION,
                                "token")).andDo(print()).andExpect(status().isOk());
        }

}

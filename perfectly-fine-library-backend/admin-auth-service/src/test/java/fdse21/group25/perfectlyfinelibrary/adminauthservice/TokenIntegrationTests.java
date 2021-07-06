package fdse21.group25.perfectlyfinelibrary.adminauthservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import fdse21.group25.perfectlyfinelibrary.adminauthservice.dto.AdminRegisterDto;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    private static String superAdminUsername = "00000", superAdminPassword = "keke520";

    @Test
    public void superAdminCanAddNewAdmin() throws Exception {
        final String username = "12345", password = "kekekeke520";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(superAdminUsername, superAdminPassword);
        String token = mockMvc.perform(get("/auth/admins/token").queryParam("library", "HD").headers(headers))
                .andDo(print()).andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);
        mockMvc.perform(get("/auth/admins/token_check").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk()).andDo(print());
        mockMvc.perform(
                post("/auth/admins").header(HttpHeaders.AUTHORIZATION, token).contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(new AdminRegisterDto(username, password))))
                .andDo(print()).andExpect(status().isCreated());
    }
}

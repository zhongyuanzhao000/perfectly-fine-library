package perfectlyfinelibrary.emailservice;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import fdse21.group25.perfectlyfinelibrary.common.dto.NoticeEmailDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.VerificationCodeEmailDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailIntegrationTests {
        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testSendEmail() throws Exception {
                mockMvc.perform(post("/emails/notices").contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(JSON.toJSONString(List.of(
                                                new NoticeEmailDto("19300200019@fudan.edu.cn", 30.50,
                                                                List.of("keke", "keke2"), List.of("keke3")),
                                                new NoticeEmailDto("qsliu19@fudan.edu.cn", null, null,
                                                                List.of("keke4", "keke5"))))))
                                .andDo(print()).andExpect(status().isAccepted());
        }

        @Test
        public void testSendVerificationCode() throws Exception {
                mockMvc.perform(post("/emails/verification_code").contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(JSON.toJSONString(
                                                new VerificationCodeEmailDto("qsliu19@fudan.edu.cn", "keke520"))))
                                .andDo(print()).andExpect(status().isCreated());
        }
}

package fdse21.group25.perfectlyfinelibrary.commentservice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import fdse21.group25.perfectlyfinelibrary.commentservice.controller.CommentController;
import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Comment;
import fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent.UserReply;

@SpringBootTest
// @AutoConfigureMockMvc
public class CommentServiceIntegrationTests {
    // @Autowired
    // private MockMvc mockMvc;

    // @Test
    // public void addCommentShouldReturnComment() throws Exception {
    // mockMvc.perform(post("/commnets").header(HttpHeaders.AUTHORIZATION, "token"))
    // .andDo(MockMvcResultHandlers.print());
    // }

    @MockBean
    private UserAuthServiceClient userAuthServiceClient;
    @MockBean
    private UserServiceClient userServiceClient;

    @Test
    public void testAddComment(@Autowired CommentController commentController) throws Exception {
        when(userAuthServiceClient.checkToken(anyString())).thenReturn(new LoginUserDto("keke", Role.POSTGRADUATE));
        when(userServiceClient.sendCommentEvent(any(CommentEvent.class)))
                .thenReturn(new UserReply(UserReply.Type.Approved, null, null));
        commentController.addComment("token", new Comment(null, 5l, "keke", null, 5.0, "", null));
        assertTrue(commentController.findComments(new Comment(null, null, null, null, null, null, null)).size() > 0);
    }
}

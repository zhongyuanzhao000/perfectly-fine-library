package fdse21.group25.perfectlyfinelibrary.commentservice.controller;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Comment;
import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Reply;
import fdse21.group25.perfectlyfinelibrary.commentservice.service.CommentService;
import fdse21.group25.perfectlyfinelibrary.commentservice.service.ReplyService;
import fdse21.group25.perfectlyfinelibrary.common.client.AdminAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.client.UserAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import feign.FeignException.Unauthorized;

@RestController
@RequestMapping("/comments")
public class CommentController {
    final private AdminAuthServiceClient adminAuthServiceClient;
    final private CommentService commentService;
    final private UserAuthServiceClient userAuthServiceClient;
    final private ReplyService replyService;

    public CommentController(AdminAuthServiceClient adminAuthServiceClient, CommentService commentService,
            UserAuthServiceClient userAuthServiceClient, ReplyService replyService) {
        this.adminAuthServiceClient = adminAuthServiceClient;
        this.commentService = commentService;
        this.userAuthServiceClient = userAuthServiceClient;
        this.replyService = replyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Comment comment)
            throws BadRequestException, Unauthorized, ConflictException {
        return commentService.addComment(userAuthServiceClient.checkToken(token), comment);
    }

    @GetMapping
    public List<Comment> findComments(@SpringQueryMap Comment example) {
        return commentFilter(commentService.findComments(example));
    }

    @PutMapping("/{commentId}")
    public Comment deleteComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long commentId)
            throws NotFoundException, Unauthorized, UnauthorizedException {
        try {
            return commentService.deleteComment(adminAuthServiceClient.checkToken(token), commentId);
        } catch (Unauthorized e) {
            return commentService.deleteComment(userAuthServiceClient.checkToken(token), commentId);
        }
    }

    @GetMapping("/{commentId}/replies")
    public List<Reply> findReplies(@PathVariable Long commentId) {
        return replyFilter(replyService.getReplies(commentId));
    }

    @GetMapping("/replies")
    public List<Reply> findReplies(@SpringQueryMap Reply reply) {
        return replyFilter(replyService.getReplies(reply));
    }

    @GetMapping("/replies/{replyId}")
    public Reply findReply(@PathVariable Long replyId) throws NotFoundException {
        return replyService.getReply(replyId);
    }

    @PostMapping("/{commentId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply addReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Reply reply) {
        return replyService.addReply(userAuthServiceClient.checkToken(token), reply);
    }

    @PutMapping("/replies/{replyId}")
    public Reply deleteReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long replyId)
            throws NotFoundException, Unauthorized, UnauthorizedException {
        try {
            return replyService.deleteReply(adminAuthServiceClient.checkToken(token), replyId);
        } catch (Unauthorized e) {
            return replyService.deleteReply(userAuthServiceClient.checkToken(token), replyId);
        }
    }

    private List<Comment> commentFilter(List<Comment> comments) {
        comments.forEach(comment -> {
            if (!comment.getIsVisible())
                comment.setContent("This comment has been delete");
        });
        return comments;
    }

    private List<Reply> replyFilter(List<Reply> replies) {
        replies.forEach(reply -> {
            if (!reply.getIsVisible())
                reply.setContent("This reply has been delete");
        });
        return replies;
    }
}

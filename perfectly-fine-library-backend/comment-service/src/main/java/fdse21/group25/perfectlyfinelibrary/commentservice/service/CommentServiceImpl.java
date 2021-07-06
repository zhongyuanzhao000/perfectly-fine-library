package fdse21.group25.perfectlyfinelibrary.commentservice.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Comment;
import fdse21.group25.perfectlyfinelibrary.commentservice.repository.CommentRepository;
import fdse21.group25.perfectlyfinelibrary.common.client.UserServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent.UserReply;
import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;

@Service
public class CommentServiceImpl implements CommentService {
    final private UserServiceClient userServiceClient;
    final private CommentRepository commentRepository;
    final private ExampleMatcher commentExampleMatcher;

    public CommentServiceImpl(UserServiceClient userServiceClient, CommentRepository commentRepository) {
        this.userServiceClient = userServiceClient;
        this.commentRepository = commentRepository;
        {
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();
            Field[] fields = Comment.class.getDeclaredFields();
            for (Field field : fields) {
                exampleMatcher = exampleMatcher.withMatcher(field.getName(), GenericPropertyMatchers.contains());
            }
            this.commentExampleMatcher = exampleMatcher;
        }
    }

    @Override
    public Comment addComment(LoginUserDto user, Comment comment) throws BadRequestException, ConflictException {
        Assert.isNull(commentRepository.findByUsernameAndBookId(user.getUsername(), comment.getBookId()),
                () -> new BadRequestException("already comment！"));
        comment.setUsername(user.getUsername());
        comment.setTimestamp(new Date(System.currentTimeMillis()));
        comment.setIsVisible(true);
        UserReply userReply = userServiceClient
                .sendCommentEvent(new CommentEvent(null, comment.getBookId(), user.getUsername()));
        if (userReply.getType().equals(UserReply.Type.Approved))
            return commentRepository.save(comment);
        throw new ConflictException(userReply.getMessage());
    }

    @Override
    public List<Comment> findComments(Comment example) {
        return commentRepository.findAll(Example.of(example, commentExampleMatcher));
    }

    @Override
    public Comment deleteComment(LoginUserDto user, Long commentId) throws NotFoundException, UnauthorizedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment not found"));
        Assert.isTrue(user.getUsername().equals(comment.getUsername()),
                () -> new UnauthorizedException("comment can be deleted by other user"));
        comment.setIsVisible(Boolean.FALSE);
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(LoginAdminDto admin, Long commentId) throws NotFoundException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment not found"));
        comment.setIsVisible(false);
        return commentRepository.save(comment);
    }
}

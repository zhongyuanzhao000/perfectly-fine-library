package fdse21.group25.perfectlyfinelibrary.commentservice.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Reply;
import fdse21.group25.perfectlyfinelibrary.commentservice.repository.ReplyRepository;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;

@Service
public class ReplyServiceImpl implements ReplyService {

    final private ReplyRepository replyRepository;
    final private ExampleMatcher replyExampleMatcher;

    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();
        {
            Field[] fields = Reply.class.getDeclaredFields();
            for (Field field : fields) {
                exampleMatcher = exampleMatcher.withMatcher(field.getName(), GenericPropertyMatchers.contains());
            }
        }
        this.replyExampleMatcher = exampleMatcher;
    }

    @Override
    public Reply addReply(LoginUserDto user, Reply reply) {
        reply.setIsVisible(true);
        reply.setTimestamp(new Date(System.currentTimeMillis()));
        reply.setUsername(user.getUsername());
        return replyRepository.save(reply);
    }

    @Override
    public List<Reply> getReplies(Long commentId) {
        return replyRepository
                .findAll(Example.of(new Reply(null, commentId, null, null, null, null, null), replyExampleMatcher));
    }

    @Override
    public List<Reply> getReplies(Reply example) {
        return replyRepository.findAll(Example.of(example, replyExampleMatcher));
    }

    @Override
    public Reply getReply(Long replyId) throws NotFoundException {
        return replyRepository.findById(replyId).orElseThrow(() -> new NotFoundException("reply not found"));
    }

    @Override
    public Reply deleteReply(LoginUserDto user, Long replyId) throws NotFoundException, UnauthorizedException {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new NotFoundException("reply not found"));
        Assert.isTrue(user.getUsername().equals(reply.getUsername()),
                () -> new UnauthorizedException("reply can be deleted by other user"));
        reply.setIsVisible(false);
        return replyRepository.save(reply);
    }

    @Override
    public Reply deleteReply(LoginAdminDto admin, Long replyId) throws NotFoundException {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new NotFoundException("reply not found"));
        reply.setIsVisible(false);
        return replyRepository.save(reply);
    }
}

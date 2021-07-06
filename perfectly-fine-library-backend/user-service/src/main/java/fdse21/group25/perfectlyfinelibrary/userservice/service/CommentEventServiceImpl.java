package fdse21.group25.perfectlyfinelibrary.userservice.service;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent.UserReply;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.util.Assert;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserRepository;

@Service
public class CommentEventServiceImpl implements CommentEventService {
    final private UserRepository userRepository;

    public CommentEventServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserReply handleCommentEvent(CommentEvent event) {
        try {
            User user = userRepository.findById(event.getUsername()).orElse(null);
            Assert.notNull(user, () -> new NotFoundException("User not found"));
            Assert.isTrue(user.getBorrowedBookIds().contains(event.getBookId()),
                    () -> new ConflictException("User has not borrowed this book"));
            return new UserReply(UserReply.Type.Approved, event.getCommentId(), null);
        } catch (Exception e) {
            return new UserReply(UserReply.Type.Rejected, event.getCommentId(), e.getMessage());
        }
    }
}
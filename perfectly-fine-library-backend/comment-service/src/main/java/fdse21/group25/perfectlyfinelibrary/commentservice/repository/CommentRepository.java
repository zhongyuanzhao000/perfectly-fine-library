package fdse21.group25.perfectlyfinelibrary.commentservice.repository;

import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findByUsernameAndBookId(String username, Long bookId);

    Comment findCommentByUsernameAndCommentId(String username,Long commentId);

    List<Comment> findByBookId(Long bookId);

    Comment findByUsername(String username);
}

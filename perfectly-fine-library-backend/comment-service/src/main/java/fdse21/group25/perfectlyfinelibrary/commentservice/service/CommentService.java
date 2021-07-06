package fdse21.group25.perfectlyfinelibrary.commentservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Comment;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;

public interface CommentService {
    Comment addComment(LoginUserDto user, Comment dto) throws BadRequestException, ConflictException;

    List<Comment> findComments(Comment example);

    Comment deleteComment(LoginUserDto user, Long commentId) throws NotFoundException, UnauthorizedException;

    Comment deleteComment(LoginAdminDto admin, Long commentId) throws NotFoundException;
}

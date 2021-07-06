package fdse21.group25.perfectlyfinelibrary.commentservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.commentservice.entity.Reply;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;

public interface ReplyService {
    Reply addReply(LoginUserDto user, Reply reply);

    List<Reply> getReplies(Long commentId);

    List<Reply> getReplies(Reply example);

    Reply getReply(Long replyId) throws NotFoundException;

    Reply deleteReply(LoginUserDto user, Long replyId) throws NotFoundException, UnauthorizedException;

    Reply deleteReply(LoginAdminDto admin, Long replyId) throws NotFoundException;
}

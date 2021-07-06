package fdse21.group25.perfectlyfinelibrary.userservice.service;

import fdse21.group25.perfectlyfinelibrary.common.event.CommentEvent;

public interface CommentEventService {
    CommentEvent.UserReply handleCommentEvent(CommentEvent event);
}
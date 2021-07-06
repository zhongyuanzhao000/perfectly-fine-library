package fdse21.group25.perfectlyfinelibrary.commentservice.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue
    private Long replyId;
    private Long commentId;
    private Long toId;
    private Date timestamp;
    private String username;
    private String content;
    private Boolean isVisible;// 能否看到该条评论
}

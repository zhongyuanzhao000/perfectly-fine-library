package fdse21.group25.perfectlyfinelibrary.commentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long commentId;
    private Long bookId;// 评论所属图书的Id
    private String username;// 评论者的用户Id
    private Date timestamp;
    private Double rate;// 评分
    private String content;// 评论的内容
    private Boolean isVisible;// 能否看到该条评论
}

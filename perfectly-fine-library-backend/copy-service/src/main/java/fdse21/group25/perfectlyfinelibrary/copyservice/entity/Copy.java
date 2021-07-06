package fdse21.group25.perfectlyfinelibrary.copyservice.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import fdse21.group25.perfectlyfinelibrary.common.domain.Library;
import fdse21.group25.perfectlyfinelibrary.common.domain.copy.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Copy {
    @Version
    private Long version;
    @Id
    @GeneratedValue
    private Long copyId;
    private Long bookId;
    private Status status;
    private Library library;
    private String username;
    private Date expiration;
    private String isbn;
    /**
     * This field indicates an order is locking this copy, in case of confliction.
     * <p>
     * When handle an order creating event, this field should be set to order's id,
     * while other fields being unchanged. When handle approved event of the same
     * order later, this field should be set to <code>null</code>, while others
     * being updated.
     * <p>
     * To revert change when handle an order rejected event, other fields should be
     * updated on receiving approved.
     * <p>
     * 这个属性表明一个Order正在锁住这个副本，以防Order间冲突。
     * <p>
     * 当处理一个Order的Creating事件时，这个属性应该更新为Order
     * Id，其他字段不变。稍后处理同一个Order的Approved事件时，这个属性被释放为<code>null</code>，然后更新其他字段。
     * <p>
     * 为了能够在处理Order的Rejected事件时撤回更改，其他字段应该在收到Approved时才更改。
     */
    private Long orderId;

    /**
     * Add new copy
     */
    public Copy(Long bookId, Library library, String isbn) {
        this(null, null, bookId, Status.Available, library, null, null, isbn, null);
    }
}
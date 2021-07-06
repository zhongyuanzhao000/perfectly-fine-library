package fdse21.group25.perfectlyfinelibrary.bookservice.entity;

import fdse21.group25.perfectlyfinelibrary.common.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private Long bookId;
    private String isbn;
    private String title;
    private String author;
    private Double price;
    private String brief;
    private String cover;

    public BookDto wrap() {
        return new BookDto(bookId, isbn, price);
    }
}

package fdse21.group25.perfectlyfinelibrary.bookservice.repository;

import fdse21.group25.perfectlyfinelibrary.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

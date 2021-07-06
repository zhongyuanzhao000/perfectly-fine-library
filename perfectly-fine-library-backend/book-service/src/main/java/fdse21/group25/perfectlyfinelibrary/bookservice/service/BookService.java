package fdse21.group25.perfectlyfinelibrary.bookservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.bookservice.entity.Book;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;

public interface BookService {
    Book addBook(Book dto);

    Book getBook(Long bookId) throws NotFoundException;

    List<Book> findBooks(Book example);
}

package fdse21.group25.perfectlyfinelibrary.bookservice.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.bookservice.entity.Book;
import fdse21.group25.perfectlyfinelibrary.bookservice.repository.BookRepository;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;

@Service
public class BookServiceImpl implements BookService {

    final private BookRepository bookRepository;
    final private ExampleMatcher bookExampleMatcher;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        {
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();
            Field[] fields = Book.class.getDeclaredFields();
            for (Field field : fields) {
                exampleMatcher = exampleMatcher.withMatcher(field.getName(), contains());
            }
            bookExampleMatcher = exampleMatcher;
        }
    }

    @Override
    public Book addBook(Book dto) {
        return bookRepository.save(dto);
    }

    @Override
    public Book getBook(Long bookId) throws NotFoundException {
        return bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("book not found"));
    }

    @Override
    public List<Book> findBooks(Book example) {
        return bookRepository.findAll(Example.of(example, bookExampleMatcher));
    }
}

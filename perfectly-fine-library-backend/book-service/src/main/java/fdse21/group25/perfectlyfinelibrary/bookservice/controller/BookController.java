package fdse21.group25.perfectlyfinelibrary.bookservice.controller;

import java.util.List;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.bookservice.entity.Book;
import fdse21.group25.perfectlyfinelibrary.bookservice.service.BookService;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book dto) {
        return bookService.addBook(dto);
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBook(@PathVariable Long bookId) throws NotFoundException {
        return bookService.getBook(bookId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> findBooks(@SpringQueryMap Book example) {
        return bookService.findBooks(example);
    }
}

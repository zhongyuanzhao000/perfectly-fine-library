package fdse21.group25.perfectlyfinelibrary.bookservice;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import fdse21.group25.perfectlyfinelibrary.bookservice.controller.BookController;
import fdse21.group25.perfectlyfinelibrary.bookservice.entity.Book;
import fdse21.group25.perfectlyfinelibrary.bookservice.repository.BookRepository;
import fdse21.group25.perfectlyfinelibrary.bookservice.service.BookService;

@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTests {
        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testFindBooks(@Autowired BookRepository bookRepository) throws Exception {
                bookRepository.saveAll(List.of(new Book(null, "123-456-7890", "keke", "keke", 5.20, "", ""),
                                new Book(null, "123-456-7891", "keke1", "keke", 5.20, "", ""),
                                new Book(null, "123-456-7892", "keke2", "keke", 5.20, "", "")));
                mockMvc.perform(get("/books").queryParam("isbn", "123")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$").isNotEmpty());
        }

        @Test
        public void testFindBooksFormController(@Autowired BookController bookController,
                        @Autowired BookRepository bookRepository) {
                bookRepository.saveAll(List.of(new Book(null, "123-456-7890", "keke", "keke", 5.20, "", ""),
                                new Book(null, "123-456-7891", "keke1", "keke", 5.20, "", ""),
                                new Book(null, "123-456-7892", "keke2", "keke", 5.20, "", "")));
                List<Book> bookDtos = bookController.findBooks(new Book(null, "123", null, null, null, null, null));
                assertNotEquals(0, bookDtos.size());
        }

        @Test
        public void testFindBooksFormService(@Autowired BookService bookService,
                        @Autowired BookRepository bookRepository) {
                bookRepository.saveAll(List.of(new Book(null, "123-456-7890", "keke", "keke", 5.20, "", ""),
                                new Book(null, "123-456-7891", "keke1", "keke", 5.20, "", ""),
                                new Book(null, "123-456-7892", "keke2", "keke", 5.20, "", "")));
                List<Book> allBooks = bookRepository.findAll();
                assertNotEquals(0, allBooks.size());
                List<Book> books = bookService.findBooks(new Book(null, "123", null, null, null, null, null));
                assertNotEquals(0, books.size());
        }

}

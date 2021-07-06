package fdse21.group25.perfectlyfinelibrary.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fdse21.group25.perfectlyfinelibrary.common.dto.BookDto;
import feign.FeignException;

@FeignClient("book-service")
@RequestMapping("/books")
public interface BookServiceClient {
    @GetMapping("/{bookId}")
    BookDto findBookByBookId(@PathVariable("bookId") long bookId) throws FeignException.NotFound;
}

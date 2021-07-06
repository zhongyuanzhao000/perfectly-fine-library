package fdse21.group25.perfectlyfinelibrary.copyservice.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.client.BookServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.dto.BookDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.LoginAdminDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.copyservice.dto.AddedCopyDto;
import fdse21.group25.perfectlyfinelibrary.copyservice.entity.Copy;
import fdse21.group25.perfectlyfinelibrary.copyservice.repository.CopyRepository;
import feign.FeignException;

@Service
public class CopyServiceImpl implements CopyService {
    private final CopyRepository copyRepository;
    private final BookServiceClient bookServiceClient;
    private final ExampleMatcher copyExampleMatcher;

    public CopyServiceImpl(CopyRepository copyRepository, BookServiceClient bookServiceClient) {
        this.copyRepository = copyRepository;
        this.bookServiceClient = bookServiceClient;
        {
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();
            Field[] fields = Copy.class.getDeclaredFields();
            for (Field field : fields) {
                exampleMatcher = exampleMatcher.withMatcher(field.getName(), contains());
            }
            copyExampleMatcher = exampleMatcher;
        }
    }

    @Override
    public List<Copy> findCopies(Copy example) {
        return copyRepository.findAll(Example.of(example, copyExampleMatcher));
    }

    @Override
    public List<Copy> addNewCopy(LoginAdminDto admin, AddedCopyDto copy) throws NotFoundException {
        BookDto book;
        try {
            book = bookServiceClient.findBookByBookId(copy.getBookId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Book " + copy.getBookId() + " not found");
        }
        int num = copyRepository.findAllByBookId(copy.getBookId()).size();
        List<Copy> addedCopies = new ArrayList<>(num);
        for (int i = 0, number = copy.getNumber(); i < number; i++) {
            String isbn = String.format("%s-%03d", book.getIsbn(), num + i);
            addedCopies.add(copyRepository.save(new Copy(copy.getBookId(), admin.getLibrary(), isbn)));
        }
        return addedCopies;
    }

    @Override
    public Copy findCopyByCopyId(Long copyId) throws NotFoundException {
        return copyRepository.findById(copyId).orElseThrow(() -> new NotFoundException(copyId + " not found"));
    }

}

package fdse21.group25.perfectlyfinelibrary.copyservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fdse21.group25.perfectlyfinelibrary.copyservice.entity.Copy;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    List<Copy> findAllByBookId(Long bookId);
}
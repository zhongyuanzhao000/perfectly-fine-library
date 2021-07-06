package fdse21.group25.perfectlyfinelibrary.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByFineGreaterThan(double fine);
}

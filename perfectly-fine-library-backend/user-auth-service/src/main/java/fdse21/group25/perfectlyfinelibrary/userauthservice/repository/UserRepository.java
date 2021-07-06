package fdse21.group25.perfectlyfinelibrary.userauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}

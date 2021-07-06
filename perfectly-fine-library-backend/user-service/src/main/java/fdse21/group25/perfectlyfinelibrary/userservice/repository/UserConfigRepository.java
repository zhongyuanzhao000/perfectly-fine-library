package fdse21.group25.perfectlyfinelibrary.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.UserConfig;

public interface UserConfigRepository extends JpaRepository<UserConfig, Role> {
}
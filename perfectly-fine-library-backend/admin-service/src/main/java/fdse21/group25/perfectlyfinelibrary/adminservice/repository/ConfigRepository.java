package fdse21.group25.perfectlyfinelibrary.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fdse21.group25.perfectlyfinelibrary.adminservice.entity.Config;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;

public interface ConfigRepository extends JpaRepository<Config, Role> {
}

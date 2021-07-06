package fdse21.group25.perfectlyfinelibrary.copyservice.repository;

import fdse21.group25.perfectlyfinelibrary.copyservice.entity.CopyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;


public interface CopyConfigRepository extends JpaRepository<CopyConfig,Role>{
}
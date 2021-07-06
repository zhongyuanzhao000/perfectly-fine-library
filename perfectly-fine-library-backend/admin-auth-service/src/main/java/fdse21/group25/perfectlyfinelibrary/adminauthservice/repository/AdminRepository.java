package fdse21.group25.perfectlyfinelibrary.adminauthservice.repository;

import fdse21.group25.perfectlyfinelibrary.adminauthservice.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}

package fdse21.group25.perfectlyfinelibrary.adminauthservice.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.adminauthservice.dto.AdminRegisterDto;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.entity.Admin;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.repository.AdminRepository;
import fdse21.group25.perfectlyfinelibrary.common.domain.admin.Role;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin addNewAdmin(AdminRegisterDto dto) {
        return addNewAdmin(dto, Role.ADMIN);
    }

    private Admin addNewAdmin(AdminRegisterDto dto, Role role) {
        return adminRepository.save(new Admin(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), role));
    }

    @Component
    public class AddInitialAdmin implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            addNewAdmin(new AdminRegisterDto("00000", "keke520"), Role.SUPERADMIN);
        }
    }
}

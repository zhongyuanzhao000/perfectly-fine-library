package fdse21.group25.perfectlyfinelibrary.adminauthservice.service;

import fdse21.group25.perfectlyfinelibrary.adminauthservice.entity.Admin;
import fdse21.group25.perfectlyfinelibrary.adminauthservice.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AdminRepository adminRepository;

    public UserDetailsServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findById(s);
        if (admin.isPresent())
            return admin.get();
        else
            throw new UsernameNotFoundException("username not found");
    }
}

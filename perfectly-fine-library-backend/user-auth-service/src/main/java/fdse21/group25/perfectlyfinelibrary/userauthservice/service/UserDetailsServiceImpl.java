package fdse21.group25.perfectlyfinelibrary.userauthservice.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userauthservice.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent())
            return user.get();
        else
            throw new UsernameNotFoundException(String.format("User %s not found", username));
    }

}

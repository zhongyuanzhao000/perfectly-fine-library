package fdse21.group25.perfectlyfinelibrary.userauthservice.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.userauthservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userauthservice.repository.UserRepository;

@Component
public class InitUser implements CommandLineRunner {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public InitUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("00000000000", passwordEncoder.encode("tyty520"), Role.POSTGRADUATE));
	}
}

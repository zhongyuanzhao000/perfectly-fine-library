package fdse21.group25.perfectlyfinelibrary.userservice.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserRepository;

@Component
public class InitUser implements CommandLineRunner {
	final private UserRepository userRepository;

	public InitUser(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("00000000000", Role.POSTGRADUATE));
	}

}

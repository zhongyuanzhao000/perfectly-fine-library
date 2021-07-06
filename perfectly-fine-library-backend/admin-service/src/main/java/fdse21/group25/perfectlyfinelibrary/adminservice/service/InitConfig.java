package fdse21.group25.perfectlyfinelibrary.adminservice.service;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fdse21.group25.perfectlyfinelibrary.adminservice.entity.Config;
import fdse21.group25.perfectlyfinelibrary.adminservice.repository.ConfigRepository;
import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;

@Component
public class InitConfig implements CommandLineRunner {
	private final ConfigRepository configRepository;

	public InitConfig(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		configRepository.saveAll(List.of(new Config(Role.UNDERGRADUATE, 1, 1000 * 60, 1000 * 60),
				new Config(Role.POSTGRADUATE, 1, 1000 * 60, 1000 * 60),
				new Config(Role.TEACHER, 1, 1000 * 60, 1000 * 60)));
	}
}

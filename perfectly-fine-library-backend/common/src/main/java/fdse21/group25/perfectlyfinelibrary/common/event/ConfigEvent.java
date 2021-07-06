package fdse21.group25.perfectlyfinelibrary.common.event;

import java.util.Map;

import fdse21.group25.perfectlyfinelibrary.common.domain.user.Role;
import fdse21.group25.perfectlyfinelibrary.common.dto.ConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEvent {
	Map<Role, ConfigDto> configs;
}

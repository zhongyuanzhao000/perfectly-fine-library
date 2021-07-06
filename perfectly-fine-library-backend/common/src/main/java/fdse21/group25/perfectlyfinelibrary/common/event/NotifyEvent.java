package fdse21.group25.perfectlyfinelibrary.common.event;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.common.dto.CopyDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyEvent {
	public enum Type {
		Creating, Approving
	}

	private Long notifyId;
	private Type type;
	private List<CopyDto> reservedCopiesToRevert;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserReply {
		private Long notifyId;
		private List<UserDto> finedUsers;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CopyReply {
		private Long notifyId;
		private List<CopyDto> reservedExpiredCopies;
		private List<CopyDto> borrowedExpiredCopies;
	}
}

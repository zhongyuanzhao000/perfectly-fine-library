package perfectlyfinelibrary.emailservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.common.dto.NoticeEmailDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.VerificationCodeEmailDto;

public interface EmailService {
    void sendNoticesEmail(List<NoticeEmailDto> notices);

    void sendVerificationCode(VerificationCodeEmailDto verificationCode);
}

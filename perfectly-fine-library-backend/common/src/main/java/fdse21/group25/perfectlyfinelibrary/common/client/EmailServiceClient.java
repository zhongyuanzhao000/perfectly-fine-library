package fdse21.group25.perfectlyfinelibrary.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fdse21.group25.perfectlyfinelibrary.common.dto.NoticeEmailDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.VerificationCodeEmailDto;

@FeignClient("email-service")
@RequestMapping("/emails")
public interface EmailServiceClient {
    @PostMapping("/notices")
    Void sendNoticeEmails(@RequestBody List<NoticeEmailDto> noticeEmails);

    @PostMapping("/verification_code")
    Void sendVerificationCodeEmail(@RequestBody VerificationCodeEmailDto verificationCodeEmail);
}

package perfectlyfinelibrary.emailservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import fdse21.group25.perfectlyfinelibrary.common.dto.NoticeEmailDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.VerificationCodeEmailDto;
import perfectlyfinelibrary.emailservice.service.EmailService;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/notices")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendNoticesEmail(@RequestBody List<NoticeEmailDto> notices) {
        emailService.sendNoticesEmail(notices);
    }

    @PostMapping("/verification_code")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendVerificationCode(@RequestBody VerificationCodeEmailDto verificationCode){
        emailService.sendVerificationCode(verificationCode);
    }
}

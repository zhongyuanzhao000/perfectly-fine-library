package perfectlyfinelibrary.emailservice.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.dto.NoticeEmailDto;
import fdse21.group25.perfectlyfinelibrary.common.dto.VerificationCodeEmailDto;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final SimpleMailMessage noticeMailMessage;
    private final SimpleMailMessage verificationMailMessage;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        noticeMailMessage = new SimpleMailMessage();
        noticeMailMessage.setFrom("fdse21_group25@163.com");
        noticeMailMessage.setSubject("Notice from Perfectly Fine Library");
        verificationMailMessage = new SimpleMailMessage();
        verificationMailMessage.setFrom("fdse21_group25@163.com");
        verificationMailMessage.setSubject("Welcome to Perfectly Fine Library");
    }

    @Override
    public void sendNoticesEmail(List<NoticeEmailDto> notices) {
        notices.forEach(notice -> {
            SimpleMailMessage msg = new SimpleMailMessage(noticeMailMessage);
            msg.setTo(notice.getEmail());
            msg.setText(formatNotice(notice));
            try {
                mailSender.send(msg);
            } catch (MailException e) {
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void sendVerificationCode(VerificationCodeEmailDto verificationCode) {
        SimpleMailMessage msg = new SimpleMailMessage(verificationMailMessage);
        msg.setTo(verificationCode.getEmail());
        msg.setText(formatVerificationCode(verificationCode));
        try {
            mailSender.send(msg);
        } catch (MailException e) {
            System.err.println(e.getMessage());
        }
    }

    private String formatVerificationCode(VerificationCodeEmailDto verificationCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You are registering for Perfectly Fine Library. Your verification code is ")
                .append(verificationCode.getVerificationCode())
                .append(" . The code expires in 30 minutes. If you do not register, ignore this email.");
        return stringBuilder.toString();
    }

    private String formatNotice(NoticeEmailDto notice) {
        StringBuilder text = new StringBuilder();
        if (notice.getFine() != null) {
            text.append(String.format("You have fine %.2f to pay.\n", notice.getFine()));
        }
        if (notice.getExpiredReserve() != null && !notice.getExpiredReserve().isEmpty()) {
            text.append("Your expired reserve(s):");
            notice.getExpiredReserve().forEach(reserve -> text.append(reserve).append(" "));
            text.append("\n");
        }
        if (notice.getExpiredBorrow() != null && !notice.getExpiredBorrow().isEmpty()) {
            text.append("Your expired borrow(s):");
            notice.getExpiredBorrow().forEach(borrow -> text.append(borrow).append(" "));
            text.append("\n");
        }
        return text.toString();
    }
}

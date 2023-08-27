package fitmate.mailserver.service;

import fitmate.mailserver.ServiceConst;
import fitmate.mailserver.domain.FindPasswordRequest;
import fitmate.mailserver.form.VerificationRequestForm;
import fitmate.mailserver.repository.FindPasswordRequestRepository;
import fitmate.mailserver.repository.MailVerificationRequestRepository;
import fitmate.mailserver.repository.VerifiedMailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindPasswordService {
    private final FindPasswordRequestRepository findPasswordRequestRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Transactional
    public String createRequest(String mailAddress) {
        findPasswordRequestRepository.deleteFindPasswordRequest(mailAddress);
        FindPasswordRequest fpr = FindPasswordRequest.createFindPasswordRequest(mailAddress);
        findPasswordRequestRepository.save(fpr);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender.concat("@fitmate.page"));
        mailMessage.setTo(mailAddress);
        mailMessage.setText(fpr.getVerificationCode());
        mailMessage.setSubject("Your email verification code");
        javaMailSender.send(mailMessage);
        log.info("created find password request! ({}) ID:[{}], Address[{}], Code[{}]", fpr.getCreatedTime(), fpr.getId(), fpr.getMailAddress(), fpr.getVerificationCode());
        return "ok";
    }

    @Transactional
    public String verifyCode(String mailAddress, String code) {
        FindPasswordRequest fpr = findPasswordRequestRepository.findByMailAddress(mailAddress);
        if (fpr == null) {
            return "ERR: find password request doesn't exists!";
        }
        if (fpr.getCreatedTime().isBefore(LocalDateTime.now().minusMinutes(ServiceConst.FIND_PASSWORD_OUTDATED_MINUTES))) {
            return "ERR: you got staled verification code create new request!";
        }
        if (!fpr.getVerificationCode().equals(code)) {
            return "ERR: wrong verification code";
        }
        return "ok";
    }
}

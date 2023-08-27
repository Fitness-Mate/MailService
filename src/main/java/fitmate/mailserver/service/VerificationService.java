package fitmate.mailserver.service;

import fitmate.mailserver.ServiceConst;
import fitmate.mailserver.domain.MailVerificationRequest;
import fitmate.mailserver.domain.VerifiedMail;
import fitmate.mailserver.form.RandomCodeVerifyingRequestForm;
import fitmate.mailserver.form.UuidVerifyingRequestForm;
import fitmate.mailserver.form.VerificationRequestForm;
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
public class VerificationService {
    private final MailVerificationRequestRepository mailVerificationRequestRepository;
    private final VerifiedMailRepository verifiedMailRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private  String sender;
    @Transactional
    public String createRequest(VerificationRequestForm verificationRequestForm) {
        // 이미 요청이 진행중인경우 진행중인 요청 엔티티를 삭제
        mailVerificationRequestRepository.deleteMailVerificationRequest(verificationRequestForm.getMailAddress());
        // TODO
        // 메일 주소 regex 검증

        /**
         * 인증 요청 엔티티 생성
         */
        MailVerificationRequest mvr = MailVerificationRequest.createMailVerification(verificationRequestForm.getMailAddress());
        mailVerificationRequestRepository.save(mvr);

        /**
         * 이메일 발송 요청 전송
         */
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender.concat("@fitmate.page"));
        mailMessage.setTo(verificationRequestForm.getMailAddress());
//        mailMessage.setTo("chanhale@fitmate.page");
        mailMessage.setText(mvr.getVerificationCode());
        mailMessage.setSubject("Your email verification code");
        javaMailSender.send(mailMessage);
        log.info("created request! ({}) ID:[{}], Address[{}], Code[{}]", mvr.getCreatedTime(), mvr.getId(), mvr.getMailAddress(), mvr.getVerificationCode());
        return "ok";
    }

    @Transactional
    public VerifiedMail createVerifiedMail(RandomCodeVerifyingRequestForm randomCodeVerifyingRequestForm) {
        MailVerificationRequest mvr = mailVerificationRequestRepository.findByMailAddress(randomCodeVerifyingRequestForm.getMailAddress());
        log.info("mvr=[{}], add=[{}]", mvr, randomCodeVerifyingRequestForm.getMailAddress());
        if (mvr == null || mvr.getCreatedTime().isBefore(LocalDateTime.now().minusMinutes(ServiceConst.VERIFYING_REQUEST_OUTDATED_MINUTES))
                || !mvr.getVerificationCode().equals(randomCodeVerifyingRequestForm.getVerificationCode())) {
            return null;
        }
        log.info("code verifying success for [{}], [{}]==[{}]", randomCodeVerifyingRequestForm.getMailAddress(), randomCodeVerifyingRequestForm.getVerificationCode(), mvr.getVerificationCode());
        verifiedMailRepository.deleteVerifiedMail(randomCodeVerifyingRequestForm.getMailAddress());
        VerifiedMail vm = VerifiedMail.createVerifiedMail(mvr);
        verifiedMailRepository.save(vm);
        return vm;
    }

    public String checkUuidVital(UuidVerifyingRequestForm uuidVerifyingRequestForm) {
        VerifiedMail vm = verifiedMailRepository.findByMailAddress(uuidVerifyingRequestForm.getMailAddress());
        if (vm == null) {
            return "requested mail did not verified";
        }
        if (vm.getCreatedTime().isBefore(LocalDateTime.now().minusMinutes(ServiceConst.VERIFIED_MAIL_OUTDATED_MINUTES))) {
            return "staled mail, please verify again";
        }
        if (!vm.getUuid().equals(uuidVerifyingRequestForm.getUuid())) {
            return "wrong uuid";
        }log.info("uuid verifying success for [{}], [{}]==[{}]", uuidVerifyingRequestForm.getMailAddress(), vm.getUuid(), uuidVerifyingRequestForm.getUuid());

        return "ok";
    }

    @Transactional
    public void purge() {
        verifiedMailRepository.deleteAll();
        mailVerificationRequestRepository.deleteAll();
    }

}

package fitmate.service;

import fitmate.domain.MailVerificationRequest;
import fitmate.form.VerificationRequestForm;
import fitmate.repository.MailVerificationRequestRepository;
import fitmate.repository.VerifiedMailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class verificationService {
    private final MailVerificationRequestRepository mailVerificationRequestRepository;
    private final VerifiedMailRepository verifiedMailRepository;

    @Transactional
    public String createRequest(VerificationRequestForm verificationRequestForm) {
        // 이미 요청이 진행중인경우
        if (mailVerificationRequestRepository.findVMailVerificationRequestByMailAddress(verificationRequestForm.getMailAddress()) != null
                || verifiedMailRepository.findVerifiedMailByMailAddress(verificationRequestForm.getMailAddress()) != null) {
            return "already verification request in process!";
        }

        /**
         * 인증 요청 엔티티 생성
         */
        MailVerificationRequest mvr = MailVerificationRequest.createMailVerification(verificationRequestForm.getMailAddress());
        mailVerificationRequestRepository.save(mvr);
        /**
         * 이메일 발송 요청 전송
         */
        log.info("created request! ({})\nID:[{}], Address[{}], Code[{}]", mvr.getRequestDate(), mvr.getId(), mvr.getMailAddress(), mvr.getVerificationCode());
        return "ok";
    }
}

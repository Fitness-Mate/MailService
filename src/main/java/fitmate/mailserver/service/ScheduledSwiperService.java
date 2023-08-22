package fitmate.mailserver.service;

import fitmate.mailserver.domain.MailVerificationRequest;
import fitmate.mailserver.domain.VerifiedMail;
import fitmate.mailserver.repository.MailVerificationRequestRepository;
import fitmate.mailserver.repository.VerifiedMailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledSwiperService {
    private final MailVerificationRequestRepository mailVerificationRequestRepository;
    private final VerifiedMailRepository verifiedMailRepository;

    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void run() {
        /**
         * 기한이 지난 인증요청 삭제
         */
        List<MailVerificationRequest> outdatedRequest = mailVerificationRequestRepository.getOutdated();
        if (outdatedRequest.isEmpty()) {
            log.info("ScheduledSwiperService[{}]: no outdated requests", LocalDateTime.now());
        } else {
            log.info("ScheduledSwiperService[{}]: outdated requests", LocalDateTime.now());
            for (MailVerificationRequest mailVerificationRequest : outdatedRequest) {
                log.info("ScheduledSwiperService[{}]: outdated requests \nAddress=[{}], createdTime:[{}]", LocalDateTime.now(), mailVerificationRequest.getMailAddress(), mailVerificationRequest.getCreatedTime());
                mailVerificationRequestRepository.deleteMailVerificationRequest(mailVerificationRequest);
            }
        }

        /**
         * 기한이 지난 인증된 메일 삭제
         */
        List<VerifiedMail> outdatedMail = verifiedMailRepository.getOutdated();
        if (outdatedMail.isEmpty()) {
            log.info("ScheduledSwiperService[{}]: no outdated verified mail", LocalDateTime.now());
        } else {
            log.info("ScheduledSwiperService[{}]: outdated verified mail", LocalDateTime.now());
            for (VerifiedMail verifiedMail : outdatedMail) {
                log.info("ScheduledSwiperService[{}]: outdated verified mail \nAddress=[{}], createdTime:[{}]", LocalDateTime.now(), verifiedMail.getMailAddress(), verifiedMail.getCreatedTime());
                verifiedMailRepository.deleteVerifiedMail(verifiedMail);
            }
        }
    }
}

package fitmate.mailserver.service;

import fitmate.mailserver.domain.FindPasswordRequest;
import fitmate.mailserver.domain.MailVerificationRequest;
import fitmate.mailserver.domain.VerifiedMail;
import fitmate.mailserver.repository.FindPasswordRequestRepository;
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
    private final FindPasswordRequestRepository findPasswordRequestRepository;

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void run() {
        /**
         * 기한이 지난 인증요청 삭제
         */
        List<MailVerificationRequest> outdatedRequest = mailVerificationRequestRepository.getOutdated();
        if (outdatedRequest.isEmpty()) {
            log.info("ScheduledSwiperService[{}]: no outdated requests", LocalDateTime.now());
        } else {
            for (MailVerificationRequest mailVerificationRequest : outdatedRequest) {
                log.info("ScheduledSwiperService[{}]: outdated requests Address=[{}], createdTime:[{}]", LocalDateTime.now(), mailVerificationRequest.getMailAddress(), mailVerificationRequest.getCreatedTime());
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
            for (VerifiedMail verifiedMail : outdatedMail) {
                log.info("ScheduledSwiperService[{}]: outdated verified mail \nAddress=[{}], createdTime:[{}]", LocalDateTime.now(), verifiedMail.getMailAddress(), verifiedMail.getCreatedTime());
                verifiedMailRepository.deleteVerifiedMail(verifiedMail);
            }
        }

        List<FindPasswordRequest> outdatedPasswordRequest = findPasswordRequestRepository.getOutdated();
        if (outdatedPasswordRequest.isEmpty()) {
            log.info("ScheduledSwiperService[{}]: no outdated password requests", LocalDateTime.now());
        } else {
            for (FindPasswordRequest findPasswordRequest : outdatedPasswordRequest) {
                log.info("ScheduledSwiperService[{}]: outdated password requests Address=[{}], createdTime:[{}]", LocalDateTime.now(), findPasswordRequest.getMailAddress(), findPasswordRequest.getCreatedTime());
                findPasswordRequestRepository.deleteFindPasswordRequest(findPasswordRequest);
            }
        }

    }
}

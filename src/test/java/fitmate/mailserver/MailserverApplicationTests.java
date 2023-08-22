package fitmate.mailserver;

import fitmate.mailserver.domain.MailVerificationRequest;
import fitmate.mailserver.domain.VerifiedMail;
import fitmate.mailserver.form.VerificationRequestForm;
import fitmate.mailserver.repository.MailVerificationRequestRepository;
import fitmate.mailserver.repository.VerifiedMailRepository;
import fitmate.mailserver.service.VerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MailserverApplicationTests {

//
//	@Autowired
//	VerificationService vs;
//	@Autowired
//	MailVerificationRequestRepository mailVerificationRequestRepository;
//	@Autowired
//	VerifiedMailRepository verifiedMailRepository;
//	@Test
//	void 도메인생성테스트() {
//
//		MailVerificationRequest mvr = MailVerificationRequest.createMailVerification("cdsfds");
//		VerifiedMail vm = VerifiedMail.createVerifiedMail(mvr);
//	}
//	@Test
//	void 메일전송테스트() {
//		List<MailVerificationRequest> outdatedRequest = mailVerificationRequestRepository.getOutdated();
//		if (outdatedRequest.isEmpty()) {
//		} else {
//			for (MailVerificationRequest mailVerificationRequest : outdatedRequest) {
//				mailVerificationRequestRepository.deleteMailVerificationRequest(mailVerificationRequest);
//			}
//		}
//
//		/**
//		 * 기한이 지난 인증된 메일 삭제
//		 */
//		List<VerifiedMail> outdatedMail = verifiedMailRepository.getOutdated();
//		if (outdatedMail.isEmpty()) {
//		} else {
//			for (VerifiedMail verifiedMail : outdatedMail) {
//				verifiedMailRepository.deleteVerifiedMail(verifiedMail);
//			}
//		}
//		vs.createRequest(new VerificationRequestForm("chan"));
//	}

}

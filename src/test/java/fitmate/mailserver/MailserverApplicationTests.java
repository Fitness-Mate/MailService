package fitmate.mailserver;

import fitmate.domain.MailVerificationRequest;
import fitmate.domain.VerifiedMail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailserverApplicationTests {

	@Test
	void 도메인생성테스트() {

		MailVerificationRequest mvr = MailVerificationRequest.createMailVerification("cdsfds");
		VerifiedMail vm = VerifiedMail.createVerifiedMail(mvr);

	}

}

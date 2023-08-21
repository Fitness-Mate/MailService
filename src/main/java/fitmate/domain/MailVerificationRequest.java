package fitmate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.Random;
import java.util.random.RandomGenerator;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
public class MailVerificationRequest {
    @Id
    @GeneratedValue
    @Column(name="mail_verification_request_id")
    private Long id;
    private LocalDate requestDate;
    private String mailAddress;
    private String verificationCode;

    public static MailVerificationRequest createMailVerification(String mailAddress) {
        MailVerificationRequest result = new MailVerificationRequest();

        result.requestDate = LocalDate.now();
        result.mailAddress = mailAddress;
        result.verificationCode = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        log.info("random: [{}]", result.verificationCode);

        return result;
    }

}

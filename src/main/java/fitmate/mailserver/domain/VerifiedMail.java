package fitmate.mailserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
public class VerifiedMail {
    @Id
    @GeneratedValue
    @Column(name = "verified_mail_id")
    private Long Id;
    private String mailAddress;
    private LocalDateTime createdTime;
    private String uuid;

    public static VerifiedMail createVerifiedMail(MailVerificationRequest mailVerificationRequest) {
        VerifiedMail result = new VerifiedMail();
        result.mailAddress = mailVerificationRequest.getMailAddress();
        result.uuid = UUID.randomUUID().toString();
        result.createdTime = LocalDateTime.now();
        log.info("uuid: [{}]", result.uuid);
        return result;
    }

}

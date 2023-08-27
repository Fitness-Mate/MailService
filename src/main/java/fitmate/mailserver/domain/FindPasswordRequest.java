package fitmate.mailserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
public class FindPasswordRequest {
    @Id
    @GeneratedValue
    @Column(name="mail_verification_request_id")
    private Long id;
    private LocalDateTime createdTime;
    private String mailAddress;
    private String verificationCode;

    public static FindPasswordRequest createFindPasswordRequest(String mailAddress) {
        FindPasswordRequest result = new FindPasswordRequest();

        result.createdTime = LocalDateTime.now();
        result.mailAddress = mailAddress;
        result.verificationCode = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        log.info("random: [{}]", result.verificationCode);

        return result;
    }
}

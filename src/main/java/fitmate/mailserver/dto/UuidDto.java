package fitmate.mailserver.dto;

import fitmate.mailserver.domain.VerifiedMail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UuidDto {
    private String status;
    private String mailAddress;
    private String uuid;

    public static UuidDto createUuidDto(String status, VerifiedMail verifiedMail) {
        UuidDto result = new UuidDto();
        result.status = status;
        if (verifiedMail != null) {
            result.mailAddress = verifiedMail.getMailAddress();
            result.uuid = verifiedMail.getUuid();
        }
        return result;
    }
}

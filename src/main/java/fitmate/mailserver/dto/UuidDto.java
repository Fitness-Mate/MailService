package fitmate.mailserver.dto;

import fitmate.mailserver.domain.VerifiedMail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UuidDto {
    private String mailAddress;
    private String uuid;

    public static UuidDto createUuidDto(VerifiedMail verifiedMail) {
        UuidDto result = new UuidDto();
        result.mailAddress = verifiedMail.getMailAddress();
        result.uuid = verifiedMail.getUuid();

        return result;
    }
}

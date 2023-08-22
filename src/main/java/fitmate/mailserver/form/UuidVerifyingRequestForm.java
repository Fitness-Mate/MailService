package fitmate.mailserver.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UuidVerifyingRequestForm {
    private String mailAddress;
    private String uuid;

}

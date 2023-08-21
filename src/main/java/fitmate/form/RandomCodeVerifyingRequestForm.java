package fitmate.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RandomCodeVerifyingRequestForm {
    private String mailAddress;
    private String verificationCode;

}

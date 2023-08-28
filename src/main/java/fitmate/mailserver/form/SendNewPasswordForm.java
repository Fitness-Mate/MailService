package fitmate.mailserver.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendNewPasswordForm {
    private String mailAddress;
    private String newPassword;
}

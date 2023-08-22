package fitmate.mailserver.controller;

import fitmate.mailserver.domain.VerifiedMail;
import fitmate.mailserver.dto.UuidDto;
import fitmate.mailserver.form.RandomCodeVerifyingRequestForm;
import fitmate.mailserver.form.UuidVerifyingRequestForm;
import fitmate.mailserver.form.VerificationRequestForm;
import fitmate.mailserver.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailServiceController {

    private final VerificationService verificationService;

    @PostMapping("/verify/mail")
    @ResponseBody
    public String verifyMail(@RequestBody VerificationRequestForm verificationRequestForm) {
        if (verificationRequestForm.getMailAddress() == null) {
            log.info("bad request for verifyMail");
            return "bad request";
        }
        return verificationService.createRequest(verificationRequestForm);
    }

    @PostMapping("/verify/code")
    @ResponseBody
    public UuidDto verifyCode(@RequestBody RandomCodeVerifyingRequestForm randomCodeVerifyingRequestForm) {
        if (randomCodeVerifyingRequestForm.getVerificationCode() == null || randomCodeVerifyingRequestForm.getMailAddress() == null) {
            log.info("bad request for verifyCode");
            return null;
        }
        VerifiedMail vm = verificationService.createVerifiedMail(randomCodeVerifyingRequestForm);
        if (vm == null) {
            return null;
        }
        return UuidDto.createUuidDto(vm);
    }

    @PostMapping("/verify/uuid")
    @ResponseBody
    public String verifyUuid(@RequestBody UuidVerifyingRequestForm uuidVerifyingRequestForm) {
        if (uuidVerifyingRequestForm.getUuid() == null || uuidVerifyingRequestForm.getMailAddress()== null) {
            log.info("bad request for verifyUuid");
            return "bad request";
        }
        if (!verificationService.checkUuidVital(uuidVerifyingRequestForm))
            return "fail";
        return "ok";
    }
}

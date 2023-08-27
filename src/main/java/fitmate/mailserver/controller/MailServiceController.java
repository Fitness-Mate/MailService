package fitmate.mailserver.controller;

import fitmate.mailserver.domain.VerifiedMail;
import fitmate.mailserver.dto.UuidDto;
import fitmate.mailserver.form.RandomCodeVerifyingRequestForm;
import fitmate.mailserver.form.UuidVerifyingRequestForm;
import fitmate.mailserver.form.VerificationRequestForm;
import fitmate.mailserver.service.FindPasswordService;
import fitmate.mailserver.service.VerificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailServiceController {

    private final VerificationService verificationService;
    private final FindPasswordService findPasswordService;

    @PostMapping("/register/verify/mail")
    @ResponseBody
    public String verifyMail(@RequestBody VerificationRequestForm verificationRequestForm, HttpServletResponse response) {
        if (verificationRequestForm.getMailAddress() == null) {
            log.info("bad request for verifyMail");
            return "bad request";
        }
        return verificationService.createRequest(verificationRequestForm);
    }

    @PostMapping("/register/verify/code")
    @ResponseBody
    public UuidDto verifyCode(@RequestBody RandomCodeVerifyingRequestForm randomCodeVerifyingRequestForm, HttpServletResponse response) {
        if (randomCodeVerifyingRequestForm.getVerificationCode() == null || randomCodeVerifyingRequestForm.getMailAddress() == null) {
            log.info("bad request for verifyCode");
            return UuidDto.createUuidDto("bad request for verifyCode", null);
        }
        VerifiedMail vm = verificationService.createVerifiedMail(randomCodeVerifyingRequestForm);
        if (vm == null) {
            return UuidDto.createUuidDto("requested mail doesn't exists or wrong code", null);
        }
        return UuidDto.createUuidDto("ok", vm);
    }

    @PostMapping("/register/verify/uuid")
    @ResponseBody
    public String verifyUuid(@RequestBody UuidVerifyingRequestForm uuidVerifyingRequestForm, HttpServletResponse response) {
        if (uuidVerifyingRequestForm.getUuid() == null || uuidVerifyingRequestForm.getMailAddress() == null) {
            log.info("bad request for verifyUuid");
            return "verified mail doesn't exist or wrong uuid";
        }
        return verificationService.checkUuidVital(uuidVerifyingRequestForm);
    }

    @PostMapping("/password/verify/mail")
    @ResponseBody
    public String findPassword(@RequestBody VerificationRequestForm verificationRequestForm) {
        if (verificationRequestForm.getMailAddress() == null) {
            log.info("bad request for verifyMail");
            return "bad request";
        }
        return findPasswordService.createRequest(verificationRequestForm.getMailAddress());
    }

    @PostMapping("/password/verify/code")
    @ResponseBody
    public String findPasswordVerifyCode(@RequestBody RandomCodeVerifyingRequestForm randomCodeVerifyingRequestForm) {
        if (randomCodeVerifyingRequestForm.getVerificationCode() == null || randomCodeVerifyingRequestForm.getMailAddress() == null) {
            log.info("bad request for verifyCode");
            return "bad request for verifyCode";
        }
        return findPasswordService.verifyCode(randomCodeVerifyingRequestForm.getMailAddress(), randomCodeVerifyingRequestForm.getVerificationCode());
    }


    @PostMapping("/purge")
    @ResponseBody
    public String purge() {

        verificationService.purge();
        log.info("!!!purge requested!!!");
        return "purged";
    }
}

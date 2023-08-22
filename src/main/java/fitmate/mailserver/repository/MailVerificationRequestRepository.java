package fitmate.mailserver.repository;

import fitmate.mailserver.ServiceConst;
import fitmate.mailserver.domain.MailVerificationRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MailVerificationRequestRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(MailVerificationRequest mailVerificationRequest) {
        if (mailVerificationRequest.getId() == null) {
            em.persist(mailVerificationRequest);
        } else {
            em.merge(mailVerificationRequest);
        }
    }

    public void deleteMailVerificationRequest(String mail) {
        List<MailVerificationRequest> list =  em.createQuery("select mvr from MailVerificationRequest mvr where mvr.mailAddress = :mail ", MailVerificationRequest.class)
                .setParameter("mail", mail)
                .getResultList();
        for (MailVerificationRequest mvr : list) {
            em.remove(mvr);
        }
    }


    public MailVerificationRequest findByMailAddress(String mail) {
        return em.createQuery("select mvr from MailVerificationRequest mvr where mvr.mailAddress = :mail", MailVerificationRequest.class)
                .setParameter("mail", mail)
                .getResultList().stream().findFirst().orElse(null);
    }


    public List<MailVerificationRequest> getOutdated() {
        LocalDateTime deadline = LocalDateTime.now().minusMinutes(ServiceConst.VERIFYING_REQUEST_OUTDATED_MINUTES);
        log.info("deadline[{}]", deadline);
        return em.createQuery("select mvr from MailVerificationRequest mvr where mvr.createdTime <= :deadline ", MailVerificationRequest.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }
    public void deleteMailVerificationRequest(MailVerificationRequest mvr) {
        if (mvr != null && mvr.getId() != null) {
            em.remove(mvr);
        }
    }

}

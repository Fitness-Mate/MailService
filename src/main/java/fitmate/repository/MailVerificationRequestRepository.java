package fitmate.repository;

import fitmate.domain.MailVerificationRequest;
import fitmate.domain.VerifiedMail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
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
        List<MailVerificationRequest> list =  em.createQuery("select mvr from MailVerificationRequest mvr where mvr.mailAddress=:mail ", MailVerificationRequest.class)
                .setParameter("mail", mail)
                .getResultList();
        for (MailVerificationRequest mvr : list) {
            em.remove(mvr);
        }
    }

    public MailVerificationRequest findVMailVerificationRequestByMailAddress(String mail) {
        return em.createQuery("select mvr from MailVerificationRequest mvr where mvr.mailAddress=:mail ", MailVerificationRequest.class)
                .setParameter("mail", mail)
                .getResultList().stream().findFirst().orElse(null);

    }

}

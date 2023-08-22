package fitmate.mailserver.repository;

import fitmate.ServiceConst;
import fitmate.mailserver.domain.VerifiedMail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VerifiedMailRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(VerifiedMail verifiedMail) {
        if (verifiedMail.getId() == null) {
            em.persist(verifiedMail);
        } else {
            em.merge(verifiedMail);
        }
    }

    public void deleteVerifiedMail(String mail) {
        List<VerifiedMail> list =  em.createQuery("select vm from VerifiedMail vm where vm.mailAddress=:mail ", VerifiedMail.class)
                .setParameter("mail", mail)
                .getResultList();
        for (VerifiedMail vm : list) {
            em.remove(vm);
        }
    }

    public VerifiedMail findByMailAddress(String mail) {
        return em.createQuery("select vm from VerifiedMail vm where vm.mailAddress=:mail ", VerifiedMail.class)
                .setParameter("mail", mail)
                .getResultList().stream().findFirst().orElse(null);

    }
    public List<VerifiedMail> getOutdated() {
        LocalDateTime deadline = LocalDateTime.now().minusMinutes(ServiceConst.VERIFIED_MAIL_OUTDATED_MINUTES);
        return em.createQuery("select vm from VerifiedMail vm where vm.createdTime<=:deadline ", VerifiedMail.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }
    public void deleteVerifiedMail(VerifiedMail vm) {
        if (vm != null && vm.getId() != null) {
            em.remove(vm);
        }
    }

}

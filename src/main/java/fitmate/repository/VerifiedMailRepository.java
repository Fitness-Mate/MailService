package fitmate.repository;

import fitmate.domain.VerifiedMail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    public VerifiedMail findVerifiedMailByMailAddress(String mail) {
        return em.createQuery("select vm from VerifiedMail vm where vm.mailAddress=:mail ", VerifiedMail.class)
                .setParameter("mail", mail)
                .getResultList().stream().findFirst().orElse(null);

    }

}

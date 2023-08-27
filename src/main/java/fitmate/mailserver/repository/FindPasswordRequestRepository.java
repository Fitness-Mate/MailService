package fitmate.mailserver.repository;

import fitmate.mailserver.ServiceConst;
import fitmate.mailserver.domain.FindPasswordRequest;
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
public class FindPasswordRequestRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(FindPasswordRequest findPasswordRequest) {
        if (findPasswordRequest.getId() == null) {
            em.persist(findPasswordRequest);
        } else {
            em.merge(findPasswordRequest);
        }
    }

    public void deleteFindPasswordRequest(String mail) {
        List<FindPasswordRequest> list =  em.createQuery("select mvr from FindPasswordRequest mvr where mvr.mailAddress = :mail ", FindPasswordRequest.class)
                .setParameter("mail", mail)
                .getResultList();
        for (FindPasswordRequest mvr : list) {
            em.remove(mvr);
        }
    }


    public FindPasswordRequest findByMailAddress(String mail) {
        return em.createQuery("select mvr from FindPasswordRequest mvr where mvr.mailAddress = :mail", FindPasswordRequest.class)
                .setParameter("mail", mail)
                .getResultList().stream().findFirst().orElse(null);
    }


    public List<FindPasswordRequest> getOutdated() {
        LocalDateTime deadline = LocalDateTime.now().minusMinutes(ServiceConst.FIND_PASSWORD_OUTDATED_MINUTES);
        log.info("deadline[{}]", deadline);
        return em.createQuery("select mvr from FindPasswordRequest mvr where mvr.createdTime <= :deadline ", FindPasswordRequest.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }
    public void deleteFindPasswordRequest(FindPasswordRequest mvr) {
        if (mvr != null && mvr.getId() != null) {
            em.remove(mvr);
        }
    }
    public void deleteAll() {
        List<FindPasswordRequest> vml = em.createQuery("select vm from FindPasswordRequest vm", FindPasswordRequest.class)
                .setMaxResults(1000)
                .getResultList();
        for (FindPasswordRequest findPasswordRequest : vml) {
            em.remove(findPasswordRequest);
        }
    }
}

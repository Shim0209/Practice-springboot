package practicesecurity.demo.Repository;

import org.springframework.stereotype.Repository;
import practicesecurity.demo.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Member member) {entityManager.persist(member);}

    public Member findOne(String id) {return entityManager.find(Member.class, id);}

    public List<Member> findAll () {
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findByEmail(String email) {
        return entityManager.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}

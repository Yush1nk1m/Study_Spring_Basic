package yushin.yushinspringbasic.repository;

import yushin.yushinspringbasic.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;     // JPA는 entity manager를 이용해 모든 요소가 관리된다

    public JpaMemberRepository(EntityManager em) {      // JPA를 사용하려면 entity manager를 주입받아야 한다
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);     // persist: 영속시키다 -> (데이터를 DB에) 영속시킨다(저장한다)
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);      // find 메소드에 찾고자 하는 데이터의 클래스와 키를 넘겨준다
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)   // :name - 파라미터 표시
                .setParameter("name", name)     // 파라미터의 이름 name에 메소드 매개변수 name 전달
                .getResultList();   // 결과를 리스트 컨테이너로 반환
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)   // qlString: JPQL이라는 쿼리 언어로, 객체 대상으로 쿼리를 날리는 기술이다
                .getResultList();       // 결과를 리스트 컨테이너로 반환

    }
}

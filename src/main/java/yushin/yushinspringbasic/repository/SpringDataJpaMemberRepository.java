package yushin.yushinspringbasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yushin.yushinspringbasic.domain.Member;

import java.util.Optional;

/* 스프링 데이터 JPA가 인식해서 자동적으로 구현체를 생성한다 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {    // 인터페이스가 인터페이스를 받을 때는 extends를 사용한다

    // JPQL: select m from Member m where m.name = ?
    Optional<Member> findByName(String name);
}

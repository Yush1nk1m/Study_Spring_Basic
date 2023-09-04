package yushin.yushinspringbasic.repository;

import yushin.yushinspringbasic.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);     // 회원을 저장하는 메소드: 저장된 회원을 반환
    Optional<Member> findById(Long id);         // NULL을 반환할 때 Optional로 감싸서 반환
    Optional<Member> findByName(String name);   // NULL을 반환할 때 Optional로 감싸서 반환
    List<Member> findAll();
}

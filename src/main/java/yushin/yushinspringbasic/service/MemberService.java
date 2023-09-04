package yushin.yushinspringbasic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yushin.yushinspringbasic.domain.Member;
import yushin.yushinspringbasic.repository.MemberRepository;
import yushin.yushinspringbasic.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

//@Service    // 스프링 컨테이너가 찾아서 서비스를 등록할 수 있게 함
public class MemberService {

    private final MemberRepository memberRepository;

    /* 외부에서 같은 리포지토리 인스턴스를 사용하게 하기 위해 수정한다.
    *  서비스 클래스에서 객체를 생성하지 않고 외부에서 생성된 객체가 주입된다.
    *  이와 같이 외부에서 해당 클래스의 객체를 결정해 주는 것을 Dependency Injection(DI)이라고 한다. */
    //@Autowired      // MemberService 클래스의 인스턴스 생성 시 자동으로 MemberRepository의 객체 인스턴스를 생성함
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 가입 메소드 */
    public Long join(Member member) {
        // 중복 회원 검사
        validateDuplicatedMember(member);

        memberRepository.save(member);      // 리포지토리에 회원 정보 저장
        return member.getId();              // 회원의 아이디 반환
    }

    private void validateDuplicatedMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 단일 회원 조회 */
    public Optional<Member> findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

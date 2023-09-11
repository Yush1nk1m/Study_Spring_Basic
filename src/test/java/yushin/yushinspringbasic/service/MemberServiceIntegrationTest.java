package yushin.yushinspringbasic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yushin.yushinspringbasic.domain.Member;
import yushin.yushinspringbasic.repository.MemberRepository;
import yushin.yushinspringbasic.repository.MemoryMemberRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest     // 스프링 컨테이너를 실행한다
@Transactional      // 테스트 이후 DB의 상태를 이전으로 롤백한다
public class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    // 다른 객체를 생성했는데 저장소가 공유되는 이유: Map 자료형 멤버가 static 멤버이기 때문이다.

    @Test
    public void 회원가입() {   // 테스트 코드는 과감하게 한글 메소드명을 사용하여도 된다
        // given: 주어진 데이터
        Member member = new Member();
        member.setName("yeonwooAhn");

        // when: 행할 동작
        Long saveId = memberService.join(member);

        // then: 검증
        Member m = memberService.findMember(saveId).get();
        assertThat(member.getName()).isEqualTo(m.getName());
    }

    @Test
    public void 중복회원가입예외() {
        // given
        Member member1 = new Member();
        member1.setName("Yushin1");

        Member member2 = new Member();
        member2.setName("Yushin1");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //try {
        //    memberService.join(member2);
        //    fail();
        //} catch (IllegalStateException e) {
        //    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //}

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
}
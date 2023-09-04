package yushin.yushinspringbasic.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yushin.yushinspringbasic.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        // given: 새로운 멤버 생성
        Member member = new Member();
        member.setName("YUSHIN");

        // when
        repository.save(member);

        // then
        Member result = repository.findById(member.getId()).get();
        //System.out.println("result = " + (result == member));
        assertThat(result).isEqualTo(member);   // 화면에 직접 출력하는 것의 대체물
        //Assertions.assertEquals(result, member);  // equivalent to the above line
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("Yushin");
        repository.save(member1);
        
        Member member2 = new Member();
        member2.setName("Yeonwoo");
        repository.save(member2);

        Member result = repository.findByName("Yushin").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("Yushin");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Yeonwoo");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}

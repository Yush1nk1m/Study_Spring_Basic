package yushin.yushinspringbasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yushin.yushinspringbasic.repository.JdbcMemberRepository;
import yushin.yushinspringbasic.repository.JdbcTemplateMemberRepository;
import yushin.yushinspringbasic.repository.MemberRepository;
import yushin.yushinspringbasic.repository.MemoryMemberRepository;
import yushin.yushinspringbasic.service.MemberService;

import javax.sql.DataSource;

@Configuration      // 스프링 빈을 등록하기 위한 Configuration 클래스
public class SpringConfig {
    // 이 클래스에서 빈으로 등록된 클래스는 컴포넌트 스캔 방식에서 Autowired 될 수 있다

    DataSource dataSource;

    @Autowired      // 자동 DI
    public SpringConfig(DataSource dataSource) {    // 스프링 컨테이너 생성 시 호출
        this.dataSource = dataSource;
    }

    @Bean   // 스프링 빈 등록
    public MemberService memberService() {
        return new MemberService(memberRepository()); // 생성자에 다른 빈을 넣어주어야 한다 -> 다른 빈의 생성자를 주입한다(Dependency Injection)
    }
    
    @Bean   // 스프링 빈 등록
    public MemberRepository memberRepository() {    // MemberRepository 자체는 인터페이스이므로
        return new JdbcTemplateMemberRepository(dataSource);
        //return new JdbcMemberRepository(dataSource);
        //return new MemoryMemberRepository();        // 구현체인 MemoryMemberRepository 객체를 생성한다
    }
}

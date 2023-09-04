package yushin.yushinspringbasic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import yushin.yushinspringbasic.domain.Member;
import yushin.yushinspringbasic.service.MemberService;

@Controller     // 스프링 빈을 등록하더라도 컨트롤러는 컴포넌트 스캔 방식으로 돌아갈 수밖에 없다
public class MemberController {     // 스프링 컨테이너가 생성함 -> 생성자가 호출됨

    private final MemberService memberService;  // new 사용 시 객체 인스턴스가 여러 개 생성되기 때문에 비효율적

    @Autowired      // MemberController 생성자 호출 시 자동으로 필요한 클래스의 객체를 생성하여 연결한다(Dependency Injection), 객체가 스프링 빈으로 등록되지 않으면 Autowired는 동작하지 않는다
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")     // /members/new 주소로 get 방식의 요청이 들어올 경우 메소드 실행, 데이터를 조회하는 경우 주로 쓰인다
    public String createForm() {
        return "members/createMemberForm";  // members/createMemberForm.html을 실행한다
    }

    @PostMapping("/members/new")    // /members/new URL이 post 방식으로 요청되었을 경우 메소드 실행, 데이터를 폼과 같은 방식으로 전달하는 경우 주로 쓰인다
    public String create(MemberForm form) {     // form의 결과 데이터를 parameter로 받아 온다
        Member member = new Member();       // 새로운 회원 클래스 인스턴스를 생성한다
        member.setName(form.getName());     // form의 결과에서 제출된 데이터(이름)를 받아 온다

        memberService.join(member);     // 리포지토리에 새로운 회원을 등록한다

        return "redirect:/";    // 홈페이지로 redirect한다
    }
}

package yushin.yushinspringbasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")        // 주소가 그냥 '/'인 홈페이지 요청이 들어올 경우 메소드가 호출된다, 홈페이지에 대한 사상이 이루어졌으므로 웰컴 페이지는 생략된다
    public String home() {
        return "home";      // 반환값에 의해 home.html이 호출될 것이다
    }
}

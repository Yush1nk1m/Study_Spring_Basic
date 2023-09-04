package yushin.yushinspringbasic.controller;

/*
* 폼을 제출할 경우 input의 name="name" 속성에 의해서 name이라는 parameter를 찾게 되는데,
* MemberForm 클래스의 name이라는 이름을 가진 멤버는 private 멤버이므로 자동적으로 setter가 호출된다.
* */
public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

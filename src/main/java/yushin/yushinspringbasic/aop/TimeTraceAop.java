package yushin.yushinspringbasic.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
* AOP로 공통 관심 사항과 핵심 관심 사항을 분리한다
* 이번 예제에서 시간 측정 로직은 공통 관심 사항, 나머지 회원 가입에 필요한 로직들은 핵심 관심 사항이다
*
*/
@Component
@Aspect
public class TimeTraceAop {

    @Around("execution(* yushin.yushinspringbasic..*(..))")   // 어떤 서브루틴에 적용할지 명시하는 것
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());

        try {
            /*
            * joinPoint: 시간 측정 로직을 적용하고자 하는 메소드
            * proceed(): 해당 메소드를 진행시키는 것을 의미
            */
            return joinPoint.proceed();     // 시간 측정 로직을 적용할 메소드의 실행을 반환
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}

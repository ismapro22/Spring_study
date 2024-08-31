package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component //이렇게 등록해도 되나 빈 등록 선호. 학습 목적으로 인한 사용
public class TimeTraceAop {


    @Around("execution(* hello.hello_spring..*(..))")//원하는 적용 대상
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
        
        
        
    }
}

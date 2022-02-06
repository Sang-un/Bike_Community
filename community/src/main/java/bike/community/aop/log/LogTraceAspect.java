package bike.community.aop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* bike.community.controller..*(..)) " +
            "|| execution(* bike.community.service..*(..)) " +
            "|| execution(* bike.community.repository..*(..))")// == 포인트컷
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{// == 어드바이스
        TraceStatus status = null;

        try{
            String message = joinPoint.getSignature().toShortString() + argsMessage(joinPoint.getArgs());
            status = logTrace.begin(message);
            // ----로직 호출 ----
            Object result = joinPoint.proceed();
            // ----------------
            logTrace.end(status);
            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }

    private String argsMessage(Object[] args) {
        if(args.length == 0) return "";
        String retVal = "";
        for (Object arg : args) retVal = arg + " ";
        return retVal;
    }
}

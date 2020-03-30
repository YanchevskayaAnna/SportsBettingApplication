package sportsbetting.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

    private void before(ProceedingJoinPoint proceedingJoinPoint){
        log.debug("Before method " + proceedingJoinPoint.getSignature());
    }

    private void after(ProceedingJoinPoint proceedingJoinPoint){
        log.debug("After method " + proceedingJoinPoint.getSignature());
    }

    private void afterReturning(ProceedingJoinPoint proceedingJoinPoint){
        log.debug("After returning " + proceedingJoinPoint.getSignature());
    }

    private void afterThrowing(ProceedingJoinPoint proceedingJoinPoint){
        log.debug("After throwing " + proceedingJoinPoint.getSignature());
    }

    private Object aroundTrackTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Parameter(s) passed to the called method  {}  are {} ", proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs());
        LocalTime startTime = LocalTime.now();
        Object object = proceedingJoinPoint.proceed();
        LocalTime endTime = LocalTime.now();
        long timeTaken = endTime.getSecond() - startTime.getSecond();
        log.info("Time taken by {} is {} seconds", proceedingJoinPoint.getSignature(), timeTaken);
        log.info("Returned value by {} is {} ", proceedingJoinPoint.getSignature(), object);
        return object;
    }

    @Around("sportsbetting.aop.CommonJoinPointConfig.businessLayerExecution()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = null;
        try {
            before(joinPoint);
            object = aroundTrackTime(joinPoint);
            after(joinPoint);
            afterReturning(joinPoint);
        } catch (Throwable throwable) {
            afterThrowing(joinPoint);
            throw throwable;
        }
        return object;
    }

}

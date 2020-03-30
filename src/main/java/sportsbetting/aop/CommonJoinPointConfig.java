package sportsbetting.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
    @Pointcut("execution(* sportsbetting.model.*.*(..))")
    public void dataLayerExecution(){}

    @Pointcut("execution(* sportsbetting.service.*.*(..))")
    public void businessLayerExecution(){}
}

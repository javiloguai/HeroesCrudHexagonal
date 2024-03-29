package com.hexagonal.heroestest.infraestructure.config.aspects;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class TimeExecutionLogger {

    private static final Logger LOGGER = LogManager.getLogger(TimeExecutionLogger.class);

    @Around("@annotation(com.hexagonal.heroestest.infraestructure.config.aspects.annotations.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object o = point.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info(String.format("Class Name: %s. Method Name: %s. Time taken for Execution is : %dms",
                point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), (end - start)));
        return o;
    }
}

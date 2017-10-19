package com.qautomatron.kaper.core.listener

import mu.KLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

/**
 * Step Logger for Allure and AspectJ
 */
@Aspect
class StepLogger {

    companion object: KLogging()

    @Pointcut("@annotation(io.qameta.allure.Step)")
    fun annotationPointCutDefinition() {
    }

    @Pointcut("execution(* *(..))")
    fun atExecution() {
    }

    @Before("annotationPointCutDefinition() && atExecution()")
    fun before(pointcut: JoinPoint) {
        logger.info { "STARTING STEP: ${pointcut.getString()}" }
    }

    @After("annotationPointCutDefinition() && atExecution()")
    fun after(pointcut: JoinPoint) {
        logger.info { "STEP DONE: ${pointcut.getString()}" }
    }
}

private fun JoinPoint.getString(): String = "${target.javaClass.simpleName} - ${signature.name}"

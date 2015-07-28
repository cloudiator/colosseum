package components.execution;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import play.Logger;

/**
 * Created by daniel on 27.07.15.
 */
public class LoopRunnableInterceptor implements MethodInterceptor {


    @Override public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (!methodInvocation.getMethod().getName().equals("run")) {
            return methodInvocation.proceed();
        }

        while (!Thread.currentThread().isInterrupted()) {
            Logger.debug("Running " + methodInvocation.getThis().getClass() + " in endless loop.");
            methodInvocation.proceed();
        }
        Logger.debug("Interrupt loop execution of " + methodInvocation.getThis().getClass());
        return null;
    }
}

package components.execution;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import play.Logger;

/**
 * Created by daniel on 28.07.15.
 */
public class StableRunnableInterceptor implements MethodInterceptor {

    @Override public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        if (!methodInvocation.getMethod().getName().equals("run")) {
            return methodInvocation.proceed();
        }

        Logger
            .debug("Running " + methodInvocation.getThis().getClass() + " in stable mode.");
        try {
            return methodInvocation.proceed();
        } catch (Exception e) {
            Logger.error("StableRunner encountered error. Ignoring....", e);
        }

        return null;
    }
}

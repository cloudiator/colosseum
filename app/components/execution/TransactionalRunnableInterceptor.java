package components.execution;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 24.07.15.
 */
public class TransactionalRunnableInterceptor implements MethodInterceptor {


    public TransactionalRunnableInterceptor() {
    }

    @Override public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        if (!methodInvocation.getMethod().getName().equals("run")) {
            return methodInvocation.proceed();
        }

        final boolean readOnly =
            methodInvocation.getMethod().getAnnotation(Transactional.class).readOnly();
        Object ret;
        Logger
            .debug("Running " + methodInvocation.getThis().getClass() + " in transaction context.");

        try {
            ret = JPA.withTransaction("default", readOnly, methodInvocation::proceed);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        Logger.debug(
            "Finished " + methodInvocation.getThis().getClass() + " in transaction context.");
        return ret;
    }
}

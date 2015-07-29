package components.execution;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by daniel on 24.07.15.
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) public @interface Stable {
}

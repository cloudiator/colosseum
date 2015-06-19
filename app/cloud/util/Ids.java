package cloud.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.06.15.
 */

public class Ids {

    public class Id {
        private final String id;

        public Id(String id) {
            checkNotNull(id);
            checkArgument(!id.isEmpty());
            this.id = id;
        }
    }



}



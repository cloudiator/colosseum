package components.auth.config;

import components.auth.AbstractTokenStoreModule;
import components.auth.DatabaseTokenStore;
import components.auth.TokenStore;

/**
 * Created by daniel on 29.09.16.
 */
public class DatabaseTokenStoreModule extends AbstractTokenStoreModule {

    @Override protected Class<? extends TokenStore> tokenStore() {
        return DatabaseTokenStore.class;
    }
}

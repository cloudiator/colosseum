package components.auth.config;

import components.auth.AbstractTokenStoreModule;
import components.auth.DatabaseTokenStore;
import components.auth.TokenStore;

/**
 * {@link AbstractTokenStoreModule} loading {@link DatabaseTokenStore} as {@link TokenStore}
 * implementation.
 */
public class DatabaseTokenStoreModule extends AbstractTokenStoreModule {

    @Override protected Class<? extends TokenStore> tokenStore() {
        return DatabaseTokenStore.class;
    }
}

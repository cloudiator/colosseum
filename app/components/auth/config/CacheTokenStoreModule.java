package components.auth.config;

import components.auth.AbstractTokenStoreModule;
import components.auth.CacheBasedTokenStore;
import components.auth.TokenStore;

/**
 * {@link AbstractTokenStoreModule} loading {@link CacheBasedTokenStore} as {@link TokenStore}
 * implementation.
 */
public class CacheTokenStoreModule extends AbstractTokenStoreModule {

    @Override protected Class<? extends TokenStore> tokenStore() {
        return CacheBasedTokenStore.class;
    }
}

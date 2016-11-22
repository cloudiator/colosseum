package components.auth;

import com.google.inject.AbstractModule;

/**
 * Abstract TokenStoreModule.
 * <p>
 * Overwrite to define module for custom {@link TokenStore} implementations.
 */
public abstract class AbstractTokenStoreModule extends AbstractModule {

    @Override protected final void configure() {
        bind(TokenStore.class).to(tokenStore());
        bind(TokenValidity.class).to(ConfigurationBasedTokenValidity.class);
        bind(TokenService.class).to(TokenServiceImpl.class);
    }

    protected abstract Class<? extends TokenStore> tokenStore();
}

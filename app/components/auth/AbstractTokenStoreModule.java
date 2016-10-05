package components.auth;

import com.google.inject.AbstractModule;

/**
 * Created by daniel on 29.09.16.
 */
public abstract class AbstractTokenStoreModule extends AbstractModule {

    @Override protected final void configure() {
        bind(TokenStore.class).to(tokenStore());
    }

    protected abstract Class<? extends TokenStore> tokenStore();
}

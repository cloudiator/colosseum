package dtos;

import com.google.inject.AbstractModule;

/**
 * Created by daniel on 26.11.14.
 */
public class FormModule extends AbstractModule {

    @Override
    protected void configure() {

        requestStaticInjection(LoginDto.FrontendUserServiceReference.class);

    }
}

package dtos.convert.converters.impl;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import dtos.convert.converters.api.ModelDtoConverter;

/**
 * Base converter module.
 * <p>
 * Registers the basic converters.
 */
public class BaseConverterModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ModelDtoConverter> converterBinder = Multibinder.newSetBinder(binder(), ModelDtoConverter.class);
        converterBinder.addBinding().to(CloudConverter.class);
    }

}

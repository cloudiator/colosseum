package dtos.convert.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.convert.api.ConverterRegistry;
import dtos.convert.converters.api.ModelDtoConverter;
import play.Logger;

import java.util.Set;

/**
 * A generic implementation of the ModelDtoConversionService, which automatically
 * registers the dependency injected converts within the registry.
 *
 * @author Daniel Baur
 */
@Singleton
public class DefaultModelDtoConversionService extends GenericModelDtoConversionService {

    /**
     * Constructor.
     *
     * @param converters a set of dependency injected converters.
     */
    @Inject
    public DefaultModelDtoConversionService(Set<ModelDtoConverter> converters) {
        addDefaultConverters(this, converters);
    }

    /**
     * Registers the dependency injected converters within the registry.
     *
     * @param converterRegistry the registry where the converters should be registered.
     * @param converters        the converts that should be registered.
     */
    private void addDefaultConverters(ConverterRegistry converterRegistry, Set<ModelDtoConverter> converters) {
        Logger.debug("Registering default dto converters.");
        converters.forEach(converterRegistry::addConverter);
    }
}

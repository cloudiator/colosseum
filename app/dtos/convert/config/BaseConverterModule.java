package dtos.convert.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import dtos.convert.converters.api.ModelDtoConverter;
import dtos.convert.converters.impl.CloudConverter;
import dtos.convert.converters.impl.CloudHardwareConverter;
import dtos.convert.converters.impl.VirtualMachineConverter;

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
        converterBinder.addBinding().to(VirtualMachineConverter.class);
        converterBinder.addBinding().to(CloudHardwareConverter.class);
    }

}

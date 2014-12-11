package dtos.convert.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import dtos.convert.converters.api.ModelDtoConverter;
import dtos.convert.converters.impl.*;
import models.CloudImage;
import models.CloudLocation;

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
        converterBinder.addBinding().to(CloudImageConverter.class);
        converterBinder.addBinding().to(CloudLocationConverter.class);
        converterBinder.addBinding().to(HardwareConverter.class);
        converterBinder.addBinding().to(ImageConverter.class);
        converterBinder.addBinding().to(CredentialConverter.class);
        converterBinder.addBinding().to(CloudApiConverter.class);
        converterBinder.addBinding().to(ApiConverter.class);
        converterBinder.addBinding().to(FrontendUserConverter.class);
        converterBinder.addBinding().to(UserCredentialConverter.class);
    }

}

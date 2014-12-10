package dtos.config;

import com.google.inject.AbstractModule;
import dtos.*;

/**
 * Created by daniel on 26.11.14.
 */
public class FormModule extends AbstractModule {

    @Override
    protected void configure() {
        requestStaticInjection(LoginDto.References.class);
        requestStaticInjection(VirtualMachineDto.References.class);
        requestStaticInjection(CloudHardwareDto.References.class);
        requestStaticInjection(CloudImageDto.References.class);
        requestStaticInjection(CloudLocationDto.References.class);
    }
}

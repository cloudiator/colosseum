package dtos.config;

import com.google.inject.AbstractModule;
import dtos.LoginDto;
import dtos.VirtualMachineDto;

/**
 * Created by daniel on 26.11.14.
 */
public class FormModule extends AbstractModule {

    @Override
    protected void configure() {
        requestStaticInjection(LoginDto.References.class);
        requestInjection(VirtualMachineDto.References.class);
    }
}

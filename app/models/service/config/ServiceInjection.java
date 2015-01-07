package models.service.config;

import com.google.inject.AbstractModule;
import controllers.security.SecuredToken;
import dtos.*;

/**
 * Created by daniel on 26.11.14.
 */
public class ServiceInjection extends AbstractModule {

    @Override
    protected void configure() {

        //static injection into dto objects
        requestStaticInjection(LoginDto.References.class);
        requestStaticInjection(VirtualMachineDto.References.class);
        requestStaticInjection(CloudHardwareDto.References.class);
        requestStaticInjection(CloudImageDto.References.class);
        requestStaticInjection(CloudLocationDto.References.class);
        requestStaticInjection(ImageDto.References.class);
        requestStaticInjection(CloudApiDto.References.class);
        requestStaticInjection(UserCredentialDto.References.class);
        requestStaticInjection(ApplicationComponentDto.References.class);
        requestStaticInjection(InstanceDto.References.class);

        //static injection into security tokens
        requestStaticInjection(SecuredToken.References.class);
    }
}

package cloud.config;

import cloud.CloudService;
import cloud.ComputeServiceFactory;
import cloud.DefaultCloudService;
import cloud.SwordComputeServiceFactory;
import com.google.inject.AbstractModule;

/**
 * Created by daniel on 28.04.15.
 */
public class CloudModule extends AbstractModule {

    @Override protected void configure() {
        bind(CloudService.class).to(DefaultCloudService.class);
        bind(ComputeServiceFactory.class).to(SwordComputeServiceFactory.class);
    }
}

package cloud;

import com.google.inject.AbstractModule;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;

/**
 * Created by daniel on 28.04.15.
 */
public class CloudModule extends AbstractModule {

    @Override protected void configure() {
        bind(ComputeService.class).to(CompositeComputeService.class);
    }
}

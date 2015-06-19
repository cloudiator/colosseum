package cloud.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;

/**
 * Created by daniel on 28.05.15.
 */
public class HardwareInLocation extends AbstractLocationScopedResource<HardwareFlavor>
    implements HardwareFlavor {

    private final HardwareFlavor hardwareFlavor;

    public HardwareInLocation(HardwareFlavor resource, String cloud, String credential,
        String location) {
        super(resource, cloud, credential, location);
        this.hardwareFlavor = resource;
    }

    @Override public int numberOfCores() {
        return hardwareFlavor.numberOfCores();
    }

    @Override public long mbRam() {
        return hardwareFlavor.mbRam();
    }
}

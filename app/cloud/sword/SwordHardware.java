package cloud.sword;

import cloud.resources.LocationScoped;
import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

/**
 * Created by daniel on 28.04.15.
 */
public class SwordHardware implements HardwareFlavor, LocationScoped {

    private final HardwareFlavor hardwareFlavor;

    SwordHardware(HardwareFlavor hardwareFlavor) {
        this.hardwareFlavor = hardwareFlavor;
    }

    @Override public int numberOfCores() {
        return hardwareFlavor.numberOfCores();
    }

    @Override public long mbRam() {
        return hardwareFlavor.mbRam();
    }

    @Override public String location() {
        return IdScopeByLocations.from(hardwareFlavor.id()).getLocationId();
    }

    @Override public String id() {
        return hardwareFlavor.id();
    }

    @Override public String name() {
        return hardwareFlavor.name();
    }
}

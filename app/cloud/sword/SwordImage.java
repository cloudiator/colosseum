package cloud.sword;

import cloud.resources.LocationScoped;
import de.uniulm.omi.cloudiator.sword.api.domain.Image;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

/**
 * Created by daniel on 28.04.15.
 */
public class SwordImage implements Image, LocationScoped {

    private Image image;

    SwordImage(Image image) {
        this.image = image;
    }

    @Override public String location() {
        return IdScopeByLocations.from(image.id()).getLocationId();
    }

    @Override public String id() {
        return image.id();
    }

    @Override public String name() {
        return image.name();
    }
}

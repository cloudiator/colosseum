package cloud.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.Image;

/**
 * Created by daniel on 28.05.15.
 */
public class ImageInLocation extends AbstractLocationScopedResource<Image>
    implements Image {

    public ImageInLocation(Image resource, String cloud, String credential, String location) {
        super(resource, cloud, credential, location);
    }
}

package cloud.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.Resource;

/**
 * Created by daniel on 20.05.15.
 */
public class AbstractLocationScopedResource<T extends Resource>
    extends AbstractCredentialScopedResource<T>
    implements CredentialScoped, LocationScoped, Resource {

    private String location;

    public AbstractLocationScopedResource(T resource, String cloud, String credential,
        String location) {
        super(resource, cloud, credential);
        this.location = location;
    }

    @Override public String location() {
        return location;
    }

    @Override public String id() {

    }
}

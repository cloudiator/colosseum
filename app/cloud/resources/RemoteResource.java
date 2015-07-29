package cloud.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.Resource;

/**
 * Created by daniel on 03.07.15.
 */
public interface RemoteResource extends Resource {

    String cloudProviderId();

}

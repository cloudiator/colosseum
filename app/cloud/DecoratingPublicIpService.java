package cloud;

import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 06.07.15.
 */
public class DecoratingPublicIpService implements PublicIpService {

    private final PublicIpService publicIpService;

    public DecoratingPublicIpService(PublicIpService publicIpService) {
        this.publicIpService = publicIpService;
    }

    @Override public String addPublicIp(String virtualMachineId) throws PublicIpException {
        checkNotNull(virtualMachineId);
        return this.publicIpService.addPublicIp(DecoratedId.of(virtualMachineId).swordId());
    }

    @Override public void removePublicIp(String virtualMachineId, String ip)
        throws PublicIpException {
        //todo: implement
        throw new UnsupportedOperationException();
    }
}

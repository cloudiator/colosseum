package cloud;

import de.uniulm.omi.cloudiator.sword.api.domain.KeyPair;
import de.uniulm.omi.cloudiator.sword.api.exceptions.KeyPairException;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;

import java.util.List;

/**
 * Created by daniel on 20.05.15.
 */
public class MultiCloudKeyPairService implements KeyPairService {

    public final List<KeyPairService> keyPairServices;

    public MultiCloudKeyPairService(List<KeyPairService> keyPairServices) {
        this.keyPairServices = keyPairServices;
    }

    @Override public KeyPair create(String name) throws KeyPairException {
        KeyPair keyPair = null;
        for (KeyPairService keyPairService : keyPairServices) {
            keyPair = keyPairService.create(name);
        }
        return keyPair;
    }

    @Override public KeyPair create(String name, String publicKey) throws KeyPairException {
        KeyPair keyPair = null;
        for (KeyPairService keyPairService : keyPairServices) {
            keyPair = keyPairService.create(name, publicKey);
        }
        return keyPair;
    }

    @Override public boolean delete(String name) throws KeyPairException {
        boolean success = true;
        for (KeyPairService keyPairService : keyPairServices) {
            boolean result = keyPairService.delete(name);
            if (!result) {
                success = false;
            }
        }
        return success;
    }

    @Override public KeyPair get(String name) throws KeyPairException {
        //TODO: check if want to return null, if it is not present in all key pair services.
        //noinspection LoopStatementThatDoesntLoop
        for (KeyPairService keyPairService : keyPairServices) {
            return keyPairService.get(name);
        }
        return null;
    }
}

package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.CloudCredential;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 01.09.16.
 */
public class ComputeServiceCache {

    private Cache<CacheKey, ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>
        cache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build();

    public void store(CloudCredential cloudCredential,
        ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> computeService) {
        cache.put(CacheKey.of(cloudCredential), computeService);
    }

    public ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> retrieve(
        CloudCredential cloudCredential, ComputeServiceFactory computeServiceFactory) {
        try {
            return cache.get(CacheKey.of(cloudCredential),
                () -> computeServiceFactory.computeService(cloudCredential));
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    private static class CacheKey {

        private final String username;
        private final String password;
        private final String internalProvider;
        @Nullable private final String endpoint;

        private CacheKey(String username, String password, String internalProvider,
            @Nullable String endpoint) {

            checkNotNull(username);
            checkNotNull(password);
            checkNotNull(internalProvider);

            this.username = username;
            this.password = password;
            this.internalProvider = internalProvider;
            this.endpoint = endpoint;
        }

        private static CacheKey of(CloudCredential cloudCredential) {
            return new CacheKey(cloudCredential.getUser(), cloudCredential.getSecret(),
                cloudCredential.getCloud().getEndpoint().orElse(null),
                cloudCredential.getCloud().api().getInternalProviderName());
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            CacheKey cacheKey = (CacheKey) o;

            if (!username.equals(cacheKey.username))
                return false;
            if (!password.equals(cacheKey.password))
                return false;
            if (!internalProvider.equals(cacheKey.internalProvider))
                return false;
            return endpoint != null ?
                endpoint.equals(cacheKey.endpoint) :
                cacheKey.endpoint == null;

        }

        @Override public int hashCode() {
            int result = username.hashCode();
            result = 31 * result + password.hashCode();
            result = 31 * result + internalProvider.hashCode();
            result = 31 * result + (endpoint != null ? endpoint.hashCode() : 0);
            return result;
        }
    }

}

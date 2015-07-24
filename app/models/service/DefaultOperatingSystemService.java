package models.service;

import com.google.inject.Inject;
import models.OperatingSystem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 23.07.15.
 */
public class DefaultOperatingSystemService extends BaseModelService<OperatingSystem>
    implements OperatingSystemService {

    @Inject public DefaultOperatingSystemService(ModelRepository<OperatingSystem> modelRepository) {
        super(modelRepository);
    }

    @Override public OperatingSystem findByImageName(String imageName) {
        Set<OperatingSystem> matches = new HashSet<>();
        for (OperatingSystem operatingSystem : getAll()) {
            String[] needsToContain = {operatingSystem.getVersion(),
                operatingSystem.getOperatingSystemArchitecture().toString(),
                operatingSystem.getOperatingSystemVendor().getName()};
            boolean isMatch = true;
            for (String check : needsToContain) {
                if (!imageName.contains(check)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                matches.add(operatingSystem);
            }
        }
        if (matches.size() == 1) {
            return matches.iterator().next();
        }
        return null;
    }
}

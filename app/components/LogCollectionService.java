package components;

import models.ApplicationInstance;

import java.io.File;

/**
 * Created by daniel on 06.06.16.
 */
public interface LogCollectionService {

    File collectFor(ApplicationInstance applicationInstance);

}

package cloud;

import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;

/**
 * Created by daniel on 27.04.15.
 */
public abstract class AbstractCloudServiceWatcher implements Runnable {

    private ComputeService computeService;

    @Inject protected AbstractCloudServiceWatcher(ComputeService computeService) {
        this.computeService = computeService;
    }

    @Override public void run() {
        watch(this.computeService);
    }

    protected abstract void watch(ComputeService computeService);
}

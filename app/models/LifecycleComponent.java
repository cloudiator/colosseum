package models;

import javax.annotation.Nullable;
import javax.persistence.Entity;

/**
 * Created by daniel on 15.12.14.
 */
@Entity
public class LifecycleComponent extends Component {

    @Nullable
    private String download;
    @Nullable
    private String install;
    @Nullable
    private String start;
    @Nullable
    private String stop;

    @Nullable
    public String getDownload() {
        return download;
    }

    public void setDownload(@Nullable String download) {
        this.download = download;
    }

    @Nullable
    public String getInstall() {
        return install;
    }

    public void setInstall(@Nullable String install) {
        this.install = install;
    }

    @Nullable
    public String getStart() {
        return start;
    }

    public void setStart(@Nullable String start) {
        this.start = start;
    }

    @Nullable
    public String getStop() {
        return stop;
    }

    public void setStop(@Nullable String stop) {
        this.stop = stop;
    }
}

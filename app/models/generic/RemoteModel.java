package models.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.05.15.
 */
@MappedSuperclass public abstract class RemoteModel extends Model {

    private RemoteState remoteState;
    @Column(unique = true, nullable = true) private String remoteId;
    @Column(updatable = false) private String cloudProviderId;

    public RemoteState getRemoteState() {
        return remoteState;
    }

    public void setRemoteState(RemoteState remoteState) {
        this.remoteState = remoteState;
    }

    public String getRemoteId() {
       return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    /**
     * Empty constructor for hibernate
     */
    protected RemoteModel() {
    }

    public RemoteModel(String remoteId) {
        checkNotNull(remoteId);
        checkArgument(!remoteId.isEmpty());
        this.remoteId = remoteId;
    }

    public String getCloudProviderId() {
        return cloudProviderId;
    }

    public void setCloudProviderId(String cloudProviderId) {
        this.cloudProviderId = cloudProviderId;
    }
}

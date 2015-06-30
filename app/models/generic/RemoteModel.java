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
    @Column(updatable = false, unique = true, nullable = true) private String remoteId;

    public RemoteState getRemoteState() {
        return remoteState;
    }

    public void setRemoteState(RemoteState remoteState) {
        this.remoteState = remoteState;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String cloudUuid) {
        this.remoteId = cloudUuid;
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
}

package models.generic;

import javax.persistence.MappedSuperclass;

/**
 * Created by daniel on 12.05.15.
 */
@MappedSuperclass public abstract class RemoteModel extends Model {
    private RemoteState remoteState;

    public RemoteState getRemoteState() {
        return remoteState;
    }

    public void setRemoteState(RemoteState remoteState) {
        this.remoteState = remoteState;
    }
}

package models.repository.api.generic;

import models.generic.RemoteModel;

import javax.annotation.Nullable;

/**
 * Created by daniel on 21.06.15.
 */
public interface RemoteModelRepository<T extends RemoteModel> extends ModelRepository<T>{

    @Nullable T findByRemoteId(String remoteId);

}

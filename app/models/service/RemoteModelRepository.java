package models.service;

import models.generic.RemoteModel;

import javax.annotation.Nullable;

/**
 * Created by daniel on 21.06.15.
 */
interface RemoteModelRepository<T extends RemoteModel> extends ModelRepository<T>{

    @Nullable T findByRemoteId(String remoteId);

}

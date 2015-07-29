package models.service;

import models.generic.RemoteModel;

/**
 * Created by daniel on 21.06.15.
 */
public interface RemoteModelService<T extends RemoteModel> extends ModelService<T> {

    T getByRemoteId(String remoteId);

}

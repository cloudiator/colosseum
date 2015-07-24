package models.service;

import com.google.inject.Inject;
import models.generic.RemoteModel;

/**
 * Created by daniel on 21.06.15.
 */
public class BaseRemoteModelService<T extends RemoteModel> extends BaseModelService<T>
    implements RemoteModelService<T> {

    protected final RemoteModelRepository<T> tRemoteModelRepository;

    @Inject public BaseRemoteModelService(RemoteModelRepository<T> tRemoteModelRepository) {
        super(tRemoteModelRepository);
        this.tRemoteModelRepository = tRemoteModelRepository;
    }

    @Override public T getByRemoteId(String remoteId) {
        return tRemoteModelRepository.findByRemoteId(remoteId);
    }
}

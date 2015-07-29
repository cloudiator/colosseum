package models.service;

import com.google.inject.TypeLiteral;
import models.generic.RemoteModel;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 21.06.15.
 */
class BaseRemoteModelRepositoryJpa<T extends RemoteModel> extends BaseModelRepositoryJpa<T>
    implements RemoteModelRepository<T> {

    @Inject BaseRemoteModelRepositoryJpa(TypeLiteral<T> type) {
        super(type);
    }

    @Nullable @Override public T findByRemoteId(String remoteId) {
        checkNotNull(remoteId);
        String queryString = String.format("from %s where remoteId=:remoteId", type.getName());
        Query query = em().createQuery(queryString).setParameter("remoteId", remoteId);
        try {
            //noinspection unchecked
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

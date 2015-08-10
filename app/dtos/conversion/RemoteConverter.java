package dtos.conversion;

import dtos.generic.RemoteDto;
import models.generic.RemoteModel;

/**
 * Created by daniel on 10.08.15.
 */
public abstract class RemoteConverter<T extends RemoteModel, S extends RemoteDto>
    extends AbstractConverter<T, S> {

    protected RemoteConverter(Class<T> t, Class<S> s) {
        super(t, s);
    }

    @Override public void configure() {
        builder().from("remoteId").to("remoteId");
        builder().from("cloudProviderId").to("cloudProviderId");
    }
}

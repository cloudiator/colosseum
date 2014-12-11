package models.service.api;

import com.google.inject.ImplementedBy;
import models.Api;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.ApiService;

/**
 * Created by daniel seybold on 11.12.2014.
 */
@ImplementedBy(ApiService.class)
public interface ApiServiceInterface extends NamedModelServiceInterface<Api> {
}

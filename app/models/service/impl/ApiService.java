package models.service.impl;

import com.google.inject.Inject;
import models.Api;
import models.repository.api.ApiRepository;
import models.service.api.ApiServiceInterface;
import models.service.impl.generic.NamedModelService;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class ApiService extends NamedModelService<Api> implements ApiServiceInterface {

    @Inject
    public ApiService(ApiRepository apiRepository){super(apiRepository);}
}

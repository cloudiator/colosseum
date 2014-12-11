package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.UserCredentialDto;
import dtos.builders.UserCredentialDtoBuilder;
import dtos.convert.converters.api.ModelDtoConverter;
import models.CloudApi;
import models.Credential;
import models.FrontendUser;
import models.UserCredential;
import models.service.impl.CloudApiService;
import models.service.impl.CredentialService;
import models.service.impl.FrontendUserService;

import static com.google.common.base.Preconditions.checkState;
import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class UserCredentialConverter implements ModelDtoConverter<UserCredential, UserCredentialDto> {

    private final CloudApiService cloudApiService;
    private final CredentialService credentialService;
    private final FrontendUserService frontendUserService;

    @Inject
    UserCredentialConverter(CloudApiService cloudApiService, CredentialService credentialService, FrontendUserService frontendUserService){

        checkNotNull(cloudApiService);
        checkNotNull(credentialService);
        checkNotNull(frontendUserService);

        this.cloudApiService = cloudApiService;
        this.credentialService = credentialService;
        this.frontendUserService = frontendUserService;
    }


    /**
     * Sets the dto to the userCredential model.
     *
     * @param userCredential    the userCredential model where the dto should be set.
     * @param userCredentialDto the dto to be set.
     * @return the merged hardware object.
     */
    protected UserCredential setDto(UserCredential userCredential, UserCredentialDto userCredentialDto){

        CloudApi cloudApi = cloudApiService.getById(userCredentialDto.cloudApi);
        checkState(cloudApi != null, "Could not retrieve cloudApi for id: " + userCredentialDto.cloudApi);
        userCredential.setCloudApi(cloudApi);

        Credential credential = credentialService.getById(userCredentialDto.credential);
        checkState(credential != null, "Could not retrieve credential for id: " + userCredentialDto.credential);
        userCredential.setCredential(credential);

        FrontendUser frontendUser = frontendUserService.getById(userCredentialDto.frontendUser);
        checkState(frontendUser != null, "Could not retrieve frontendUser for id: " + userCredentialDto.frontendUser);
        userCredential.setFrontendUser(frontendUser);

        return userCredential;
    }

    @Override
    public UserCredential toModel(UserCredentialDto dto) {
        checkNotNull(dto);
        return this.setDto(new UserCredential(), dto);
    }

    @Override
    public UserCredential toModel(UserCredentialDto dto, UserCredential model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model,dto);
    }

    @Override
    public UserCredentialDto toDto(UserCredential model) {
        checkNotNull(model);
        return new UserCredentialDtoBuilder()
                .cloudApi(model.getCloudApi().getId())
                .credential(model.getCredential().getId())
                .frontendUser(model.getFrontendUser().getId())
                .build();
    }
}

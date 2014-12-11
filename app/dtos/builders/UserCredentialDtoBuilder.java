package dtos.builders;

import dtos.UserCredentialDto;

public class UserCredentialDtoBuilder {
    private Long cloudApi;
    private Long credential;
    private Long frontendUser;

    public UserCredentialDtoBuilder cloudApi(Long cloudApi) {
        this.cloudApi = cloudApi;
        return this;
    }

    public UserCredentialDtoBuilder credential(Long credential) {
        this.credential = credential;
        return this;
    }

    public UserCredentialDtoBuilder frontendUser(Long frontendUser) {
        this.frontendUser = frontendUser;
        return this;
    }

    public UserCredentialDto build() {
        return new UserCredentialDto(cloudApi, credential, frontendUser);
    }
}
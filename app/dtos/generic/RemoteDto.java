package dtos.generic;

import dtos.validation.validators.NotNullOrEmptyValidator;

/**
 * Created by daniel on 10.08.15.
 */
public abstract class RemoteDto extends ValidatableDto {

    private String remoteId;
    private String cloudProviderId;


    @Override public void validation() {
        //validator(String.class).validate(remoteId).withValidator(new NotNullOrEmptyValidator());
        //validator(String.class).validate(cloudProviderId)
        //    .withValidator(new NotNullOrEmptyValidator());
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getCloudProviderId() {
        return cloudProviderId;
    }

    public void setCloudProviderId(String cloudProviderId) {
        this.cloudProviderId = cloudProviderId;
    }
}

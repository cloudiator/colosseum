package dtos.builders;

import dtos.CloudLocationDto;

public class CloudLocationDtoBuilder {
    private Long cloud;
    private Long location;
    private String cloudUuid;

    public CloudLocationDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudLocationDtoBuilder location(Long location) {
        this.location = location;
        return this;
    }

    public CloudLocationDtoBuilder cloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
        return this;
    }

    public CloudLocationDto build() {
        return new CloudLocationDto(cloud, location, cloudUuid);
    }
}
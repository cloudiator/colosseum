package dtos.builders;

import dtos.CloudLocationDto;

public class CloudLocationDtoBuilder {
    private Long cloud;
    private Long location;
    private String uuid;

    public CloudLocationDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudLocationDtoBuilder location(Long location) {
        this.location = location;
        return this;
    }

    public CloudLocationDtoBuilder uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public CloudLocationDto build() {
        return new CloudLocationDto(cloud, location, uuid);
    }
}
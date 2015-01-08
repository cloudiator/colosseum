package dtos.builders;

import dtos.CloudImageDto;

public class CloudImageDtoBuilder {
    private Long cloud;
    private Long image;
    private String cloudUuid;

    public CloudImageDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudImageDtoBuilder image(Long image) {
        this.image = image;
        return this;
    }

    public CloudImageDtoBuilder cloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
        return this;
    }

    public CloudImageDto build() {
        return new CloudImageDto(cloud, image, cloudUuid);
    }
}
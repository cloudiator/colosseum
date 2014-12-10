package dtos.builders;

import dtos.CloudImageDto;

public class CloudImageDtoBuilder {
    private Long cloud;
    private Long image;
    private String uuid;

    public CloudImageDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudImageDtoBuilder image(Long image) {
        this.image = image;
        return this;
    }

    public CloudImageDtoBuilder uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public CloudImageDto build() {
        return new CloudImageDto(cloud, image, uuid);
    }
}
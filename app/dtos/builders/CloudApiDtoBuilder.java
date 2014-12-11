package dtos.builders;

import dtos.CloudApiDto;

public class CloudApiDtoBuilder {
    private Long api;
    private Long cloud;
    private String endpoint;

    public CloudApiDtoBuilder api(Long api) {
        this.api = api;
        return this;
    }

    public CloudApiDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudApiDtoBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public CloudApiDto build() {
        return new CloudApiDto(api, cloud, endpoint);
    }
}
package dtos.builders;

import dtos.CloudHardwareDto;

public class CloudHardwareDtoBuilder {
    private Long cloud;
    private Long hardware;
    private String cloudUuid;

    public CloudHardwareDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudHardwareDtoBuilder hardware(Long hardware) {
        this.hardware = hardware;
        return this;
    }

    public CloudHardwareDtoBuilder cloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
        return this;
    }

    public CloudHardwareDto build() {
        return new CloudHardwareDto(cloud, hardware, cloudUuid);
    }
}
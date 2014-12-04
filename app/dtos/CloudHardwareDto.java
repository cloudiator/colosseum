package dtos;

public class CloudHardwareDto {

    public Long cloud;
    public Long hardware;
    public String uuid;

    public CloudHardwareDto() {
        super();
    }

    public CloudHardwareDto(Long cloud, Long hardware, String uuid) {
        this.cloud = cloud;
        this.hardware = hardware;
        this.uuid = uuid;
    }
}

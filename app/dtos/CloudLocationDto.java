package dtos;

public class CloudLocationDto {

    public Long cloud;
    public Long location;
    public String uuid;

    public CloudLocationDto() {

    }

    public CloudLocationDto(Long cloud, Long location, String uuid) {
        this.cloud = cloud;
        this.location = location;
        this.uuid = uuid;
    }
}

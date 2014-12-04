package dtos;

public class CloudImageDto {


    public Long cloud;
    public Long image;
    public String uuid;

    public CloudImageDto() {

    }

    public CloudImageDto(Long cloud, Long image, String uuid) {
        this.cloud = cloud;
        this.image = image;
        this.uuid = uuid;
    }
}

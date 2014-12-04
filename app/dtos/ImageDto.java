package dtos;

import dtos.generic.NamedDto;

public class ImageDto extends NamedDto {

    public Long operatingSystem;

    public ImageDto() {

    }

    public ImageDto(String name, Long operatingSystem) {
        super(name);
        this.operatingSystem = operatingSystem;
    }
}

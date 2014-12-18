package dtos.builders;

import dtos.LifecycleComponentDto;

public class LifecycleComponentDtoBuilder {
    private String name;
    private String download;
    private String install;
    private String start;
    private String stop;

    public LifecycleComponentDtoBuilder name(String name) {
        this.name = name;
        return this;
    }

    public LifecycleComponentDtoBuilder download(String download) {
        this.download = download;
        return this;
    }

    public LifecycleComponentDtoBuilder install(String install) {
        this.install = install;
        return this;
    }

    public LifecycleComponentDtoBuilder start(String start) {
        this.start = start;
        return this;
    }

    public LifecycleComponentDtoBuilder stop(String stop) {
        this.stop = stop;
        return this;
    }

    public LifecycleComponentDto build() {
        return new LifecycleComponentDto(name, download, install, start, stop);
    }
}
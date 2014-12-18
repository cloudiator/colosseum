package dtos.convert.converters.impl;

import dtos.LifecycleComponentDto;
import dtos.builders.LifecycleComponentDtoBuilder;
import models.LifecycleComponent;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel seybold on 16.12.2014.
 */
public class LifecycleComponentConverter extends BaseConverter<LifecycleComponent, LifecycleComponentDto> {

    protected LifecycleComponent setDto(LifecycleComponent lifecycleComponent, LifecycleComponentDto lifecycleComponentDto) {

        lifecycleComponent.setName(lifecycleComponentDto.name);
        lifecycleComponent.setDownload(lifecycleComponentDto.download);
        lifecycleComponent.setInstall(lifecycleComponentDto.install);
        lifecycleComponent.setStart(lifecycleComponentDto.start);
        lifecycleComponent.setStop(lifecycleComponentDto.stop);

        return lifecycleComponent;
    }


    @Override
    public LifecycleComponent toModel(LifecycleComponentDto dto) {
        checkNotNull(dto);
        return this.setDto(new LifecycleComponent(), dto);
    }

    @Override
    public LifecycleComponent toModel(LifecycleComponentDto dto, LifecycleComponent model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public LifecycleComponentDto toDto(LifecycleComponent model) {
        return new LifecycleComponentDtoBuilder()
                .name(model.getName())
                .download(model.getDownload())
                .install(model.getInstall())
                .start(model.getStart())
                .stop(model.getStop())
                .build();
    }
}

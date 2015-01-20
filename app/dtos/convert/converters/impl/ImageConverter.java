/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.ImageDto;
import models.Image;
import models.OperatingSystem;
import models.service.impl.OperatingSystemService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class ImageConverter extends BaseConverter<Image, ImageDto> {

    private final OperatingSystemService operatingSystemService;

    @Inject
    ImageConverter(OperatingSystemService operatingSystemService) {

        checkNotNull(operatingSystemService);

        this.operatingSystemService = operatingSystemService;

    }

    protected Image setDto(Image image, ImageDto imageDto) {
        image.setName(imageDto.name);

        OperatingSystem operatingSystem = operatingSystemService.getById(imageDto.operatingSystem);
        checkState(operatingSystem != null, "Could not retrieve operating system for id: " + imageDto.operatingSystem);
        image.setOperatingSystem(operatingSystem);

        return image;
    }

    @Override
    public Image toModel(ImageDto dto) {
        checkNotNull(dto);
        return this.setDto(new Image(), dto);
    }

    @Override
    public Image toModel(ImageDto dto, Image model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public ImageDto toDto(Image model) {
        checkNotNull(model);
        return new ImageDto(model.getName(), model.getOperatingSystem().getId());
    }
}

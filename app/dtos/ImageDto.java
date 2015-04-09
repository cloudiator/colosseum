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

package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import models.Cloud;
import models.ImageProperties;
import models.service.impl.generic.BaseModelService;

public class ImageDto extends ValidatableDto {


    protected Long cloud;
    protected Long image;
    protected String cloudUuid;

    public ImageDto() {
        super();
    }

    @Override public void validation() {

    }

    public ImageDto(Long cloud, Long image, String cloudUuid) {
        this.cloud = cloud;
        this.image = image;
        this.cloudUuid = cloudUuid;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }

//    @Override public List<ValidationError> validateNotNull() {
//        List<ValidationError> errors = new ArrayList<>();
//
//        //validate cloud reference
//        Cloud cloud = null;
//        if (this.cloud == null) {
//            errors.add(new ValidationError("cloud", "The cloud is required."));
//        } else {
//            cloud = References.cloudService.get().getById(this.cloud);
//            if (cloud == null) {
//                errors.add(new ValidationError("cloud", "The given cloud is invalid."));
//            }
//        }
//
//        //validate cloud reference
//        ImageProperties imageProperties = null;
//        if (this.image == null) {
//            errors.add(new ValidationError("image", "The image is required."));
//        } else {
//            imageProperties = References.imagePropertiesService.get().getById(this.image);
//            if (imageProperties == null) {
//                errors.add(new ValidationError("image", "The given image is invalid."));
//            }
//        }
//
//        return errors;
//    }


    public static class References {

        @Inject public static Provider<BaseModelService<Cloud>> cloudService;

        @Inject public static Provider<BaseModelService<ImageProperties>> imagePropertiesService;
    }
}

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

package models;

import models.generic.NamedModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The model class image.
 * <p>
 * Stores information about the cloud images available in a cloud.
 *
 * @author Daniel Baur
 */
@Entity
public class Image extends NamedModel {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;


    @ManyToOne(optional = false)
    private OperatingSystem operatingSystem;

    /**
     * The cloud images where this image is used (ManyToMany)
     */
    @OneToMany(mappedBy = "image")
    private List<CloudImage> cloudImages;

    /**
     * Empty constructor for hibernate.
     */
    private Image() {
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        checkNotNull(operatingSystem);
        this.operatingSystem = operatingSystem;
    }

    public List<CloudImage> getCloudImages() {
        return cloudImages;
    }

    public void setCloudImages(List<CloudImage> cloudImages) {
        checkNotNull(cloudImages);
        this.cloudImages = cloudImages;
    }
}

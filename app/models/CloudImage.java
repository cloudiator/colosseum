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

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents the relation between a cloud and a generic image, by specifing the
 * cloud-dependant cloudUuid.
 */
@Entity
public class CloudImage extends Model {

    /**
     * Empty constructor for hibernate.
     */
    private CloudImage() {
    }

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The cloud where this image is available.
     */
    @ManyToOne(optional = false)
    private Cloud cloud;

    /**
     * The image.
     */
    @ManyToOne(optional = false)
    private Image image;

    @OneToMany(mappedBy = "cloudImage")
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    /**
     * The cloud-dependant unique identifier.
     */
    private String cloudUuid;

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        checkNotNull(image);
        this.image = image;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        checkNotNull(cloudUuid);
        checkArgument(!cloudUuid.isEmpty(), "Empty String for cloudUuid provided");
        this.cloudUuid = cloudUuid;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public void setVirtualMachineTemplates(List<VirtualMachineTemplate> virtualMachineTemplates) {
        this.virtualMachineTemplates = virtualMachineTemplates;
    }
}

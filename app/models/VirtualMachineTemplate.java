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

import com.google.common.collect.ImmutableList;
import models.generic.Model;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 11.02.15.
 */
@Entity public class VirtualMachineTemplate extends Model {

    /**
     * Owned relations
     */
    @ManyToOne(optional = false) private Cloud cloud;
    @ManyToOne(optional = false) private Image image;
    @ManyToOne(optional = false) private Location location;
    @ManyToOne(optional = false) private Hardware hardware;
    @Nullable @ManyToOne(optional = true) private TemplateOptions templateOptions;

    /**
     * Foreign relations
     */
    @OneToMany(mappedBy = "virtualMachineTemplate") private List<ApplicationComponent>
        applicationComponents;

    /**
     * Empty constructor for hibernate.
     */
    protected VirtualMachineTemplate() {
    }

    public VirtualMachineTemplate(Cloud cloud, Image image, Location location, Hardware hardware,
        @Nullable TemplateOptions templateOptions) {
        checkNotNull(cloud);
        this.cloud = cloud;
        checkNotNull(image);
        this.image = image;
        checkNotNull(location);
        this.location = location;
        checkNotNull(hardware);
        this.hardware = hardware;
        this.templateOptions = templateOptions;
    }

    public List<ApplicationComponent> getApplicationComponentsUsedFor() {
        return ImmutableList.copyOf(applicationComponents);
    }

    public Image image() {
        return image;
    }

    public Location location() {
        return location;
    }

    public Hardware hardware() {
        return hardware;
    }

    public Cloud cloud() {
        return cloud;
    }

    public Optional<TemplateOptions> templateOptions() {
        return Optional.ofNullable(templateOptions);
    }
}

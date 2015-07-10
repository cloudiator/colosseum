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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import models.generic.RemoteModel;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity public class VirtualMachine extends RemoteModel {

    @ManyToOne(optional = true) private Cloud cloud;
    @Column(unique = true, nullable = false) private String name;
    @ManyToMany private List<CloudCredential> cloudCredentials;

    @Nullable @Column(nullable = true) private String generatedLoginUsername;
    @Nullable @Column(nullable = true) private String generatedLoginPassword;

    @Nullable @ManyToOne(optional = true) private Image image;
    @Nullable @ManyToOne(optional = true) private Hardware hardware;
    @Nullable @ManyToOne(optional = true) private Location location;


    /**
     * Use cascade type merge due to bug in all
     * https://hibernate.atlassian.net/browse/HHH-7404
     */
    @OneToMany(mappedBy = "virtualMachine", cascade = CascadeType.MERGE) private List<IpAddress>
        ipAddresses;

    /**
     * Empty constructor for hibernate.
     */
    protected VirtualMachine() {
    }

    public VirtualMachine(String name, Cloud cloud, @Nullable String cloudUuid,
        @Nullable Hardware hardware, @Nullable Image image, @Nullable Location location,
        @Nullable String generatedLoginUsername, @Nullable String generatedLoginPassword) {
        super(cloudUuid);
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
        checkNotNull(cloud);
        this.cloud = cloud;
        this.hardware = hardware;
        this.image = image;
        this.location = location;
        this.generatedLoginUsername = generatedLoginUsername;
        this.generatedLoginPassword = generatedLoginPassword;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    @Nullable public Image getImage() {
        return image;
    }

    public void setImage(@Nullable Image image) {
        this.image = image;
    }

    @Nullable public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(@Nullable Hardware hardware) {
        this.hardware = hardware;
    }

    @Nullable public Location getLocation() {
        return location;
    }

    public void setLocation(@Nullable Location location) {
        this.location = location;
    }

    public List<IpAddress> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(List<IpAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    @Nullable public IpAddress getPublicIpAddress() {
        final Iterable<IpAddress> ipAddresses =
            Iterables.filter(this.getIpAddresses(), ipAddress -> {
                return ipAddress.getIpType().equals(IpType.PUBLIC);
            });
        if (ipAddresses.iterator().hasNext()) {
            return ipAddresses.iterator().next();
        }
        return null;
    }

    public List<CloudCredential> getCloudCredentials() {
        return cloudCredentials;
    }

    public void setCloudCredentials(List<CloudCredential> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable public String getGeneratedLoginUsername() {
        return generatedLoginUsername;
    }

    public void setGeneratedLoginUsername(@Nullable String generatedLoginUsername) {
        this.generatedLoginUsername = generatedLoginUsername;
    }

    @Nullable public String getGeneratedLoginPassword() {
        return generatedLoginPassword;
    }

    public void setGeneratedLoginPassword(@Nullable String generatedLoginPassword) {
        this.generatedLoginPassword = generatedLoginPassword;
    }



}

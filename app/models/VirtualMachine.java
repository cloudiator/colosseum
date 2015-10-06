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

import com.google.common.collect.Iterables;
import models.generic.RemoteResourceInLocation;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.*;

/**
 * Created by daniel on 31.10.14.
 */
@Entity public class VirtualMachine extends RemoteResourceInLocation {

    @Column(unique = true, nullable = false) private String name;

    @Nullable @Column(nullable = true) private String generatedLoginUsername;
    @Nullable @Column(nullable = true) private String generatedLoginPassword;

    @Nullable @ManyToOne(optional = true) private Image image;
    @Nullable @ManyToOne(optional = true) private Hardware hardware;

    @Nullable @ManyToOne(optional = true) private TemplateOptions templateOptions;

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

    public VirtualMachine(@Nullable String remoteId, @Nullable String cloudProviderId, Cloud cloud,
        Location location, String name, @Nullable String generatedLoginUsername,
        @Nullable String generatedLoginPassword, @Nullable Image image, @Nullable Hardware hardware,
        @Nullable TemplateOptions templateOptions) {
        super(remoteId, cloudProviderId, cloud, location);
        this.name = name;
        this.generatedLoginUsername = generatedLoginUsername;
        this.generatedLoginPassword = generatedLoginPassword;
        this.image = image;
        this.hardware = hardware;
        this.templateOptions = templateOptions;
    }

    public Optional<Image> image() {
        return Optional.ofNullable(image);
    }

    public Optional<Hardware> hardware() {
        return Optional.ofNullable(hardware);
    }

    public void addIpAddress(IpAddress ipAddress) {
        if (ipAddresses == null) {
            this.ipAddresses = new ArrayList<>();
        }
        this.ipAddresses.add(ipAddress);
    }

    public Optional<IpAddress> publicIpAddress() {
        final Iterable<IpAddress> ipAddresses = Iterables.filter(this.ipAddresses, ipAddress -> {
            return ipAddress.getIpType().equals(IpType.PUBLIC);
        });
        if (ipAddresses.iterator().hasNext()) {
            return Optional.of(ipAddresses.iterator().next());
        }
        return Optional.empty();
    }

    public Optional<IpAddress> privateIpAddress(boolean fallbackToPublic) {
        final Iterable<IpAddress> ipAddresses = Iterables.filter(this.ipAddresses, ipAddress -> {
            return ipAddress.getIpType().equals(IpType.PRIVATE);
        });
        if (ipAddresses.iterator().hasNext()) {
            Optional.of(ipAddresses.iterator().next());
        }
        if (fallbackToPublic) {
            return publicIpAddress();
        }
        return Optional.empty();
    }

    public String name() {
        return name;
    }

    public Collection<String> loginNameCandidates() {
        Collection<String> loginNameCandidates;
        if (image != null) {
            loginNameCandidates = image.getLoginNameCandidates();
        } else {
            loginNameCandidates = new Stack<>();
        }
        if (generatedLoginUsername != null) {
            loginNameCandidates.add(generatedLoginPassword);
        }
        return loginNameCandidates;
    }

    public Collection<String> loginPasswordCandidates() {
        Collection<String> loginPasswordCandidates;
        if (image != null) {
            loginPasswordCandidates = image.getLoginPasswordCandidates();
        } else {
            loginPasswordCandidates = new Stack<>();
        }
        if (generatedLoginPassword != null) {
            loginPasswordCandidates.add(generatedLoginPassword);
        }
        return loginPasswordCandidates;
    }

    public Optional<String> loginName() {
        return loginNameCandidates().stream().findFirst();
    }

    public Optional<String> loginPassword() {
        return loginPasswordCandidates().stream().findFirst();
    }

    public OperatingSystemVendorType operatingSystemVendorTypeOrDefault() {
        if (image != null && image.operatingSystem().isPresent()) {
            return image.operatingSystemVendorTypeOrDefault();
        }
        return OperatingSystemVendorType.DEFAULT_VENDOR_TYPE;
    }

    public boolean supportsKeyPair() {
        return image != null && image.operatingSystem().isPresent() && image.operatingSystem().get()
            .operatingSystemVendor().operatingSystemVendorType().supportsKeyPair();
    }

    public Optional<Integer> remotePort() {
        if (image != null && image.operatingSystem().isPresent()) {
            return Optional.of(image.operatingSystem().get().operatingSystemVendor()
                .operatingSystemVendorType().port());
        }
        return Optional.empty();
    }

    public Integer remotePortOrDefault() {
        if (remotePort().isPresent()) {
            return remotePort().get();
        }
        return OperatingSystemVendorType.DEFAULT_VENDOR_TYPE.port();
    }

    public Optional<TemplateOptions> templateOptions() {
        return Optional.ofNullable(templateOptions);
    }
}

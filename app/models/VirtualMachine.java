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
    @Nullable @Lob @Column(nullable = true) private String generatedPrivateKey;

    @Nullable @ManyToOne(optional = true) private Image image;
    @Nullable @ManyToOne(optional = true) private Hardware hardware;

    @Nullable @ManyToOne(optional = true) private TemplateOptions templateOptions;

    /**
     * Use set to avoid duplicate entries due to hibernate bug
     * https://hibernate.atlassian.net/browse/HHH-7404
     */
    @OneToMany(mappedBy = "virtualMachine", cascade = CascadeType.ALL) private Set<IpAddress>
        ipAddresses;

    /**
     * Empty constructor for hibernate.
     */
    protected VirtualMachine() {
    }

    public VirtualMachine(@Nullable String remoteId, @Nullable String cloudProviderId, Cloud cloud,
        @Nullable CloudCredential owner, Location location, String name,
        @Nullable String generatedLoginUsername, @Nullable String generatedLoginPassword,
        @Nullable String generatedPrivateKey, @Nullable Image image, @Nullable Hardware hardware,
        @Nullable TemplateOptions templateOptions) {
        super(remoteId, cloudProviderId, cloud, owner, location);
        this.name = name;
        this.generatedLoginUsername = generatedLoginUsername;
        this.generatedLoginPassword = generatedLoginPassword;
        this.generatedPrivateKey = generatedPrivateKey;
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
            this.ipAddresses = new HashSet<>();
        }
        this.ipAddresses.add(ipAddress);
    }

    public Optional<IpAddress> publicIpAddress() {
        return ipAddresses.stream().filter(ipAddress -> ipAddress.getIpType().equals(IpType.PUBLIC))
            .findAny();
    }

    public Optional<IpAddress> privateIpAddress(boolean fallbackToPublic) {

        final Optional<IpAddress> any =
            ipAddresses.stream().filter(ipAddress -> ipAddress.getIpType().equals(IpType.PRIVATE))
                .findAny();

        if (!any.isPresent() && fallbackToPublic) {
            return publicIpAddress();
        }
        return any;
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

    public Optional<String> loginPrivateKey() {
        return Optional.ofNullable(generatedPrivateKey);
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

    public void setGeneratedLoginUsername(@Nullable String generatedLoginUsername) {
        if (this.generatedLoginUsername != null) {
            throw new IllegalStateException("Changing generatedLoginUsername not permitted.");
        }
        this.generatedLoginUsername = generatedLoginUsername;
    }

    public void setGeneratedLoginPassword(@Nullable String generatedLoginPassword) {
        if (this.generatedLoginPassword != null) {
            throw new IllegalStateException("Changing generatedLoginPassword not permitted.");
        }
        this.generatedLoginPassword = generatedLoginPassword;
    }

    public void setGeneratedPrivateKey(@Nullable String generatedPrivateKey) {
        if (this.generatedPrivateKey != null) {
            throw new IllegalStateException("Changing generatedPrivateKey not permitted.");
        }
        this.generatedPrivateKey = generatedPrivateKey;
    }
}

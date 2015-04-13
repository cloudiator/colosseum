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

import dtos.generic.NamedDto;
import dtos.validation.NotNullOrEmptyValidator;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class LifecycleComponentDto extends NamedDto {

    private String init;
    private String preInstall;
    private String install;
    private String postInstall;
    private String preStart;
    private String start;
    private String startDetection;
    private String stopDetection;
    private String postStart;
    private String preStop;
    private String stop;
    private String postStop;
    private String shutdown;

    public LifecycleComponentDto() {
        super();
    }

    public LifecycleComponentDto(String init, String preInstall, String install, String postInstall,
        String preStart, String start, String startDetection, String stopDetection,
        String postStart, String preStop, String stop, String postStop, String shutdown) {
        this.init = init;
        this.preInstall = preInstall;
        this.install = install;
        this.postInstall = postInstall;
        this.preStart = preStart;
        this.start = start;
        this.startDetection = startDetection;
        this.stopDetection = stopDetection;
        this.postStart = postStart;
        this.preStop = preStop;
        this.stop = stop;
        this.postStop = postStop;
        this.shutdown = shutdown;
    }

    @Override public void validation() {
        super.validation();
        validator(String.class).validate(start).withValidator(new NotNullOrEmptyValidator());
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getPreInstall() {
        return preInstall;
    }

    public void setPreInstall(String preInstall) {
        this.preInstall = preInstall;
    }

    public String getInstall() {
        return install;
    }

    public void setInstall(String install) {
        this.install = install;
    }

    public String getPostInstall() {
        return postInstall;
    }

    public void setPostInstall(String postInstall) {
        this.postInstall = postInstall;
    }

    public String getPreStart() {
        return preStart;
    }

    public void setPreStart(String preStart) {
        this.preStart = preStart;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStartDetection() {
        return startDetection;
    }

    public void setStartDetection(String startDetection) {
        this.startDetection = startDetection;
    }

    public String getStopDetection() {
        return stopDetection;
    }

    public void setStopDetection(String stopDetection) {
        this.stopDetection = stopDetection;
    }

    public String getPostStart() {
        return postStart;
    }

    public void setPostStart(String postStart) {
        this.postStart = postStart;
    }

    public String getPreStop() {
        return preStop;
    }

    public void setPreStop(String preStop) {
        this.preStop = preStop;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getPostStop() {
        return postStop;
    }

    public void setPostStop(String postStop) {
        this.postStop = postStop;
    }

    public String getShutdown() {
        return shutdown;
    }

    public void setShutdown(String shutdown) {
        this.shutdown = shutdown;
    }
}

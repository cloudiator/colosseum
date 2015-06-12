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

import javax.annotation.Nullable;
import javax.persistence.Entity;

/**
 * Created by daniel on 15.12.14.
 */
@Entity public class LifecycleComponent extends Component {

    @Nullable private String init;
    @Nullable private String preInstall;
    @Nullable private String install;
    @Nullable private String postInstall;
    @Nullable private String preStart;
    private String start;
    @Nullable private String startDetection;
    @Nullable private String stopDetection;
    @Nullable private String postStart;
    @Nullable private String preStop;
    @Nullable private String stop;
    @Nullable private String postStop;
    @Nullable private String shutdown;

    /**
     * Empty constructor for hibernate.
     */
    protected LifecycleComponent() {
    }

    public LifecycleComponent(@Nullable String init, @Nullable String preInstall,
        @Nullable String install, @Nullable String postInstall, @Nullable String preStart,
        String start, @Nullable String startDetection, @Nullable String stopDetection,
        @Nullable String postStart, @Nullable String preStop, @Nullable String stop,
        @Nullable String postStop, @Nullable String shutdown) {
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

    @Nullable public String getInit() {
        return init;
    }

    public void setInit(@Nullable String init) {
        this.init = init;
    }

    @Nullable public String getPreInstall() {
        return preInstall;
    }

    public void setPreInstall(@Nullable String preInstall) {
        this.preInstall = preInstall;
    }

    @Nullable public String getInstall() {
        return install;
    }

    public void setInstall(@Nullable String install) {
        this.install = install;
    }

    @Nullable public String getPostInstall() {
        return postInstall;
    }

    public void setPostInstall(@Nullable String postInstall) {
        this.postInstall = postInstall;
    }

    @Nullable public String getPreStart() {
        return preStart;
    }

    public void setPreStart(@Nullable String preStart) {
        this.preStart = preStart;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @Nullable public String getStartDetection() {
        return startDetection;
    }

    public void setStartDetection(@Nullable String startDetection) {
        this.startDetection = startDetection;
    }

    @Nullable public String getStopDetection() {
        return stopDetection;
    }

    public void setStopDetection(@Nullable String stopDetection) {
        this.stopDetection = stopDetection;
    }

    @Nullable public String getPostStart() {
        return postStart;
    }

    public void setPostStart(@Nullable String postStart) {
        this.postStart = postStart;
    }

    @Nullable public String getPreStop() {
        return preStop;
    }

    public void setPreStop(@Nullable String preStop) {
        this.preStop = preStop;
    }

    @Nullable public String getStop() {
        return stop;
    }

    public void setStop(@Nullable String stop) {
        this.stop = stop;
    }

    @Nullable public String getPostStop() {
        return postStop;
    }

    public void setPostStop(@Nullable String postStop) {
        this.postStop = postStop;
    }

    @Nullable public String getShutdown() {
        return shutdown;
    }

    public void setShutdown(@Nullable String shutdown) {
        this.shutdown = shutdown;
    }
}

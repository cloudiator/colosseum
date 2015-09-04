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
import javax.persistence.Lob;

/**
 * Created by daniel on 15.12.14.
 */
@Entity public class LifecycleComponent extends Component {

    @Nullable @Lob private String init;
    @Nullable @Lob private String preInstall;
    @Nullable @Lob private String install;
    @Nullable @Lob private String postInstall;
    @Nullable @Lob private String preStart;
    @Lob private String start;
    @Nullable @Lob private String startDetection;
    @Nullable @Lob private String stopDetection;
    @Nullable @Lob private String postStart;
    @Nullable @Lob private String preStop;
    @Nullable @Lob private String stop;
    @Nullable @Lob private String postStop;
    @Nullable @Lob private String shutdown;

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

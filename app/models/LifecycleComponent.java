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
@Entity
public class LifecycleComponent extends Component {

    @Nullable
    private String download;
    @Nullable
    private String install;
    @Nullable
    private String start;
    @Nullable
    private String stop;

    @Nullable
    public String getDownload() {
        return download;
    }

    public void setDownload(@Nullable String download) {
        this.download = download;
    }

    @Nullable
    public String getInstall() {
        return install;
    }

    public void setInstall(@Nullable String install) {
        this.install = install;
    }

    @Nullable
    public String getStart() {
        return start;
    }

    public void setStart(@Nullable String start) {
        this.start = start;
    }

    @Nullable
    public String getStop() {
        return stop;
    }

    public void setStop(@Nullable String stop) {
        this.stop = stop;
    }
}

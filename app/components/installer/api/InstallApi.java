/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.installer.api;

/**
 * Created by Daniel Seybold on 19.05.2015.
 */
public interface InstallApi {

    /**
     * Place all download commands in the download queue
     */
    public void initSources();

    /**
     * download all sources in parallel
     */
    public void downloadSources();

    /**
     * extract Java archive, set Java environement
     */
    public void installJava();

    /**
     * create the visor configuration file and start visor
     */
    public void installVisor();

    /**
     * extract and start kairosDB
     */
    public void installKairosDb();

    /**
     * download, setup and start the LifecycleAgent (Unix:Docker, Windows: not yet decided)
     */
    public void installLifecycleAgent();

    /**
     * Download and install all necessary software for cloudiator (java, visor, lifecycle agent)
     */
    public void installAll();


    /**
     * Closes all open remote connections
     */
    public void finishInstallation();

}


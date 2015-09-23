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

import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;

/**
 * Created by Daniel Seybold on 19.05.2015.
 */
public interface InstallApi extends AutoCloseable {

    /**
     * Place all download commands in the download queue
     */
    void initSources();

    /**
     * download all sources in parallel
     */
    void downloadSources();

    /**
     * extract Java archive, set Java environement
     */
    void installJava() throws RemoteException;

    /**
     * create the visor configuration file and start visor
     */
    void installVisor() throws RemoteException;

    /**
     * extract and start kairosDB
     */
    void installKairosDb() throws RemoteException;

    /**
     * download, setup and start Lance (LifecycleAgent - Unix:Docker, Windows: not yet decided)
     */
    void installLance() throws RemoteException;

    /**
     * Download and install all necessary software for cloudiator (java, visor, lifecycle agent)
     */
    void installAll() throws RemoteException;

    @Override void close() throws RemoteException;
}


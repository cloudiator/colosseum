/*
 * Copyright (c) 2014-2016 University of Ulm
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

package cloud.actors;

/**
 * Created by daniel on 12.08.16.
 */
public class VirtualMachineProtocol {

    public static class VirtualMachineRequest {

        public String name;
        public Long owner;
        public Long cloud;
        public Long image;
        public Long hardware;
        public Long location;
        public Long templateOptions;

        public VirtualMachineRequest(String name, Long owner, Long cloud, Long image, Long hardware,
            Long location, Long templateOptions) {
            this.name = name;
            this.owner = owner;
            this.cloud = cloud;
            this.image = image;
            this.hardware = hardware;
            this.location = location;
            this.templateOptions = templateOptions;
        }
    }

}

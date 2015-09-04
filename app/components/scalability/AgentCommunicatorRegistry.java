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

package components.scalability;

import io.netty.util.internal.ConcurrentSet;

/**
 * Created by Frank on 01.09.2015.
 */
public class AgentCommunicatorRegistry {
    private static ConcurrentSet<AgentCommunicator> agentCommunicators = new ConcurrentSet<>();

    private AgentCommunicatorRegistry(){
        // no instantiation
    }

    public synchronized static AgentCommunicator getAgentCommunicator(String protocol, String ip, int port){
        for(AgentCommunicator agent : agentCommunicators){
            if (agent.getProtocol().equals(protocol) && agent.getIp().equals(ip) && agent.getPort() == port){
                return agent;
            }
        }
        AgentCommunicator agent = new AgentCommunicatorImpl(protocol, ip, port);
        agentCommunicators.add(agent);
        return agent;
    }
}

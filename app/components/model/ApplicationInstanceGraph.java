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

package components.model;

import models.ApplicationInstance;
import models.Instance;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

/**
 * Created by daniel on 15.07.16.
 */
public class ApplicationInstanceGraph {

    private final DirectedPseudograph<Instance, CommunicationInstanceEdge> componentGraph;


    public ApplicationInstanceGraph(ApplicationInstance applicationInstance) {
        componentGraph = GraphFactory.of(applicationInstance);
    }

    private static class CommunicationInstanceEdge extends DefaultEdge {

    }


    private static class CommunicationInstanceEdgeFactory
        implements EdgeFactory<Instance, CommunicationInstanceEdge> {
        @Override
        public CommunicationInstanceEdge createEdge(Instance sourceVertex, Instance targetVertex) {
            return new CommunicationInstanceEdge();
        }
    }


    private static class GraphFactory {

        public static DirectedPseudograph<Instance, CommunicationInstanceEdge> of(
            ApplicationInstance applicationInstance) {

            DirectedPseudograph<Instance, CommunicationInstanceEdge> instanceGraph =
                new DirectedPseudograph<>(new CommunicationInstanceEdgeFactory());

            applicationInstance.getInstances().forEach(instanceGraph::addVertex);

            return instanceGraph;
        }
    }

}

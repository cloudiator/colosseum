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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ApplicationInstance;
import models.Instance;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import play.libs.Json;

import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by daniel on 15.07.16.
 */
public class ApplicationInstanceGraph {

    private final DirectedPseudograph<Instance, CommunicationInstanceEdge> instanceGraph;

    public static ApplicationInstanceGraph of(ApplicationInstance applicationInstance) {
        return new ApplicationInstanceGraph(applicationInstance);
    }

    private ApplicationInstanceGraph(ApplicationInstance applicationInstance) {
        instanceGraph = GraphFactory.of(applicationInstance);
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

    public JsonNode toJson() {
        final ObjectNode objectNode = Json.newObject().with("elements");
        final ArrayNode nodes = objectNode.putArray("nodes");
        this.instanceGraph.vertexSet().forEach(instance -> {
            final ObjectNode vertex = nodes.addObject();
            vertex.with("data").put("id", instance.getUuid())
                .put("name", instance.getApplicationComponent().getComponent().getName())
                .put("state", instance.getRemoteState().toString())
                .put("parent", instance.getVirtualMachine().getUuid());
        });
        //add virtual machines as compound nodes
        this.instanceGraph.vertexSet().stream().map(Instance::getVirtualMachine).distinct()
            .forEach(virtualMachine -> {
                final ObjectNode compound = nodes.addObject();
                compound.with("data").put("id", virtualMachine.getUuid())
                    .put("name", virtualMachine.name())
                    .put("state", virtualMachine.getRemoteState().toString());
            });
        final ArrayNode edges = objectNode.putArray("edges");
        this.instanceGraph.edgeSet().forEach(communicationEdge -> {
            final ObjectNode edge = edges.addObject();
            edge.with("data").put("id", new Random().nextInt())
                .put("source", instanceGraph.getEdgeSource(communicationEdge).getUuid())
                .put("target", instanceGraph.getEdgeTarget(communicationEdge).getUuid());
        });

        return objectNode;
    }


    private static class GraphFactory {

        public static DirectedPseudograph<Instance, CommunicationInstanceEdge> of(
            ApplicationInstance applicationInstance) {

            DirectedPseudograph<Instance, CommunicationInstanceEdge> instanceGraph =
                new DirectedPseudograph<>(new CommunicationInstanceEdgeFactory());

            applicationInstance.getInstances().forEach(instanceGraph::addVertex);
            applicationInstance.getInstances().forEach(new Consumer<Instance>() {
                @Override public void accept(Instance instance) {
                    for (Instance targetInstance : instance.getTargetCommunicationInstances()) {
                        instanceGraph.addEdge(instance, targetInstance);
                    }
                }
            });

            return instanceGraph;
        }
    }

}

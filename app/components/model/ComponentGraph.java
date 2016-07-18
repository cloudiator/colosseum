package components.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Application;
import models.ApplicationComponent;
import models.Communication;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import play.libs.Json;

import java.util.Random;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 17.06.16.
 */
public class ComponentGraph {

    private final DirectedPseudograph<ApplicationComponent, CommunicationEdge> componentGraph;
    private final DirectedPseudograph<ApplicationComponent, CommunicationEdge>
        mandatoryComponentGraph;

    private ComponentGraph(Application application) {
        componentGraph = GraphFactory.of(application);
        mandatoryComponentGraph = GraphFactory.of(application, true);
    }

    public static ComponentGraph of(Application application) {
        checkNotNull(application);
        return new ComponentGraph(application);
    }

    private CycleDetector<ApplicationComponent, CommunicationEdge> cycleDetector() {
        return new CycleDetector<>(mandatoryComponentGraph);
    }

    public boolean hasCycle() {
        return cycleDetector().detectCycles();
    }

    public Set<ApplicationComponent> cycles() {
        return cycleDetector().findCycles();
    }

    public JsonNode toJson() {
        final ObjectNode objectNode = Json.newObject().with("elements");
        final ArrayNode nodes = objectNode.putArray("nodes");
        this.componentGraph.vertexSet().forEach(applicationComponent -> {
            final ObjectNode vertex = nodes.addObject();
            vertex.with("data").put("id", applicationComponent.getUuid())
                .put("name", applicationComponent.getComponent().getName());
        });
        final ArrayNode edges = objectNode.putArray("edges");
        this.componentGraph.edgeSet().forEach(communicationEdge -> {
            final ObjectNode edge = edges.addObject();
            edge.with("data").put("id", new Random().nextInt())
                .put("source", componentGraph.getEdgeSource(communicationEdge).getUuid())
                .put("target", componentGraph.getEdgeTarget(communicationEdge).getUuid());
            if (communicationEdge.isMandatory()) {
                edge.put("classes", "mandatory");
            }
        });

        return objectNode;
    }

    private static class CommunicationEdge extends DefaultEdge {

        private final Communication communication;

        private CommunicationEdge(Communication communication) {
            this.communication = communication;
        }

        public Communication communication() {
            return communication;
        }

        public boolean isMandatory() {
            return communication().isMandatory();
        }
    }



    private static class GraphFactory {

        public static DirectedPseudograph<ApplicationComponent, CommunicationEdge> of(
            Application application, boolean onlyMandatory) {

            DirectedPseudograph<ApplicationComponent, CommunicationEdge> componentGraph =
                new DirectedPseudograph<>(CommunicationEdge.class);
            application.getApplicationComponents().forEach(componentGraph::addVertex);
            application.communications().stream()
                .filter(communication -> !onlyMandatory || communication.isMandatory()).forEach(
                communication -> componentGraph
                    .addEdge(communication.getSource(), communication.getTarget(),
                        new CommunicationEdge(communication)));
            return componentGraph;
        }

        public static DirectedPseudograph<ApplicationComponent, CommunicationEdge> of(
            Application application) {
            return GraphFactory.of(application, false);
        }
    }

}

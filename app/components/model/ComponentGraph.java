package components.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Application;
import models.ApplicationComponent;
import models.Communication;
import org.jgrapht.EdgeFactory;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import play.libs.Json;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        this.componentGraph.vertexSet().forEach(new Consumer<ApplicationComponent>() {
            @Override public void accept(ApplicationComponent applicationComponent) {
                final ObjectNode vertex = nodes.addObject();
                vertex.with("data").put("id", applicationComponent.getUuid())
                    .put("name", applicationComponent.getComponent().getName());
            }
        });
        final ArrayNode edges = objectNode.putArray("edges");
        this.componentGraph.edgeSet().forEach(new Consumer<CommunicationEdge>() {
            @Override public void accept(CommunicationEdge communicationEdge) {
                final ObjectNode edge = edges.addObject();
                edge.with("data").put("id", new Random().nextInt())
                    .put("source", componentGraph.getEdgeSource(communicationEdge).getUuid())
                    .put("target", componentGraph.getEdgeTarget(communicationEdge).getUuid());
                if (communicationEdge.communication().isMandatory()) {
                    edge.put("classes", "mandatory");
                }
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
    }


    private static class CommunicationEdgeFactory
        implements EdgeFactory<ApplicationComponent, CommunicationEdge> {

        private final boolean onlyMandatory;

        public CommunicationEdgeFactory(boolean onlyMandatory) {
            this.onlyMandatory = onlyMandatory;
        }

        @Override public CommunicationEdge createEdge(ApplicationComponent sourceVertex,
            ApplicationComponent targetVertex) {
            final List<Communication> communications =
                sourceVertex.getRequiredCommunications().stream().filter(
                    communication -> communication.getProvidedPort().getApplicationComponent()
                        .equals(targetVertex)).collect(Collectors.toList());
            if (communications.size() > 1) {
                throw new IllegalStateException(String
                    .format("Found multiple communications between %s and %s", sourceVertex,
                        targetVertex));
            } else if (communications.size() == 0) {
                if (onlyMandatory) {
                    return null;
                }
                throw new IllegalStateException(String
                    .format("Found no communication between %s and %s", sourceVertex,
                        targetVertex));
            } else {
                return new CommunicationEdge(communications.get(0));
            }

        }
    }


    private static class GraphFactory {

        public static DirectedPseudograph<ApplicationComponent, CommunicationEdge> of(
            Application application, boolean onlyMandatory) {

            DirectedPseudograph<ApplicationComponent, CommunicationEdge> componentGraph =
                new DirectedPseudograph<>(new CommunicationEdgeFactory(onlyMandatory));
            application.getApplicationComponents().forEach(componentGraph::addVertex);
            application.communications().stream().filter(new Predicate<Communication>() {
                @Override public boolean test(Communication communication) {
                    return !onlyMandatory || communication.isMandatory();
                }
            }).forEach(communication -> componentGraph
                .addEdge(communication.getSource(), communication.getTarget()));
            return componentGraph;
        }

        public static DirectedPseudograph<ApplicationComponent, CommunicationEdge> of(
            Application application) {
            return GraphFactory.of(application, false);
        }
    }

}

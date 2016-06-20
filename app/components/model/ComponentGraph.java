package components.model;

import models.Application;
import models.ApplicationComponent;
import models.PortRequired;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 17.06.16.
 */
public class ComponentGraph {

    private final DirectedGraph<ApplicationComponent, DefaultEdge> componentGraph;
    private final DirectedGraph<ApplicationComponent, DefaultEdge> mandatoryComponentGraph;

    private ComponentGraph(Application application) {
        componentGraph = GraphFactory.of(application);
        mandatoryComponentGraph = GraphFactory.of(application, true);
    }

    public static ComponentGraph of(Application application) {
        return new ComponentGraph(application);
    }

    public boolean hasCycle() {
        CycleDetector<ApplicationComponent, DefaultEdge> cycleDetector =
            new CycleDetector<>(mandatoryComponentGraph);
        return cycleDetector.detectCycles();
    }

    private static class GraphFactory {

        public static DirectedGraph<ApplicationComponent, DefaultEdge> of(Application application,
            boolean onlyMandatory) {
            DirectedGraph<ApplicationComponent, DefaultEdge> componentGraph =
                new DefaultDirectedGraph<>(DefaultEdge.class);
            application.getApplicationComponents().forEach(componentGraph::addVertex);
            application.getApplicationComponents().forEach(
                applicationComponent -> applicationComponent.getRequiredPorts()
                    .forEach((Consumer<PortRequired>) portRequired -> {
                        if (onlyMandatory && !portRequired.isMandatory()) {
                            return;
                        }
                        checkState(portRequired.communication() != null, String
                            .format("portRequired %s is missing communication entity",
                                portRequired));
                        componentGraph.addEdge(portRequired.communication().getProvidedPort()
                                .getApplicationComponent(),
                            portRequired.communication().getRequiredPort()
                                .getApplicationComponent());
                    }));
            return componentGraph;
        }

        public static DirectedGraph<ApplicationComponent, DefaultEdge> of(Application application) {
            return GraphFactory.of(application, false);
        }

    }

}

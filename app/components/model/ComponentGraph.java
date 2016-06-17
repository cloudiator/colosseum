package components.model;

import models.Application;
import models.ApplicationComponent;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Created by daniel on 17.06.16.
 */
public class ComponentGraph {

    private final DirectedGraph<ApplicationComponent, DefaultEdge> componentGraph;

    private ComponentGraph(Application application) {
        componentGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        application.getApplicationComponents().forEach(componentGraph::addVertex);
        application.getApplicationComponents().forEach(
            applicationComponent -> applicationComponent.getRequiredPorts().forEach(
                portRequired -> componentGraph.addEdge(
                    portRequired.communication().getProvidedPort().getApplicationComponent(),
                    portRequired.communication().getRequiredPort().getApplicationComponent())));
    }

    public static ComponentGraph of(Application application) {
        return new ComponentGraph(application);
    }

    public boolean hasCycle() {
        CycleDetector<ApplicationComponent, DefaultEdge> cycleDetector =
            new CycleDetector<>(componentGraph);
        return cycleDetector.detectCycles();
    }

}

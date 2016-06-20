package components.model;

import com.mxgraph.util.mxCellRenderer;
import models.Application;
import models.ApplicationComponent;
import models.PortRequired;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;
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
        checkNotNull(application);
        return new ComponentGraph(application);
    }

    private CycleDetector<ApplicationComponent, DefaultEdge> cycleDetector() {
        return new CycleDetector<>(mandatoryComponentGraph);
    }

    public boolean hasCycle() {
        return cycleDetector().detectCycles();
    }

    public Set<ApplicationComponent> cycles() {
        return cycleDetector().findCycles();
    }

    public File image() {
        final JGraphXAdapter<ApplicationComponent, DefaultEdge> jGraphX =
            new JGraphXAdapter<>(componentGraph);
        final BufferedImage bufferedImage =
            mxCellRenderer.createBufferedImage(jGraphX, null, 1, Color.WHITE, true, null);
        try {
            final File tempFile = File.createTempFile("cloudiator", ".png");
            ImageIO.write(bufferedImage, "PNG", tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

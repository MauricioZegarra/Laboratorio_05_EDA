import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import java.util.Scanner;

public class GraphAVLWord<E extends Comparable<E>> {

    private Avl<E> avl;
    private Graph graph;

    private static final String styleSheet = "node {"
            + "	shape: circle;"
            + "	size: 40px;"
            + "       text-size: 12;"
            + "	fill-mode: plain;"
            + "	fill-color: white;"
            + "	stroke-mode: plain;"
            + "	stroke-color: black;"
            + "	stroke-width: 1px;"
            + "}"
            + "edge { arrow-shape: arrow; arrow-size: 20px, 4px; }";

    public AVLGraphPrinter(Avl<E> avl) {
        this.avl = avl;
        this.graph = new SingleGraph("AVL Tree");
        
        this.graph.setAttribute("ui.stylesheet", styleSheet);
    }

    private void addNodes(NodeAvl<E> node, Node parentNode) {
        if (node != null) {
            Node graphNode = this.graph.addNode(node.getData().toString());
            graphNode.setAttribute("ui.label", node.getData().toString());
            if (parentNode != null) {
                this.graph.addEdge(parentNode.getId() + "_" + graphNode.getId(), parentNode, graphNode);
            }
            addNodes(node.getLeft(), graphNode);
            addNodes(node.getRight(), graphNode);
        }
    }

    public void print() {
        addNodes(this.avl.root, null);
        addEdges(this.avl.root);
        
        this.graph.display();
    }

}

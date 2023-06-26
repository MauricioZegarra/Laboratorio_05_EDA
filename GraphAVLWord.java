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

    public GraphAVLWord(Avl<E> avl) {
        this.avl = avl;
        this.graph = new SingleGraph("AVL Tree");
        
        this.graph.setAttribute("ui.stylesheet", styleSheet);
    }

    private void addNodes(Nodo<E> node, Node parentNode) {
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

    private void addEdges(Nodo<E> current) {
        if (current.getLeft() != null) {
            String edgeId = current.getData().toString() + "_" + current.getLeft().getData().toString();
            if (this.graph.getEdge(edgeId) == null) {
                this.graph.addEdge(edgeId, current.getData().toString(), current.getLeft().getData().toString());
            }
            addEdges(current.getLeft());
        }

        if (current.getRight() != null) {
            String edgeId = current.getData().toString() + "_" + current.getRight().getData().toString();
            if (this.graph.getEdge(edgeId) == null) {
                this.graph.addEdge(edgeId, current.getData().toString(), current.getRight().getData().toString());
            }
            addEdges(current.getRight());
        }
    }

    public static void main(String[] args) throws ExceptionNoFound {

        System.setProperty("org.graphstream.ui", "swing");

        Scanner sc = new Scanner(System.in);
        
        System.out.print("Introduzca la palabra: ");
        String word = sc.next();
        
        Avl<Character> avl = new Avl<Character>();
        
        for (int i = 0; i < word.length(); i++) {
            avl.insert(word.charAt(i));
        }
        
        System.out.println("\nLa cabeza del AVL es: " + avl.getRoot());

        GraphAVLWord<Character> graphPrinter = new GraphAVLWord<Character>(avl);
        graphPrinter.print();
    }

    public void print() {
        addNodes(this.avl.getRoot(), null);
        addEdges(this.avl.getRoot());
        
        this.graph.display();
    }

}

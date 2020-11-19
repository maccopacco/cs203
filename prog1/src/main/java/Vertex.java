import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vertex {
    private List<Vertex> connectedVertices;
    private int number;
    private int componentNumber = -1;

    public Vertex(int number) {
        this.number = number;
        this.connectedVertices = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void addConnectedVertex(Vertex otherVertex) {
        this.connectedVertices.add(otherVertex);
    }

    @Override
    public String toString() {
        final List<Integer> connected =
                connectedVertices.stream().map(Vertex::getNumber).collect(Collectors.toList());
        return String.format("Node {%d} connected to %s", number, connected);
    }

    public int getComponentNumber() {
        return componentNumber;
    }

    public void visitWith(int componentNumber) {
        this.componentNumber = componentNumber;
    }

    public boolean hasBeenVisited() {
        return componentNumber != -1;
    }

    public List<Vertex> getConnectedVertices() {
        return connectedVertices;
    }
}

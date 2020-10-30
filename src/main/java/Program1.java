import java.util.*;

class Program1 {
    public static void main(String[] args) {
        List<Vertex> vertices = getVertices(args);
        assignComponentNumbers(vertices);
        assembleComponents(vertices);
    }

    /**
     * Errors checks args and returns list of {@link Vertex} with {@link Vertex#addConnectedVertex(Vertex)}
     * appropriately called
     *
     * @param args input arguments
     * @return list of {@link Vertex}
     */
    private static List<Vertex> getVertices(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException(String.format("%s required >= 2 inputs, received %d: %s", Program1.class.getSimpleName(), args.length, List.of(args)));
        }

        final int numberOfNodes;
        final String firstArg = args[0];
        try {
            numberOfNodes = Integer.parseInt(firstArg);
        } catch (NumberFormatException numberFormat) {
            throw new NumberFormatException(String.format("Could not parse [%s] as number of vertices", firstArg));
        }

        //Add vertices to list
        List<Vertex> vertices = new ArrayList<>();
        for (int vertex = 1; vertex < numberOfNodes + 1; vertex++) {
            vertices.add(new Vertex(vertex));
        }

        //Loop through args
        for (int vertexLoop = 1; vertexLoop < args.length; vertexLoop++) {
            String vertexString = args[vertexLoop];
            String commaSeperatedString = vertexString.replace("(", "").replace(")", "");
            String[] numbers = commaSeperatedString.split(",");

            //Error checking
            if (numbers.length != 2) {
                throw new IllegalArgumentException(String.format("A vertex pair must have 2 elements, [%s] had %d", vertexString, numbers.length));
            }
            final int indexA;
            final int indexB;
            try {
                indexA = Integer.parseInt(numbers[0]);
                indexB = Integer.parseInt(numbers[1]);
            } catch (NumberFormatException numberFormat) {
                throw new IllegalArgumentException(String.format("Could not parse [%s] to integers", vertexString));
            }

            //Check within reasonable bounds
            if (Math.min(indexA, indexB) < 1 || Math.max(indexA, indexB) > vertices.size()) {
                throw new IllegalArgumentException(String.format("[%s] has value too low, minimum = %d, maximum = %d", vertexString, 1, vertices.size()));
            }

            //Finally, add connection
            final Vertex vertexA = vertices.get(indexA - 1);
            final Vertex vertexB = vertices.get(indexB - 1);
            vertexA.addConnectedVertex(vertexB);
            vertexB.addConnectedVertex(vertexA);
        }
        return vertices;
    }

    /**
     * @param vertexList list of {@link Vertex} to convert to strign
     * @return {@link String} representing list of {@link Vertex}
     */
    private static String vertexListToString(List<Vertex> vertexList) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0, vertexListSize = vertexList.size(); i < vertexListSize; i++) {
            Vertex v = vertexList.get(i);
            builder.append(v.getNumber());
            final boolean last = i == vertexListSize - 1;
            if (!last) {
                builder.append(" ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * @param vertices to assemble into components and display
     */
    private static void assembleComponents(List<Vertex> vertices) {
        Map<Integer, List<Vertex>> ids = new HashMap<>();
        for (Vertex v : vertices) {
            final int componentNumber = v.getComponentNumber();
            List<Vertex> component;
            if (!ids.containsKey(componentNumber)) {
                component = new ArrayList<>();
                ids.put(componentNumber, component);
            } else {
                component = ids.get(componentNumber);
            }
            component.add(v);
        }

        StringBuilder components = new StringBuilder();
        for (List<Vertex> componentList : ids.values()) {
            //Sort component list and add string result to StringBuilder
            componentList.sort(Comparator.comparingInt(Vertex::getNumber));
            components.append(vertexListToString(componentList)).append(" ");
        }

        System.out.printf("%d connected components: %s%n", ids.size(), components.toString());
    }

    /**
     * @param vertices list to assign component numbers to
     */
    private static void assignComponentNumbers(List<Vertex> vertices) {
        int count = 0;

        //For each vertex, surf over it's connected components with a number.
        //If we haven't visited the vertex/it's connected components yet, assign a number to all of them. That means
        //they are a connected component

        //For graph A -- B  C -- D
        //count equals 0
        //Visit A. mark A with 0. visit As connected vertices. mark B with 0. count++

        //count now equals 1
        //Now visit B. B has already been visited, move on. count++

        //count now equals 2
        //Visit C. C has not been visited. C is marked with 2. Surf Cs connected vertices. Mark D with 2. count++

        //count not equals 3
        //Visit D. D has already been visited, move on.

        //A & B have been marked with 0. C & D have been marked with 2. In list [0,2] we have two unique numbers. There
        //are two connected components.
        for (Vertex v : vertices) {
            surfTree(v, count++);
        }
    }

    /**
     * @param vertex {@link Vertex} vertex to surf
     * @param count
     */
    private static void surfTree(Vertex vertex, int count) {
        if (!vertex.hasBeenVisited()) {
            vertex.visitWith(count);
            for (Vertex v : vertex.getConnectedVertices()) {
                surfTree(v, count);
            }
        }
    }

}
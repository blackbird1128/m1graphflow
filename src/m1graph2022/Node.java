package m1graph2022;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node implements Comparable<Node> {

    String name;
    int id;


    public Node(int _id)
    {

        id = _id;
        name = "default";

    }

    public Node(int _id , String _name)
    {
        id = _id;
        name = _name;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "" + id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Node o) {
        return id - o.id;
    }

    public int getId() {
        return id;
    }
}

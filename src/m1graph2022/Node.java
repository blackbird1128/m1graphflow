package m1graph2022;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node implements Comparable<Node> {

    String name = null;
    int id;
    static int max_id = -1;


    public Node(int _id)
    {
        if (_id>max_id){
            max_id = _id;
        }
        id = _id;
        name = null;

    }

    public Node(int _id , String _name)
    {
        if (_id>max_id){
            max_id = _id;
        }
        id = _id;
        name = _name;
    }

    public Node(String _name){
        id = ++max_id;
        name = _name;
    }

    public Node(){
        id = ++max_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass().getSimpleName().equals("int") || o.getClass().getSimpleName().equals("Integer")){
            return this.id== (Integer) o;
        }
        if (o.getClass().getSimpleName().equals("String")){
            return name.equals((String) o);
        }
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return (name != null && node.name != null && name.equals(node.name)) || id == node.id;
    }

    @Override
    public int hashCode() {
        if(name!=null){
            return Objects.hash(name);
        }
        return Objects.hash(null, id);
    }

    @Override
    public String toString() {
        return name!=null ? name : ""+id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Node o) {
        return id - o.id;
    }

    public int getId() {
        return id;
    }
}

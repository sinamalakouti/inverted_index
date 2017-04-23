package invertedIndex.utils.datastructres.linkedList;

import invertedIndex.utils.datastructres.DataStructreNode;

/**
 * Created by sina on 11/30/16.
 *
 *
 *
 * linked list node
 */
public class Node <T> extends DataStructreNode implements Comparable {

    private T data;
    private Node next;

    public Node(T data) {
        this.data = data;
    }

    public Node() {
        this(null);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public int compareTo(Object o) {
        return 0;


    }
}

package invertedIndex.utils.datastructres.trees.bst;

import invertedIndex.utils.datastructres.linkedList.LinkedList;

/**
 * Created by sina on 11/29/16.
 */

public class Node <T>extends invertedIndex.utils.datastructres.trees.Node  {
    protected Node <T>leftChild;
    protected Node <T>rightChild;

    public Node(T data) {
        super.data = data;
        this.rightChild = null;
        this.leftChild = null;
        this.listOfFiles = new LinkedList();

    }


    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node <T>getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }


    


}

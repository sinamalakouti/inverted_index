package invertedIndex.utils.datastructres.trees.tst;

/**
 * Created by sina on 12/1/16.
 */
public class Node<T> extends invertedIndex.utils.datastructres.trees.Node <T> {



    private Node leftChild;
    private Node rightChild;
    private Node middleChild;
    private Boolean isEndOfWord;
    private Node parentNode;

    public Node(T data, Boolean isEndOfWord) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
        this.middleChild = null;
        this.isEndOfWord = isEndOfWord;
    }






    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node getMiddleChild() {
        return middleChild;
    }

    public void setMiddleChild(Node middleChild) {
        this.middleChild = middleChild;
    }

    public Boolean getEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(Boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
}

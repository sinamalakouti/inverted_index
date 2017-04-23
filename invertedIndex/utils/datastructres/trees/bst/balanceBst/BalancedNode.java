package invertedIndex.utils.datastructres.trees.bst.balanceBst;

import invertedIndex.utils.datastructres.linkedList.LinkedList;
import invertedIndex.utils.datastructres.trees.bst.Node;

/**
 * Created by sina on 1/10/17.
 */
public class BalancedNode <T>extends Node<T> {
    private int height ;

    public BalancedNode(T data) {
        super(data);
        this.height = 1;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

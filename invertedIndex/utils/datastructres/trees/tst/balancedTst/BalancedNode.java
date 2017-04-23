package invertedIndex.utils.datastructres.trees.tst.balancedTst;

import invertedIndex.utils.datastructres.trees.tst.Node;

/**
 * Created by sina on 1/10/17.
 */
public class BalancedNode <T> extends Node <T> {

    private int height ;


    public BalancedNode(T data, Boolean isEndOfWord) {
        super(data, isEndOfWord);
        this.height = 1;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

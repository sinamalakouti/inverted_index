package invertedIndex.utils.datastructres.trees;

import com.sun.source.tree.BinaryTree;
import invertedIndex.utils.datastructres.DataStructreNode;

import invertedIndex.utils.datastructres.linkedList.LinkedList;

import java.util.ArrayList;


/**
 * Created by sina on 12/9/16.
 */
public class Node<T>  extends DataStructreNode<T> implements Comparable  {
    @Override
    public int compareTo(Object o) {
        return ( o.toString()).compareTo( this.data.toString());
    }

}


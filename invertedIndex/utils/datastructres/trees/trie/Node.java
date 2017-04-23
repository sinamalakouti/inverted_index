package invertedIndex.utils.datastructres.trees.trie;

import invertedIndex.utils.datastructres.linkedList.LinkedList;



/**
 * Created by sina on 12/7/16.
 */
public class Node <T> extends invertedIndex.utils.datastructres.trees.Node<T>  {

    private LinkedList childeren;
    public Node parent;

    private boolean isEndOfWord;

    public Node(T data, boolean isEndOfWord) {
        this.data  = data;
        listOfFiles = new LinkedList();
        childeren = new LinkedList();
        this.isEndOfWord = isEndOfWord;

        // todo : constructor
    }
    public void addChild(Node child){
        this.childeren.add(child);

    }


    public Node subNode (Character data ){

        invertedIndex.utils.datastructres.linkedList.Node tempNode = this.childeren.getFirst().getNext();

        while ( tempNode != null){

            if(   ( (Node) tempNode.getData()).getData().equals(data) ){

                return   (Node) tempNode.getData();

            }


            tempNode = tempNode.getNext();
        }
        return  null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LinkedList getChilderen() {
        return childeren;
    }

    public void setChilderen(LinkedList childeren) {
        this.childeren = childeren;
    }




    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }


}

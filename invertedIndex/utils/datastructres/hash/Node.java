package invertedIndex.utils.datastructres.hash;

import invertedIndex.utils.datastructres.linkedList.LinkedList;

import java.util.ArrayList;

/**
 * Created by sina on 12/21/16.
 */
public class Node < T > {

//    private T key;
    private T value;
//    private Node<K,V> nextNode ;
    private  LinkedList listOfFiles;
    private ArrayList<String> wordsPosition;


    public Node(T value) {
//        this.key =key;
        this.value = value;
    }

//    public K getKey() {
//    }

    public T getValue() {
        return value;
    }

//    public Node<K, V> getNextNode() {
//        return nextNode;
//    }

//    public void setNextNode(Node<K, V> nextNode) {
//        this.nextNode = nextNode;
//    }


    public void  addToListOfFilles(String fileName){

        if( this.listOfFiles == null )
            listOfFiles = new LinkedList();
        this.listOfFiles.add(fileName);

    }

    public void addToWordsPositions(String position){

        if( this.wordsPosition == null)
            this.wordsPosition = new ArrayList<>();
        this.wordsPosition.add(position);
    }

    public boolean removeFromListOfFiles(String fileName) {

        if (listOfFiles == null || listOfFiles.getSize() == 0) {
            this.listOfFiles.delete(fileName);
            return true;
        }

        return false;
    }

    public boolean removeFromWordsPositions(String position){

        if( wordsPosition == null ||wordsPosition.size() == 0 ){

            this.wordsPosition.remove(position);
            return true;
        }
        return false;
    }


}

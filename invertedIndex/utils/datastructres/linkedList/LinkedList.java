package invertedIndex.utils.datastructres.linkedList;


import invertedIndex.utils.datastructres.DataStructre;
import invertedIndex.utils.datastructres.DataStructreNode;
import invertedIndex.utils.datastructres.DataStructreNode;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by sina on 11/29/16.
 *
 *
 *
 *
 * linked list clss
 *
 *
 *
 */
public   class LinkedList <T> extends DataStructre{
// TODO  : last
    private Node <T> head ;
//    private  Node last;
    private int size;


    public LinkedList() {
        head = new Node<T>();
//        last = head;
        this.size = 0;
    }
    public LinkedList (LinkedList list) {

        head = new Node<T>();
        Node <T>listCurrentNode = list.getFirst().getNext();

        while ( listCurrentNode != null){


            this.add(listCurrentNode.getData());

            listCurrentNode = listCurrentNode.getNext();

        }




    }

//     inserts the specified element at the specified position in this list if this position is not exists then add to end
    public void add(T data, int index){
        Node<T> tempNode = new Node<T>(data);
        Node<T> currentNode = head;

        // if the list is not exits then we make new one
        if (head == null ) {
            head = new Node<T>();
            currentNode = head;
        }

        // going to the index that should add node to
        for ( int i =0 ; i<index && currentNode.getNext()!=null; i++)
            currentNode = currentNode.getNext();
        //setting new node's ref to the currentNode's next
        tempNode.setNext(currentNode.getNext());

        // now set the current node's next to the new node
        currentNode.setNext(tempNode);
        // if it was added to the end of the list so it's last node's point must updated
//        if( tempNode.getNext() == null)
//            last = tempNode;
        // incrementing the number of elements in the linkedlist
        this.incrementSize();



    }
    // adding an object to the end of the linked list
    public void add(T data){

        Node<T> tempNode = new Node<T>(data);
//        Node lastNode  = this.last;
        Node <T>currentNode = head;

//        lastNode.setNext(tempNode);
//        this.last = tempNode;

        while( currentNode.getNext() !=null)
            currentNode = currentNode.getNext();

        currentNode.setNext(tempNode);
        this.incrementSize();

    }
    // returns element at specific index
    public Node <T>get (int index){
        Node<T> currentNode = null;
        // index should be greater than 0
        if ( index < 0) return  currentNode;

        if ( head != null){

            currentNode = head.getNext();
            for( int i=0 ; i<index ; i++){
                if( currentNode.getNext() == null) return  null;
                currentNode = currentNode.getNext();
            }
            return currentNode;
        }
        return currentNode;
    }

    public DataStructreNode get(Object data){

        return get(this.indexOf((T)data));

    }

    // removing object at the specific position
    public boolean  delete(int index) {

        Node<T> currentNode;

        if (index < 0 || index >= size)
            return false;
        currentNode = head;

        for (int i = 0; i < index; i++) {

            if (currentNode.getNext() == null)
                return false;
            currentNode = currentNode.getNext();
        }

        currentNode.setNext(currentNode.getNext().getNext());
//        if ( this.size -1 == index)
//            this.last = currentNode.getNext();
        this.decrementSize();
        return true;
    }
    public Node<T> getFirst(){


        return head;
    }

    @Override
    public boolean delete(Object word) {

        return this.delete(this.indexOf((T)word));
    }


    @Override
    public ArrayList<String> traverse(ArrayList array) {
        return null;
    }

    public int indexOf(T data){

        if( head == null)
            return -1;

        Node current = this.head.getNext();
        int index = 0;

        while(current != null){

            if( current.getData().equals(data))
                return  index;
            index ++;
            current = current.getNext();

        }
        return  -1 ;


    }

    public boolean contains(T data){

    Node currentNode ;

        if ( head == null) return  false;

        currentNode = head.getNext();

        while (currentNode != null){

            if( currentNode.getData().equals(data)) return true;
            currentNode = currentNode.getNext();
        }
        return  false;
    }


    public void incrementSize(){

        this.size ++;
    }

    public void decrementSize(){

        this.size --;

    }
    public boolean isEmpty(){

        return size == 0;

    }
    public int getSize(){
        return this.size;

    }

    @Override
    public String toString() {
            String output = "";

            if (head != null) {
                Node current = head.getNext();
                while (current != null) {
                    if( current.getNext() != null)
                    output += "[" + current.getData().toString() + "],";
                    else
                        output += "[" + current.getData().toString() + "]";

                    current = current.getNext();
                }

            }
            return output;
    }

    @Override
    public void add(Object data, String fileName, String wordPosition) {

    }
    @Override
    public void update(ArrayList wordsInFile, String filename) {

    }
}

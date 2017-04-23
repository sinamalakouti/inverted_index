package invertedIndex.utils.datastructres.trees.trie;


import invertedIndex.Word;
import invertedIndex.managers.FileManager;
import invertedIndex.utils.datastructres.DataStructreNode;
import invertedIndex.utils.datastructres.stack.Stack;
import invertedIndex.utils.datastructres.trees.Tree;

import java.util.ArrayList;
import java.util.Scanner;


/*
  Created by sina on 11/29/16.
 */


public class Trie <T>extends Tree<T> {
    Node root;

    public Trie(Object data) {

        root = new Node(data, false);
    }


    public Trie() {

        root = new Node(null, false);
    }


    @Override
    public void add(T data) {
        if (data.getClass().equals(Word.class)) {

            this.add((T) ((Word) data).getData(), (String) ((Word) data).getListOfFiles().get(0).getData(),
                    FileManager.getFileManagerSharedInstance().filePostitionStringGenerator(((Word) data).getFileNumber(),
                            ((Word) data).getWordNumber()));
        }
    }

    public void add(T data, String fileName , String wordPosition) {

        // TODO

        if (root == null)
            return;
        if (this.contains(data)) {
            Node tempNode = (Node) this.get(data);
            tempNode.addToFiles(fileName);
            tempNode.addToWordsPosition(wordPosition);


        }else
        this.add(root, ((String) data).toCharArray(), fileName , 0 , wordPosition, null);
    }


    public void add(Node node, char[] word, String filename , int counter , String wordPosition , Node parent) {

        // TODO :

        Node child ;
        parent = root;


            for (Character character : word) {

                try {

                    child = node.subNode(character);
                }catch (Exception e){
                    child = null;
                }
              // if null -> add if not null traverse :D
                if (child == null) {

                    Node tempNode = new Node(character , false);
                    tempNode.parent = node;
                    node.addChild(tempNode);
                    this.sizeIncrementer();
                    if (counter == word.length-1) {
                        tempNode.setEndOfWord(true);
                        tempNode.addToFiles(filename);
                        tempNode.addToWordsPosition(wordPosition);
                    }
                }else {
                    if( counter == word.length-1 && child.getData().equals(character)){

                        child.setEndOfWord(true);
                        child.addToFiles(filename);
                        child.addToWordsPosition(wordPosition);
                    }
                }
                counter++;
                node = node.subNode(character);
            }


    }


    // function that search for the word
    public boolean contains(Object data) {

        if (root == null)
            return false;
        Node currentNode = this.root;

        for (Character character : ((String) data).toCharArray()) {


            if (currentNode.subNode(character) == null)
                return false;
            else {


                currentNode = currentNode.subNode(character);

            }


        }
        return currentNode.isEndOfWord();


    }

    //     function that gives a node with specific data (returning the node with true endOfOWord, last node of the tree which is part of the word )
    public DataStructreNode get(T data) {

        if (root == null)
            return null;

        Node currentNode = root;

        for (Character character : ((String) data).toCharArray()) {

            if (currentNode.subNode(character) == null)
                return currentNode.subNode(character);

            else
                currentNode = currentNode.subNode(character);
        }


        if (currentNode.isEndOfWord())
            return currentNode;
        return null;

    }

    public boolean delete(Object word) {
        TODO :


        return this.delete(root, (String) word);


    }



    private boolean delete(Node currentNode , String word) {
        Stack parents = new Stack();
        Node temp;

        for (Character character : (word).toCharArray()) {

            if (currentNode.subNode(character) == null)
                 return false;

            else{
                parents.push(currentNode);
                currentNode = currentNode.subNode(character);
            }
        }

        if ( currentNode == null || currentNode.getData() == null ||! currentNode.isEndOfWord())
            return false;

        if (currentNode != null) {
            currentNode.setEndOfWord(false);
            currentNode.makeListOfFilesEmpty();
            currentNode.makeWordPositionsEmpty();
            while ( (currentNode.getChilderen() == null || currentNode.getChilderen().isEmpty()) && currentNode != null && currentNode != root){


                temp = currentNode;
                currentNode=(Node) parents.pop();
                 currentNode.getChilderen().delete(temp) ;
                this.sizeDecrementer();
                if ( currentNode.getChilderen().getSize() == 0);
//                    currentNode.setChilderen(null);



            }

            return true;
        }
        return false;

    }


    @Override
    public void update(ArrayList wordsInFile, String filename) {






        ArrayList<String> wordsInTree = new ArrayList<>();
        ArrayList<String> wordsDataInFile ;
        ArrayList<String> wordsPositions ;
        wordsInTree = this.traverse(root, "", wordsInTree);
        wordsDataInFile = this.wordArrayToStringArray(wordsInFile);
        wordsPositions = this.wordArrayToPositionArray(wordsInFile);

        for (String word : wordsInTree) {
            Node tempNode = (Node)this.get((T)word);

            if (!wordsDataInFile.contains(word)) {

                tempNode.removeFromFile(filename);


            }

            if (tempNode.getListOfFiles().getSize() == 0 || tempNode.getListOfFiles() == null)
                this.delete(word);
            else
            for  (int i =0 ; i< tempNode.getWordsPosition().size() ; i++ ){

                if ( !wordsPositions.contains(tempNode.getWordsPosition().get(i))) {
                    if ( FileManager.getFileManagerSharedInstance().parsingWordPositionString((String)tempNode.
                            getWordsPosition().get(i)).get(0).equals(FileManager.getFileManagerSharedInstance().
                            parsingWordPositionString(wordsPositions.get(0)).get(0))) {
                        tempNode.removeFromWordPosition(i);
                        i--;
                    }
                }else if ( ! tempNode.getData().equals(wordsDataInFile.get(wordsPositions.indexOf(tempNode.getWordsPosition().get(i))))){

                    tempNode.removeFromWordPosition(i);
                    i--;
                }






            }
        }
            for ( int i =0 ; i< wordsDataInFile.size() ; i++ ){
                Node tempNode = (Node)this.get((T) wordsDataInFile.get(i));

            if (tempNode == null || !wordsInTree.contains(wordsDataInFile.get(i))) {


                this.add((T) wordsDataInFile.get(i), filename , wordsPositions.get(i) );

            }   else
                this.get((T) wordsDataInFile.get(i)).addToFiles(filename);
                if ( tempNode != null && ! tempNode.getWordsPosition().contains(wordsPositions.get(i))) {
                    tempNode.addToWordsPosition(wordsPositions.get(i));
                }


        }


    }

    @Override
    public boolean isEmpty() {
        return this.getSize() == 0 || this.root == null;
    }

    public String toString() {
        ArrayList<String> arr = new ArrayList<>();
        arr = this.traverse(root, "", arr);
        return arr.toString();

    }

    @Override
    public ArrayList<String> traverse(ArrayList array) {

        return this.traverse(root, "", array);

    }

    @Override
    public int getHeight() {
        if (this.isEmpty())
            return 0;
        // minus 1 is for root ( root is always null it is not one of the words)
        return getHeight(root) - 1;


    }

    private int getHeight(Node node) {
        Integer height = new Integer(0);
        int tempHeight = 0;

        invertedIndex.utils.datastructres.linkedList.Node currentNode = node.getChilderen().getFirst().getNext();

        while (currentNode != null) {

            tempHeight = this.getHeight(((Node) currentNode.getData()));

            if (tempHeight > height.intValue())
                height = Integer.parseInt(tempHeight +"")  ;
            else tempHeight  = height;
            currentNode = currentNode.getNext();
        }

        return tempHeight + 1;


    }


    private ArrayList<String> traverse(Node node, String str, ArrayList<String> arr) {


        if (node != null) {
            if (node.getData() != null)
                str = str + node.getData();
            if (node.isEndOfWord()) {
                arr.add(str);
            }

            invertedIndex.utils.datastructres.linkedList.Node linkedListNode = node.getChilderen().getFirst().getNext();
            while (linkedListNode != null) {


                arr = this.traverse((Node) linkedListNode.getData(), str, arr);

                linkedListNode = linkedListNode.getNext();

            }


        }
        return arr;


    }





}

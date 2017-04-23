package invertedIndex.utils.datastructres.hash;

import invertedIndex.Word;
import invertedIndex.managers.FileManager;
import invertedIndex.utils.datastructres.DataStructre;
import invertedIndex.utils.datastructres.DataStructreNode;
import invertedIndex.utils.datastructres.linkedList.LinkedList;
import invertedIndex.utils.datastructres.linkedList.Node;
import invertedIndex.utils.datastructres.trees.bst.BstTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sina on 12/21/16.
 * <p>
 * <p>
 * implementing Hash Table  using chaining addressing
 */
public class HashTable<T>  extends DataStructre<T>{
    // table
    private Object[] hashTable;
    private boolean bucketIsLinkedList[];
    private int maxCapacity;
    private int eachBucketCapacity = 10;


    public HashTable(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        hashTable = new Object[maxCapacity];
        this.settingBucketArray();

    }


    public int getSize() {


        return hashTable.length;
    }

    public void settingBucketArray() {
        bucketIsLinkedList = new boolean[maxCapacity];
        for (int i = 0; i < maxCapacity; i++) {

            hashTable[i] = new HashLinkedList<>();
            bucketIsLinkedList[i] = true;
        }
    }

    @Override
    public boolean delete(T word) {


        int index = this.getHashCode((String) word);
        if (index < 0)
            return false;
        if (hashTable[index].getClass().equals(HashLinkedList.class)) {

            return ((HashLinkedList) hashTable[index]).delete(word);

        } else {
            // is bst tree
            return ((BstTree) hashTable[index]).delete(word);

        }

    }
    @Override
    public DataStructreNode get(Object data) {

        int index = this.getHashCode((String) data);
        if (hashTable[index].getClass().equals(HashLinkedList.class)) {
            return (DataStructreNode) (((HashLinkedList) hashTable[index]).get(data)).getData();


        } else {

            return ((BstTree) hashTable[index]).get(data);
        }

    }





    //    TODO : :D

    @Override
    public ArrayList<String> traverse(ArrayList array) {

        for (int i = 0; i < maxCapacity; i++) {


            if (hashTable[i] != null && hashTable[i].getClass().equals(HashLinkedList.class)) {
                array.addAll(((HashLinkedList) hashTable[i]).traverse(new ArrayList()));


            } else if (hashTable[i] != null) {


                array.addAll(((BstTree) hashTable[i]).traverse(new ArrayList<>()));
            }


        }
// TODO : check
        return array;


    }

    public boolean isEmpty() {

        return this.getSize() == 0;
    }

    public String toString() {
        String output = "{  ";

        for (int i = 0; i < maxCapacity; i++) {

            if (hashTable[i] != null && hashTable[i].getClass().equals(HashLinkedList.class)) {

                output = output + ((HashLinkedList) hashTable[i]).toString() + "  }, { ";

            } else if (hashTable[i] != null) {
                output = output + ((BstTree) hashTable[i]).toString() + "  }, {  ";
            }


        }
        return output;

    }

    private boolean equals() {
//        hash table
        return false;
    }

    public ArrayList<Word>[] indexingWordsOfFile(ArrayList<Word> wordArray) {
        ArrayList<Word> stringArray[] = new ArrayList[maxCapacity];
//        ArrayList<String> stringArray = new ArrayList<>();
        for (int i = 0; i < maxCapacity; i++) {
            stringArray[i] = new ArrayList<>();
        }

        for (Word word : wordArray) {
            int index = this.getHashCode(word.getData());
            stringArray[index].add(word);

        }

        return stringArray;

    }

    public ArrayList<String>[] wordArrayToPositionArray(ArrayList<Word> wordArray) {

        ArrayList<String>[] positionArray = new ArrayList[maxCapacity];
//        ArrayList<String> positionArray = new ArrayList<>();
        for (int i = 0; i < maxCapacity; i++)
            positionArray[i] = new ArrayList<>();

        for (Word word : wordArray) {
            int index = this.getHashCode(word.getData());
            positionArray[index].add(FileManager.getFileManagerSharedInstance().
                    filePostitionStringGenerator(word.getFileNumber(), word.getWordNumber()));

        }

        return positionArray;


    }


    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        maxCapacity = maxCapacity;
    }

//    public void add(T data, String filenName, String wordPosition) {
//
//        int index = this.getHashCode(data.toString());
//
//        // this is linked list
//        System.out.println("index is \t" + index);
//        if (bucketIsLinkedList[index]) {
//            if (hashTable[index] == null)
//                hashTable[index] = new HashLinkedList<>();
//            if (!((HashLinkedList) hashTable[index]).contains(data)) {
//                System.out.println("1");
//
//                ((HashLinkedList) hashTable[index]).add(new HashLinkedListNode<>(data, filenName, wordPosition));
//            } else {
//                System.out.println("2");
//
//                ((HashLinkedListNode) ((HashLinkedList) hashTable[index]).get(data).getData()).addToFiles(filenName);
//                ((HashLinkedListNode) ((HashLinkedList) hashTable[index]).get(data).getData()).addToWordsPosition(wordPosition);
//            }
//            this.checkWheterShouldUseLinkedListOrBst(index);
//
//        } else if (bucketIsLinkedList[index] == false) {
//            if (hashTable[index].getClass().equals(HashLinkedList.class)) {
//                BstTree tree = new BstTree();
//                hashTable[index] = this.convertingLinkedListToBst((HashLinkedList) hashTable[index], tree);
//                System.out.println("tree is \t" + tree.traverse(new ArrayList<>()));
//            }
//
//
//        }
//
//
//    }

    private BstTree<T> convertingLinkedListToBst(HashLinkedList<T> linkedList, BstTree tree) {

        Node<T> currentNode = linkedList.getFirst().getNext();
        while (currentNode != null) {
            Node<T> fileNode = ((HashLinkedListNode) currentNode.getData()).getListOfFiles().getFirst().getNext();
            for (int i = 0; i < ((HashLinkedListNode) currentNode.getData()).getWordsPosition().size(); i++) {

//                tree.add(((HashLinkedListNode) currentNode.getData()).getData(),(String) fileNode.getData() ,((HashLinkedListNode) currentNode.getData()).getWordsPosition().get(i));
                tree.add(((HashLinkedListNode) currentNode.getData()).getData(), (String) fileNode.getData(), (String) ((HashLinkedListNode) currentNode.getData()).getWordsPosition().get(i));
                if (fileNode.getNext() != null)
                    fileNode = fileNode.getNext();
            }
            currentNode = currentNode.getNext();


        }


        return tree;
    }

    private void checkWheterShouldUseLinkedListOrBst(int index) {

        if (((HashLinkedList) hashTable[index]).getSize() >= this.eachBucketCapacity)
            bucketIsLinkedList[index] = false;

    }

    public int getHashCode(String str) {
        int hash = str.hashCode();
        hash = hash % maxCapacity;
        if (hash < 0)
            hash += maxCapacity;

        return hash;
//        return  super.hashCode();
    }

    @Override
    public void add(Object data, String fileName, String wordPosition) {
        int index = this.getHashCode(data.toString());

        // this is linked list
//        System.out.println("index is \t" + index);
        if (bucketIsLinkedList[index]) {
            if (hashTable[index] == null)
                hashTable[index] = new HashLinkedList<>();
            if (!((HashLinkedList) hashTable[index]).contains(data)) {
//                System.out.println("1");

                ((HashLinkedList) hashTable[index]).add(new HashLinkedListNode<>(data, fileName, wordPosition));
            } else {
//                System.out.println("2");

                ((HashLinkedListNode) ((HashLinkedList) hashTable[index]).get(data).getData()).addToFiles(fileName);
                ((HashLinkedListNode) ((HashLinkedList) hashTable[index]).get(data).getData()).addToWordsPosition(wordPosition);
            }
            this.checkWheterShouldUseLinkedListOrBst(index);

        } else if (bucketIsLinkedList[index] == false) {
            if (hashTable[index].getClass().equals(HashLinkedList.class)) {
                BstTree tree = new BstTree();
                hashTable[index] = this.convertingLinkedListToBst((HashLinkedList) hashTable[index], tree);

            }
            ((BstTree) hashTable[index]).add(data, fileName, wordPosition);
//            System.out.println("tree is \t" + ((BstTree) hashTable[index]).traverse(new ArrayList<>()));



        }


    }

    @Override
    public void update(ArrayList wordsInFile, String filename) {
        ArrayList<Word>[] wordsInFileData = this.indexingWordsOfFile(wordsInFile);

        // should delete
        for (int i = 0; i < maxCapacity; i++) {

            if (hashTable[i].getClass().equals(HashLinkedList.class)) {

                ((HashLinkedList) hashTable[i]).update(wordsInFileData[i], filename);

            } else {

                ((BstTree) hashTable[i]).update(wordsInFileData[i], filename);


            }


        }


    }

    private class HashLinkedListNode<T>  extends DataStructreNode{

        public HashLinkedListNode(T data, String fileName, String wordPosition) {
            this.data = data;
            this.addToFiles(fileName);
            this.addToWordsPosition(wordPosition);


        }



    }

    private class HashLinkedList<T> extends LinkedList {


        @Override
        public String toString() {
            String output = "";

            if (this.getFirst() != null) {
                Node current = this.getFirst().getNext();
                while (current != null) {
                    if (current.getNext() != null)
                        output += "[" + ((HashLinkedListNode) current.getData()).getData().toString() + "],";
                    else
                        output += "[" + ((HashLinkedListNode) current.getData()).getData().toString() + "]";

                    current = current.getNext();
                }

            }
            return output;
        }

        public ArrayList<String> traverse(ArrayList array) {

            Node currentNode = this.getFirst().getNext();
            while (currentNode != null) {

                array.add(((HashLinkedListNode) currentNode.getData()).getData());

                currentNode = currentNode.getNext();
            }
            return array;
        }


        @Override
        public boolean delete(Object word) {


            Node currentNode = this.getFirst();

            while (currentNode != null) {
                if (currentNode.getNext() == null)
                    return false;
                if (((HashLinkedListNode) currentNode.getNext().getData()).getData().equals(word)) {

                    currentNode.setNext(currentNode.getNext().getNext());
                    this.decrementSize();
                    return true;
                }

                currentNode = currentNode.getNext();
            }
            return false;
        }

        @Override
        public boolean delete(int index) {
            return super.delete(index);
        }

        @Override
        public Node get(Object data) {
            Node currentNode = this.getFirst().getNext();
            while (currentNode != null) {


                if (((HashLinkedListNode) currentNode.getData()).getData().equals(data)) {
                    return currentNode;
                }
                currentNode = currentNode.getNext();

            }
            return currentNode;


        }


        @Override
        public boolean contains(Object data) {
            Node currentNode = this.getFirst().getNext();
            while (currentNode != null) {

                if (((HashLinkedListNode) currentNode.getData()).getData().equals(data))
                    return true;

                currentNode = currentNode.getNext();

            }
            return false;
        }

        @Override
        public void add(Object data, String fileName, String wordPosition) {
        }

        @Override
        public void update(ArrayList wordsInFile, String filename) {


        ArrayList<String> wordsInFileData = this.wordArrayToStringArray(wordsInFile);
            ArrayList<String> wordsPositions = this.wordArrayToPositionArray(wordsInFile);
            ArrayList<String> wordsInLinkedList = this.traverse(new ArrayList<>());

            // check for deletation

            for (int i = 0; i < wordsInLinkedList.size(); i++) {
                HashLinkedListNode tempNode = (HashLinkedListNode) this.get(wordsInLinkedList.get(i)).getData();
                // if words is not this file do update
                if (!wordsInFileData.contains(wordsInLinkedList.get(i))) {
                    tempNode.removeFromFile(filename);
                    if ((tempNode.getListOfFiles().getSize()) == 0)
                        this.delete(wordsInLinkedList.get(i));
                }

                for (int j = 0; j < tempNode.getWordsPosition().size(); j++) {

                    if (!wordsPositions.contains(tempNode.getWordsPosition().get(i))) {


                        if (FileManager.getFileManagerSharedInstance().parsingWordPositionString((String)
                                tempNode.getWordsPosition().get(i)).get(0).equals(FileManager.getFileManagerSharedInstance().
                                parsingWordPositionString(wordsPositions.get(0)).get(0))) {
                            tempNode.removeFromWordPosition(j);
                            j--;
                        }

                    } else if (!tempNode.getData().equals(wordsInFileData.get(wordsPositions.indexOf(tempNode.getWordsPosition().get(i))))) {

                        tempNode.removeFromWordPosition(j);
                        j--;
                    }


                }
            }

            for (int i = 0; i < wordsInFileData.size(); i++) {

                HashLinkedListNode tempNode = (HashLinkedListNode) this.get(wordsInFileData.get(i)).getData();

                if (tempNode == null || !wordsInLinkedList.contains(wordsInFileData.get(i))) {

                    this.add(new HashLinkedListNode<>(wordsInFileData.get(i), filename, wordsPositions.get(i)));
                } else {

                    tempNode.addToFiles(filename);
                }

                if (tempNode != null && !tempNode.getWordsPosition().contains(wordsPositions.get(i))) {

                    tempNode.addToWordsPosition(wordsPositions.get(i));
                }

            }


        }

        public ArrayList<String> wordArrayToStringArray(ArrayList<Word> wordArray) {
            ArrayList<String> stringArray = new ArrayList<>();

            for (Word word : wordArray)
                stringArray.add(word.getData());

            return stringArray;
        }

        public ArrayList<String> wordArrayToPositionArray(ArrayList<Word> wordArray) {

            ArrayList<String> positionArray = new ArrayList<>();

            for (Word word : wordArray) {

                positionArray.add(FileManager.getFileManagerSharedInstance().
                        filePostitionStringGenerator(word.getFileNumber(), word.getWordNumber()));
            }
            return positionArray;


        }


    }


}

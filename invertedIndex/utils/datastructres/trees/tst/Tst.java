package invertedIndex.utils.datastructres.trees.tst;

import groovy.util.IFileNameFinder;
import invertedIndex.Word;
import invertedIndex.managers.FileManager;
import invertedIndex.utils.datastructres.DataStructreNode;
import invertedIndex.utils.datastructres.trees.Tree;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sina on 11/29/16.
 *
 *
 *
 *  TODO :  should make this avl ( balance weight tree );
 *
 * TODO : complete and `
 *
 */

public class Tst<T> extends Tree<T> {

    protected Node root;
    // constructor :

    public Tst() {

        this.root = null;
    }

    // check whether the tree is empty or not

    public boolean isEmpty() {
        return root == null;
    }

    //making the tree empty
    public void makeEmpty() {
        this.root = null;
    }


    @Override
    public void add(T data) {
        if (data.getClass().equals(Word.class)) {

            this.add((T) ((Word) data).getData(), (String) ((Word) data).getListOfFiles().get(0).getData(),
                    FileManager.getFileManagerSharedInstance().filePostitionStringGenerator(((Word) data).getFileNumber(),
                            ((Word) data).getWordNumber()));
        }
    }
    // adding a string and its fileanme to the tree
    // public abstract void add(Object data , String fileName);
    public void add(Object data, String fileName , String wordPosition) {
        // TODO

        this.root = this.add(root, ((String) data).toCharArray(), 0, fileName, wordPosition);
    }

    @Override
    public void update(ArrayList wordsInFile, String filename) {
        {

            ArrayList<String> wordsInTree = new ArrayList<>();
            ArrayList<String> wordsDataInFile;
            ArrayList<String> wordsPositions ;
            wordsInTree = this.traverse(root, "", wordsInTree);
            wordsDataInFile = this.wordArrayToStringArray(wordsInFile);
            wordsPositions = this.wordArrayToPositionArray(wordsInFile);

            for (String word : wordsInTree) {
                Node tempNode = (Node) this.get(word);
                if (!wordsDataInFile.contains(word)) {
                    tempNode.removeFromFile(filename);
                }

                if (tempNode.getListOfFiles().getSize() == 0 || tempNode.getListOfFiles() == null)
                    this.delete(word);
                else
                    for  (int i =0 ; i< tempNode.getWordsPosition().size() ; i++ ){

                        if ( !wordsPositions.contains(tempNode.getWordsPosition().get(i))) {
                            if ( FileManager.getFileManagerSharedInstance().parsingWordPositionString(
                                    (String)tempNode.getWordsPosition().get(i)).get(0).equals(FileManager.getFileManagerSharedInstance().
                                    parsingWordPositionString(wordsPositions.get(0)).get(0))) {
                                tempNode.removeFromWordPosition(i);
                                i--;
                            }
                        }else if ( ! tempNode.getData().equals(wordsDataInFile.get(wordsPositions.indexOf(tempNode.getWordsPosition().get(i))))){
//                            System.out.println("we are removing position   " + tempNode.getWordsPosition().get(i) + "   with the value of  " + tempNode.getData());
                            tempNode.removeFromWordPosition(i);
                            i--;
                        }






                    }
            }
            for ( int i =0 ; i< wordsDataInFile.size() ; i++ ){
                Node tempNode = (Node) this.get(wordsDataInFile.get(i));

                if (tempNode == null || !wordsInTree.contains(wordsDataInFile.get(i))) {


                    this.add(wordsDataInFile.get(i), filename , wordsPositions.get(i) );

                } else
                    this.get(wordsDataInFile.get(i)).addToFiles(filename);
                if ( tempNode != null && ! tempNode.getWordsPosition().contains(wordsPositions.get(i))) {
                    tempNode.addToWordsPosition(wordsPositions.get(i));
                }


            }


        }

    }




    // adding a char[] of word to the tree
    public Node add(Node node, char[] word, int counter, String fileName, String wordPosition ) {

        if (node == null) {
            node = new Node(word[counter], false);

        }


        if (node.compareTo(word[counter]) < 0) {


            node.setLeftChild(add(node.getLeftChild(), word, counter, fileName, wordPosition));

        } else if (node.compareTo(word[counter]) > 0) {

            node.setRightChild(add(node.getRightChild(), word, counter, fileName, wordPosition));


        } else {


            if (counter + 1 < word.length) {
                node.setMiddleChild(add(node.getMiddleChild(), word, counter + 1, fileName, wordPosition));
            }
            else {
                node.setEndOfWord(true);
                node.addToFiles(fileName);
                node.addToWordsPosition(wordPosition);
//                counter++;
                this.sizeIncrementer();
            }
        }
        return node;


    }
// print the tree inorder

    public String toString() {
        ArrayList<String> arr = new ArrayList<>();

        arr = traverse(root, "", arr);
        return "\nTernary Search Tree : " + arr;
    }


    @Override
    public ArrayList<String> traverse(ArrayList array) {

        return this.traverse(root, "", array);

    }

    @Override
    public int getHeight() {
        if (this.isEmpty())
            return 0;
        return this.getHeight(root);


    }




    private int getHeight(Node node) {
        int nodeHeight = 0, leftHeight = 0, rightHeight = 0, treeHight = 0;

        if (node.getLeftChild() != null)
            leftHeight = this.getHeight(node.getLeftChild());
        if (node.getRightChild() != null)
            rightHeight = this.getHeight(node.getRightChild());
        if (node.getMiddleChild() != null)
            nodeHeight = this.getHeight(node.getMiddleChild());

        if (nodeHeight >= leftHeight && nodeHeight >= rightHeight)
            return nodeHeight + 1;
        else if (leftHeight >= nodeHeight && leftHeight >= rightHeight)
            return leftHeight + 1;
        else
            return rightHeight + 1;
    }

    //     function to traverse tree (in order)
    private ArrayList<String> traverse(Node node, String str, ArrayList<String> words) {

        if (node != null) {
            words = traverse(node.getLeftChild(), str, words);

            str = str + node.getData();
            if (node.getEndOfWord() && !words.contains(str)) {
                words.add(str);
            }

            words = traverse(node.getMiddleChild(), str, words);
            str = str.substring(0, str.length() - 1);

            words = traverse(node.getRightChild(), str, words);
        }
        return words;
    }

    // delete a nod in the tree

    public boolean delete(Object word) {
        // TODO :
            if ( root == null)
                return  false;

            this.root = delete(root , ((String)word).toCharArray(), 0 ,root  );

            if ( root == null && this.getSize() == 0 || root != null)
                return true;
        else return  false;

    }


        protected Node delete(Node node, char[] word, int ptr, Node parent) {

            if ( node == null)
                return null;
            if ( node.compareTo(word[ptr]) < 0){
                node.setLeftChild( this.delete(node.getLeftChild() , word , ptr , node));
            }else if (node.compareTo(word[ptr]) >0)
            {
                node.setRightChild(this.delete(node.getRightChild() , word , ptr , node ));
            }else {

                if ( node.getMiddleChild() != null){


                    node.setMiddleChild(this.delete(node.getMiddleChild() , word , ptr + 1 , node ));
                    if ( node.getMiddleChild() == null)
                    {
                        this.delete(node , word , ptr , node );
                    }

                }else {
                    // should delete it like what we do for bst

                    // no child :
                    if ( node.getLeftChild() == null && node.getRightChild() == null){

                        return null;
                        // only left child
                    }else if ( node.getLeftChild() != null && node.getRightChild() == null){

                        return node.getLeftChild();
                        // only right child
                    }else if ( node.getRightChild() != null && node.getLeftChild() == null){

                        return node.getRightChild();

                     // has both right child and left child
                    }else {

                        Node temp = this.findMin(node.getRightChild());
                        node.setData(temp.getData());
                        node.setEndOfWord(temp.getEndOfWord());
                        node.setMiddleChild(temp.getMiddleChild());
                        String tempWord = "" ;

                        while ( temp != null){
                            tempWord = tempWord + temp.getData().toString();
                            temp = temp.getMiddleChild();
                        }

                        node.setRightChild(this.delete(node.getRightChild(), tempWord.toCharArray() , 0 , node ));
                        return node;
                    }





                }




            }

            return   node;
        }

    // deleting char [] form tree -> making its end of word to false;
//    protected boolean delete(Node node, char[] word, int ptr, Node parent) {
//        // TODO :
//
//        if (node == null)
//            return false;
//        if (node.compareTo(word[ptr]) < 0) {
//            return this.delete(node.getLeftChild(), word, ptr, node);
//        } else if (node.compareTo(word[ptr]) > 0) {
//
//            return this.delete(node.getRightChild(), word, ptr, node);
//
//        } else {
//
//            // to delete a word from tree we can only make the is end of the word to false
//
//            if (node.getEndOfWord() && ptr == word.length - 1) {
//
//                node.setEndOfWord(false);
//                node.makeListOfFilesEmpty();
//                node.makeWordPositionsEmpty();
////                // if there is no children
////                if ( node.getLeftChild() == null && node.getRightChild() == null && node.getMiddleChild() == null){
////                    node = null;
////                }
////                //only middle child
////
////                else if ( node.getLeftChild() == null && node.getRightChild() == null && node.getMiddleChild() != null){
////
////
////
////                }else if ( node.getLeftChild() != null && node.getRightChild() == null && node.getMiddleChild() == null){
////
////
////
////
////                }else if ( node.getLeftChild() == null && node.getRightChild() != null && node.getMiddleChild() == null){
////
////
////
////                }
////                ptr --;
////                Node tempNode = node;
////                while (tempNode.getParentNode().compareTo(word[ptr]) == 0){
////                   node.getParentNode().setMiddleChild(null);
////                    if(  node.getRightChild() != null || node.getLeftChild() != null){
////                        if( node.getLeftChild() != null) {
////                            node.getParentNode().setMiddleChild(node.getLeftChild());
////                            // todo setting the rightest left childeren to middle childeren
////                        }
////
////
////                    }
////                    node = node.getParentNode();
////                    tempNode = node;
////                    ptr --;
////
////                }
//                return true;
//
//            } else if (ptr + 1 < word.length)
//                return this.delete(node.getMiddleChild(), word, ptr + 1, node);
//            return false;
//
//        }
//    }



    // getting tree's node with specific string content

    public DataStructreNode get(Object word) {


        return this.get(root, ((String) word).toCharArray(), 0);
    }


    // getting tree's node with specific char [] content
    private Node get(Node node, char[] word, int ptr) {


        {


            if (node == null || ptr >= word.length)
                return null;


            if (node.compareTo(word[ptr]) > 0) {

                return this.get(node.getRightChild(), word, ptr);


            } else if (node.compareTo(word[ptr]) < 0) {

                return this.get(node.getLeftChild(), word, ptr);


            } else {

                if (ptr == word.length - 1 && node.getEndOfWord())
                    return node;

                else
                    return this.get(node.getMiddleChild(), word, ptr + 1);


            }


        }
    }

    protected Node findMin(Node current) {

        while (current.getLeftChild() != null)
            current = current.getLeftChild();

        return current;
    }


    // whether tree has that node or not
    public boolean contains(Object word) {

        return contains(root, ((String) word).toCharArray(), 0);
    }


    private boolean contains(Node node, char[] word, int ptr) {


        if (node == null || ptr >= word.length)
            return false;


        if (node.compareTo(word[ptr]) > 0) {

            return this.contains(node.getRightChild(), word, ptr);


        } else if (node.compareTo(word[ptr]) < 0) {

            return this.contains(node.getLeftChild(), word, ptr);


        } else {
//            if (ptr == word.length - 1 && node.getEndOfWord() == true)
//                return true;
//
//            else
//                return this.contains(node.getMiddleChild(), word, ptr + 1);
            // Check

            return ptr == word.length - 1 && node.getEndOfWord() || this.contains(node.getMiddleChild(), word, ptr + 1);


        }


    }
    // updating the tree






    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        /* Creating object of TernarySearchTree */
        Tst tst = new Tst();
        System.out.println("Ternary Search Tree Test\n");

        char ch;
        /*  Perform tree operations  */
        do {
            System.out.println("\nTernary Search Tree Operations\n");
            System.out.println("1. insert word");
            System.out.println("2. search word");
            System.out.println("3. delete word");
            System.out.println("4. check empty");
            System.out.println("5. make empty");

            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter word to insert");
                    tst.add(scan.next(), scan.next(), FileManager.getFileManagerSharedInstance().filePostitionStringGenerator(1,1));
                    break;
                case 2:
                    System.out.println("Enter word to search");
//                    System.out.println("Search result : "+ tst.search( scan.next() ));
                    break;
                case 3:
                    System.out.println("Enter word to delete");
                    System.out.println("" + tst.delete(scan.next()));

                    break;
                case 4:
                    System.out.println("Empty Status : " + tst.isEmpty());
                    break;
                case 5:
                    System.out.println("Ternary Search Tree cleared");
                    tst.makeEmpty();
                    break;
                case 6:
                    System.out.println("size of the tree is ");
                    System.out.println(tst.getSize());
                    break;
                case 7:
                    System.out.println("search  of the tree is ");

                    System.out.println(tst.contains(scan.next()));
                    break;
                case 8:
                    System.out.println("get  of the tree is ");
                    System.out.println(tst.get(scan.next()));
                    break;

                case 9:
                    System.out.println("update   the tree is ");
                    ArrayList<String> word = new ArrayList();
                    word.add("mohammad");
                    word.add("ali");
                    // TODO :
//                    tst.update(word, scan.next());
                    System.out.println(tst.toString());
                    break;

                case 10:

                    System.out.println("getting files");
                    System.out.println(tst.get(scan.next()).getListOfFiles().toString());

                    break;

                case 11:
                    System.out.println("height is ");
                    System.out.println(tst.getHeight());
                    break;
                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            System.out.println(tst);
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }

}

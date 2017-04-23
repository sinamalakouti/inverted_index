package invertedIndex.utils.datastructres.trees.bst;

import invertedIndex.Word;
import invertedIndex.managers.FileManager;
import invertedIndex.utils.datastructres.DataStructreNode;
import invertedIndex.utils.datastructres.trees.Tree;

import java.util.ArrayList;

/**
 * Created by sina on 11/29/16.
 * <p>
 * bst tree
 * <p>
 * node > left chile && node < right child
 */
public class BstTree<T> extends Tree<T> {

    protected Node<T> root;

    /**
     * Initializes an empty symbol table.
     * <p>
     * // TODO :  should make this avl ( balance weight tree );
     */
    public BstTree() {
        this.root = null;

    }

    // TODO :  making tree from an array of words
    public BstTree(ArrayList arrayOfNodes){


    }

    public BstTree(Node<T> root) {

        this.root = root;

    }


    public boolean isEmpty() {
        // Returns true if this symbol table is empty.
        return this.getSize() == 0 || this.root == null;
    }

    public void makeEmpty() {
        this.root = null;
        super.setSize(0);
    }


    public boolean contains(T data) {

        Node currentNode = this.root;
        while (currentNode != null) {

            if (currentNode.compareTo(data) == 0) {
                return true;

            } else if (currentNode.compareTo(data) > 0)
                currentNode = currentNode.getRightChild();

            else if (currentNode.compareTo(data) < 0)
                currentNode = currentNode.getLeftChild();

        }

        return false;
    }

    @Override
    public void add(T data) {
        if (data.getClass().equals(Word.class)) {

            this.add((T) ((Word) data).getData(), (String) ((Word) data).getListOfFiles().get(0).getData(),
                    FileManager.getFileManagerSharedInstance().filePostitionStringGenerator(((Word) data).getFileNumber(),
                            ((Word) data).getWordNumber()));
        }else {

            this.add(data , "" , "");

        }
    }

    @Override
    public DataStructreNode<T> get(T data) {

        Node currentNode = this.root;
        while (currentNode != null) {

            if (currentNode.compareTo(data) == 0) {
                return currentNode;

            } else if (currentNode.compareTo(data) > 0)
                currentNode = currentNode.getRightChild();

            else if (currentNode.compareTo(data) < 0)
                currentNode = currentNode.getLeftChild();

        }
        return null;
    }

    public void add(T data, String fileName, String wordPosition) {



        if (this.root == null) {

            root = new Node(data);
            root.addToFiles(fileName);
            root.addToWordsPosition(wordPosition);
            this.sizeIncrementer();
        } else {
            if (root.compareTo(data) > 0)
                root.setRightChild(this.add(data, root.getRightChild(), fileName, wordPosition));
            else if (root.compareTo(data) < 0)
                root.setLeftChild(this.add(data, root.getLeftChild(), fileName, wordPosition));
            else {

                Node node = (Node) this.get((T) data);
                node.addToFiles(fileName);
                node.addToWordsPosition(wordPosition);

            }

        }


    }

    @Override
    public void update(ArrayList wordsInFile, String filename) {
        {
            // TODO : handling remove of words position
            ArrayList<String> wordsInTree = new ArrayList<>();
            ArrayList<String> wordsDataInFile = this.wordArrayToStringArray(wordsInFile);
            ArrayList<String> wordsPosition = this.wordArrayToPositionArray(wordsInFile);
            wordsInTree = this.traverse(root, wordsInTree);
            for (String word : wordsInTree) {
                Node<T> tempNode = (Node) this.get((T) word);
// TODo : file
                if (!wordsDataInFile.contains(word)) {

                    tempNode.removeFromFile(filename);
                    if (((Node) this.get((T) word)).getListOfFiles().getSize() == 0)
                        this.delete((T) word);
                }
                for (int i = 0; i < tempNode.getWordsPosition().size(); i++) {


                    if (!wordsPosition.contains(tempNode.getWordsPosition().get(i))) {

                        if (FileManager.getFileManagerSharedInstance().parsingWordPositionString((String) tempNode.getWordsPosition().get(i)).get(0).equals(FileManager.getFileManagerSharedInstance().parsingWordPositionString(wordsPosition.get(0)).get(0))) {
                            tempNode.removeFromWordPosition(i);
                            i--;
                        }
                    } else if (!tempNode.getData().equals(wordsDataInFile.get(wordsPosition.indexOf(tempNode.getWordsPosition().get(i))))) {

                        tempNode.removeFromWordPosition(i);
                        i--;
                    }


                }

            }


            for (int i = 0; i < wordsDataInFile.size(); i++) {
                Node<T> tempNode = (Node<T>) this.get((T) wordsDataInFile.get(i));

                if (tempNode == null || !wordsInTree.contains(wordsDataInFile.get(i))) {

                    this.add((T) wordsDataInFile.get(i), filename, wordsPosition.get(i));

                } else {
                    tempNode.addToFiles(filename);
//
                }

                if (tempNode != null && !tempNode.getWordsPosition().contains(wordsPosition.get(i))) {
//
                    tempNode.addToWordsPosition(wordsPosition.get(i));
                }
            }


        }
    }

    // adding a new record to the tree
    public Node add(T data, Node currentNode, String fileName, String wordPosition) {


        Node tempNode = new Node(data);

        if (currentNode == null) {
            this.sizeIncrementer();
            tempNode.addToFiles(fileName);
//            System.out.println("we are adding position3    " + wordPosition+ "   to the word with data   " + tempNode.getData());
            tempNode.addToWordsPosition(wordPosition);
            return tempNode;
        } else if (currentNode.compareTo(data) > 0)
            currentNode.setRightChild(this.add(data, currentNode.getRightChild(), fileName, wordPosition));
        else if (currentNode.compareTo(data) < 0)
            currentNode.setLeftChild(this.add(data, currentNode.getLeftChild(), fileName, wordPosition));
        else {
            currentNode.addToFiles(fileName);
            currentNode.addToWordsPosition(wordPosition);
        }
        return currentNode;


    }


    private T findMin(Node<T> current) {

        while (current.getLeftChild() != null)
            current = current.getLeftChild();

        return (T) current.getData();
    }

    public boolean delete(T word) {


        if (this.root == null)
            return false;
        this.root = this.delete((T) word, this.root);
        System.out.println("size is \t" + this.getSize());
        if ( root == null && this.getSize() == 0 || root  != null)
            return  true;
        else return false;
    }


    protected Node delete(T data, Node currentNode) {

        if (currentNode == null)
            return null;

        if (currentNode.compareTo(data) > 0) {

            currentNode.setRightChild(this.delete(data, currentNode.getRightChild()));
        } else if (currentNode.compareTo(data) < 0)
            currentNode.setLeftChild(this.delete(data, currentNode.getLeftChild()));
        else {
            // no child
            if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {

                currentNode = null;


                // only left child
            } else if (currentNode.getLeftChild() != null && currentNode.getRightChild() == null) {
                currentNode = currentNode.getLeftChild();


                // only have right child
            } else if (currentNode.getLeftChild() == null && currentNode.getRightChild() != null) {
                currentNode = currentNode.getRightChild();


                // have two childeren
            } else {

                // setting the nodes's data the minimum  data  of its right sub tree then will delete the node of right subtree with that data
                currentNode.setData(this.findMin(currentNode.getRightChild()));
                currentNode.setRightChild(this.delete((T) currentNode.getData(), currentNode.getRightChild()));
            }
            this.sizeDecrementer();
        }
        return currentNode;
    }


    @Override
    public ArrayList<String> traverse(ArrayList array) {

        return this.traverse(root, array);
    }

    @Override
    public int getHeight() {
        if (this.isEmpty())
            return 0;
        return getHeight(root);

    }

    private int getHeight(Node node) {
        int rightHeight = 0, leftHeight = 0;
        if (node.getRightChild() != null)
            rightHeight = this.getHeight(node.getRightChild());
        if (node.getLeftChild() != null)
            leftHeight = this.getHeight(node.getLeftChild());

        if (rightHeight > leftHeight)
            return rightHeight + 1;
        else
            return leftHeight + 1;


    }


    protected ArrayList<String> traverse(Node node, ArrayList<String> array) {


        // inOrder -> l v r


        if (node.getLeftChild() != null) {

            array = this.traverse(node.getLeftChild(), array);

        }

        array.add((String) node.getData());
//        System.out.println("data is \t " +    node.getData() +"\tposition is \t" + node.getWordsPosition());


        if (node.getRightChild() != null)
            array = this.traverse(node.getRightChild(), array);

        return array;

    }

    @Override
    public String toString() {

        ArrayList<String> wordsInTree = new ArrayList<>();
        wordsInTree = this.traverse(root, wordsInTree);

        return wordsInTree.toString();
//        return "";
    }
    // function which rotate tree to left -> used for node with right child


    public static void main(String[] args) {
        BstTree bstTree = new BstTree();

        bstTree.add("sina", "1", "12,3");
        bstTree.add("sina", "1", "23,4");
        bstTree.add("sina", "1", "12,3");
        bstTree.add("ali", "1", "12,3");
        System.out.println(bstTree.get("sina").getListOfFiles().getSize());
        System.out.println(bstTree.get("sina").getWordsPosition().size());
        System.out.println(bstTree.delete("sina"));


    }


}

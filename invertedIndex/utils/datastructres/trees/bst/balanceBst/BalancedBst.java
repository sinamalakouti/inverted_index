package invertedIndex.utils.datastructres.trees.bst.balanceBst;

import invertedIndex.Word;
import invertedIndex.managers.FileManager;
import invertedIndex.utils.datastructres.trees.bst.BstTree;
import invertedIndex.utils.datastructres.trees.bst.Node;

import java.util.ArrayList;

/**
 * Created by sina on 1/10/17.
 */
public class BalancedBst<T> extends BstTree<T> {

//        BalancedNode root ;
    public BalancedBst() {
        super();
    }

    public BalancedBst(ArrayList arrayOfNodes) {

        super(arrayOfNodes);
    }

    public BalancedBst(BalancedNode<T> root) {
        super(root);
    }



    @Override
        public void add(T data) {
//            System.out.println("we are adding to avl");

            if (data.getClass().equals(Word.class)) {

                this.add((T) ((Word) data).getData(), (String) ((Word) data).getListOfFiles().get(0).getData(),
                        FileManager.getFileManagerSharedInstance().filePostitionStringGenerator(((Word) data).getFileNumber(),
                                ((Word) data).getWordNumber()));
            }else {


                this.add(data, "" , "");
            }
        }

    @Override
    public Node add(T data, Node currentNode, String fileName, String wordPosition) {
//        System.out.println("we are adding to avl");


        BalancedNode tempNode = new BalancedNode(data);

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
        ((BalancedNode) currentNode).setHeight(1 + max(gettingHeightOfNode((BalancedNode) currentNode.getLeftChild()),
                gettingHeightOfNode((BalancedNode) currentNode.getRightChild())));
        int balance = getBalanceFactor((BalancedNode) currentNode);

        // checking left left case :

        if (balance >= 2 && currentNode.getLeftChild().compareTo(data) < 0) {
            return right_rotation((BalancedNode) currentNode);

            // right right case :
        } else if (balance <= -2 && currentNode.getRightChild().compareTo(data) > 0) {

            return left_rotation((BalancedNode) currentNode);

            // left right case :
        } else if (balance >= 2 && currentNode.getLeftChild().compareTo(data) > 0) {
            currentNode.setLeftChild(left_rotation((BalancedNode) currentNode.getLeftChild()));
            return right_rotation((BalancedNode) currentNode);
            // right left case :
        } else if (balance <= -2 && currentNode.getRightChild().compareTo(data) < 0) {

            currentNode.setRightChild((BalancedNode) currentNode.getRightChild());
            return left_rotation((BalancedNode) currentNode);
        }
        return currentNode;

    }


    @Override
    public void add(T data, String fileName, String wordPosition) {
//        System.out.println("we are adding to avl");

        if (this.root == null) {

            root = new BalancedNode(data);
            root.addToFiles(fileName);
            root.addToWordsPosition(wordPosition);
            this.sizeIncrementer();
        } else {
            if (root.compareTo(data) > 0)
                root.setRightChild(this.add(data, root.getRightChild(), fileName, wordPosition));
            else if (root.compareTo(data) < 0)
                root.setLeftChild(this.add(data, root.getLeftChild(), fileName, wordPosition));
            else {

                Node node = (Node) this.get( data);
                node.addToFiles(fileName);
                node.addToWordsPosition(wordPosition);

            }

        }

        ((BalancedNode) root).setHeight(1 + max(gettingHeightOfNode((BalancedNode) root.getLeftChild()),
                gettingHeightOfNode((BalancedNode) root.getRightChild())));
        int balance = getBalanceFactor((BalancedNode) root);


        // checking left left case :

        if (balance >= 2 && root.getLeftChild().compareTo(data) < 0) {
            root = right_rotation((BalancedNode) root);

            // right right case :
        } else if (balance <= -2 && root.getRightChild().compareTo(data) > 0) {

            root = left_rotation((BalancedNode) root);

            // left right case :
        } else if (balance >= 2 && root.getLeftChild().compareTo(data) > 0) {
            root.setLeftChild(left_rotation((BalancedNode) root.getLeftChild()));
            root = right_rotation((BalancedNode) root);
            // right left case :
        } else if (balance <= -2 && root.getRightChild().compareTo(data) < 0) {

            root.setRightChild(right_rotation((BalancedNode) root.getRightChild()));
            root = left_rotation((BalancedNode) root);
        }


    }

    @Override
    public boolean delete(T word) {
        boolean isDeletionOk ;
         isDeletionOk =  super.delete(word);

        // now we should go for the avl part ;
        ((BalancedNode)root).setHeight(max(gettingHeightOfNode((BalancedNode)root.getLeftChild()), gettingHeightOfNode((BalancedNode)root.getRightChild()) +1));

        int balance = getBalanceFactor((BalancedNode) root);


        // left left case  :
        if( balance >= 2 && root.getLeftChild().compareTo(word)  < 0  ){

            this. root = right_rotation((BalancedNode) root);

            // right right case :
        }else if (balance <= -2  &&  getBalanceFactor((BalancedNode)root.getRightChild()) <= 0 ){

            this.root = this.left_rotation((BalancedNode) root);


            // left right case :
        }else if (balance >=2 && root.getRightChild().compareTo(word) >0){

            root.setLeftChild(this.left_rotation((BalancedNode)root.getLeftChild()));
            this.root = this.right_rotation((BalancedNode) root);


            // case right left case :
        }else if (balance <= -2 && root.getLeftChild().compareTo(word) <0 ){

            this.root.setRightChild(this.right_rotation((BalancedNode) root.getRightChild()));
            this.root = this.left_rotation((BalancedNode) root);
        }




    return isDeletionOk;
    }

    @Override
    protected Node delete(T data, Node currentNode) {
         currentNode = super.delete(data, currentNode);

        ((BalancedNode)currentNode).setHeight(max(gettingHeightOfNode((BalancedNode)currentNode.getLeftChild()), gettingHeightOfNode((BalancedNode)currentNode.getRightChild()) +1));

        int balance = getBalanceFactor((BalancedNode) currentNode);


        // left left case  :
        if( balance >= 2 && currentNode.getLeftChild().compareTo(currentNode)  < 0  ){

            currentNode = right_rotation((BalancedNode) currentNode);

            // right right case :
        }else if (balance <= -2  &&  getBalanceFactor((BalancedNode)currentNode.getRightChild()) <= 0 ){

            currentNode = this.left_rotation((BalancedNode) currentNode);


            // left right case :
        }else if (balance >=2 && currentNode.getRightChild().compareTo(data) >0){

            currentNode.setLeftChild(this.left_rotation((BalancedNode)currentNode.getLeftChild()));
            currentNode = this.right_rotation((BalancedNode) currentNode);


            // case right left case :
        }else if (balance <= -2 && currentNode.getLeftChild().compareTo(data) <0 ){

            currentNode.setRightChild(this.right_rotation((BalancedNode) currentNode.getRightChild()));
            currentNode= this.left_rotation((BalancedNode) currentNode);
        }

        return currentNode;


    }

    private int max(int a, int b) {

        return a > b ? a : b;
    }

    private int gettingHeightOfNode(BalancedNode node) {

        if (node == null)
            return 0;
        return node.getHeight();

    }

    private BalancedNode right_rotation(BalancedNode node) {

        BalancedNode nodeLeft = (BalancedNode) node.getLeftChild();
        BalancedNode temp = (BalancedNode) nodeLeft.getRightChild();

        // Perform rotation
        nodeLeft.setRightChild(node);
        node.setLeftChild(temp);

        // Update heights
        node.setHeight(max(gettingHeightOfNode((BalancedNode) node.getLeftChild()), gettingHeightOfNode((BalancedNode) node.getRightChild())) + 1);
        nodeLeft.setHeight(max(gettingHeightOfNode((BalancedNode) nodeLeft.getLeftChild()), gettingHeightOfNode((BalancedNode) nodeLeft.getRightChild())) + 1);

        // Return new root
        return nodeLeft;

    }

    private BalancedNode left_rotation(BalancedNode node) {

        BalancedNode nodeRight = (BalancedNode) node.getRightChild();
        BalancedNode temp = (BalancedNode) nodeRight.getLeftChild();

        // Perform rotation
        nodeRight.setLeftChild(node);
        node.setRightChild(temp);

        //  Update heights
        node.setHeight(max(gettingHeightOfNode((BalancedNode) node.getLeftChild()), gettingHeightOfNode((BalancedNode) node.getRightChild())) + 1);
        nodeRight.setHeight(max(gettingHeightOfNode((BalancedNode) nodeRight.getLeftChild()), gettingHeightOfNode((BalancedNode) nodeRight.getRightChild())) + 1);

        // Return new root
        return nodeRight;

    }

    private int getBalanceFactor(BalancedNode node) {

        if (node == null)
            return 0;

        return gettingHeightOfNode((BalancedNode) node.getLeftChild()) - gettingHeightOfNode((BalancedNode) node.getRightChild());


    }

    @Override
    public String toString() {
        ArrayList<String> wordsInTree = new ArrayList<>();
        wordsInTree = this.traverse(root, wordsInTree);

        return wordsInTree.toString();    }

    public static void main(String[] args) {
        BalancedBst bst = new BalancedBst();
         Word word = new Word("20", 1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        bst.add(word);

        word = new Word("30", 1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        bst.add(word);

        word = new Word("25", 1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        bst.add(word);

        word = new Word("40", 1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        bst.add(word);

        word = new Word("50", 1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        bst.add(word);

        word = new Word("60", 1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        bst.add(word);

        word = new Word("0", 1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        bst.add(word);

        bst.delete("50");
        System.out.println(bst.toString());
    }
}

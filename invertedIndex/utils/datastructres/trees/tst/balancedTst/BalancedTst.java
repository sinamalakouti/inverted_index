package invertedIndex.utils.datastructres.trees.tst.balancedTst;

import invertedIndex.Word;
import invertedIndex.managers.FileManager;

import invertedIndex.utils.datastructres.trees.tst.Node;
import invertedIndex.utils.datastructres.trees.tst.Tst;

/**
 * Created by sina on 1/10/17.
 */
public class BalancedTst<T> extends Tst<T> {


    @Override
    public void add(T data) {
        if (data.getClass().equals(Word.class)) {

            this.add((T) ((Word) data).getData(), (String) ((Word) data).getListOfFiles().get(0).getData(),
                    FileManager.getFileManagerSharedInstance().filePostitionStringGenerator(((Word) data).getFileNumber(),
                            ((Word) data).getWordNumber()));
        }
    }

    @Override
    public void add(Object data, String fileName, String wordPosition) {

        this.root = this.add(root, ((String) data).toCharArray(), 0, fileName, wordPosition);

        ((BalancedNode)root).setHeight(max(gettingHeightOfNode((BalancedNode) root.getLeftChild()), gettingHeightOfNode((BalancedNode) root.getRightChild())));
        int balance = this.getBalanceFactor((BalancedNode) root);



        // left left case :
        if(balance >= 2 && root.getLeftChild().compareTo(  ((String) data).toCharArray()[0]   )  <0){

            root = right_rotation((BalancedNode)root);


            // right right case :
        }else if( balance <= -2 && root.getLeftChild().compareTo(  ((String) data).toCharArray()[0]   ) > 0){

            root = left_rotation((BalancedNode)root);


            // left right case :
        }else if(balance >= 2 && root.getLeftChild().compareTo(  ((String) data).toCharArray()[0]   )  >0 ){


            root.setLeftChild(left_rotation((BalancedNode)root.getLeftChild()));
            root = right_rotation((BalancedNode)root);
            // right left case
        }else if ( balance <= -2 && root.getLeftChild().compareTo(  ((String) data).toCharArray()[0]   )  <0){

            root.setRightChild(right_rotation((BalancedNode)root.getRightChild()));
            root = left_rotation((BalancedNode)root);


        }


    }

    @Override
    public Node add(Node node, char[] word, int counter, String fileName, String wordPosition) {


            if (node == null) {
                node = new BalancedNode(word[counter], false);

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
                   // counter++;
                    this.sizeIncrementer();
                }
            }
        // setting height
        ((BalancedNode) node).setHeight(max(gettingHeightOfNode((BalancedNode) node.getLeftChild()), gettingHeightOfNode((BalancedNode) node.getRightChild()))+1);

        int balance = this.getBalanceFactor((BalancedNode) node);

        // left left case :

        if(balance >= 2 && node.getLeftChild().compareTo(word[counter]) <0 ){

            return right_rotation((BalancedNode) node);



            // right right case :
        }else if ( balance <= -2  && node.getRightChild().compareTo(word[counter]) > 0 ){

            return  left_rotation((BalancedNode) node);

            // left right case :
        }else if( balance >= 2 && node.getLeftChild().compareTo(word[counter]) > 0  ){

            node.setLeftChild( this.left_rotation((BalancedNode) node.getLeftChild()));

            return right_rotation((BalancedNode) node);

            // right left case :
        }else if ( balance <= -2 && node.getRightChild().compareTo(word[counter]) < 0 ){

            node.setRightChild(right_rotation((BalancedNode) node.getRightChild()));
            return left_rotation((BalancedNode) node);


        }


            return node;


        }


    @Override
    public boolean delete(Object word) {
        // TODO :
        if ( root == null)
            return  false;

        this.root = delete(root , ((String)word).toCharArray(), 0 ,root  );
        char [] word1 = ((String) word).toCharArray();

        if ( root == null && this.getSize() == 0 || root != null)
            return true;
        else return  false;

//        ((BalancedNode) root).setHeight(max(gettingHeightOfNode((BalancedNode) root.getLeftChild()), gettingHeightOfNode((BalancedNode) root.getRightChild()))+1);
//
//        int balance = this.getBalanceFactor((BalancedNode) root);
//
//        // left left case :
//
//        if(balance >= 2 && root.getLeftChild().compareTo(word1[0]) <0 ){
//
//            root = right_rotation((BalancedNode) root);
//
//
//
//            // right right case :
//        }else if ( balance <= -2  && root.getRightChild().compareTo(word1[0]) > 0 ){
//
//            root =   left_rotation((BalancedNode) root);
//
//            // left right case :
//        }else if( balance >= 2 && root.getLeftChild().compareTo(word1[0]) > 0  ){
//
//            root.setLeftChild( this.left_rotation((BalancedNode) root.getLeftChild()));
//
//            root = right_rotation((BalancedNode) root);
//
//            // right left case :
//        }else if ( balance <= -2 && root.getRightChild().compareTo(word1[0]) < 0 ){
//
//            root.setRightChild(right_rotation((BalancedNode) root.getRightChild()));
//            root =  left_rotation((BalancedNode) root);
//
//
//        }

    }

    @Override
    protected Node delete(Node node, char[] word, int ptr, Node parent) {
        {

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

            ((BalancedNode) node).setHeight(max(gettingHeightOfNode((BalancedNode) node.getLeftChild()), gettingHeightOfNode((BalancedNode) node.getRightChild()))+1);

            int balance = this.getBalanceFactor((BalancedNode) node);

            // left left case :

            if(balance >= 2 && node.getLeftChild().compareTo(word[ptr]) <0 ){

                return right_rotation((BalancedNode) node);



                // right right case :
            }else if ( balance <= -2  && node.getRightChild().compareTo(word[ptr]) > 0 ){

                return  left_rotation((BalancedNode) node);

                // left right case :
            }else if( balance >= 2 && node.getLeftChild().compareTo(word[ptr]) > 0  ){

                node.setLeftChild( this.left_rotation((BalancedNode) node.getLeftChild()));

                return right_rotation((BalancedNode) node);

                // right left case :
            }else if ( balance <= -2 && node.getRightChild().compareTo(word[ptr]) < 0 ){

                node.setRightChild(right_rotation((BalancedNode) node.getRightChild()));
                return left_rotation((BalancedNode) node);


            }


            return   node;
        }

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
        return super.toString();
    }

    public static void main(String[] args) {
        BalancedTst tst = new BalancedTst();
        Word word = new Word("sina",1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        tst.add(word);
        word = new Word("shna",1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        tst.add(word);
        word = new Word("saaa",1);
        word.addToFiles("1");
        word.addToWordsPosition("1,2");
        tst.add(word);
        System.out.println(tst.toString());
    }

}

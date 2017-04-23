package invertedIndex.utils.datastructres.trees;

import invertedIndex.Word;
import invertedIndex.managers.FileManager;
import invertedIndex.utils.datastructres.DataStructre;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sina on 11/30/16.
 */
public abstract class Tree<T>  extends DataStructre<T>{

    private int size;

    public int getSize() {

        return size;
    }

    public void setSize(int size){
        this.size = size;
    }



    public void sizeIncrementer()
    {
        this.size++;

    }
    public void sizeDecrementer(){
        this.size--;
    }

     public abstract String  toString() ;

    public abstract boolean isEmpty() ;
    public abstract  boolean contains(T word) ;
//    TODO :  implement this method for tree
    public abstract void add(T data);
//    public abstract void add(T data , String fileName , String wordPosition) ;
    public abstract int getHeight();
    public int numberOfWords() {
        ArrayList<String> words = new ArrayList<>();
        words = this.traverse(words);
        return  words.size();

    }


    public ArrayList<String> wordArrayToStringArray(ArrayList<Word> wordArray){
        ArrayList<String> stringArray =new ArrayList<>();

        for ( Word word : wordArray){

            stringArray.add(word.getData());

        }

        return stringArray;

    }

    public ArrayList<String> wordArrayToPositionArray(ArrayList<Word> wordArray){

        ArrayList<String> positionArray = new ArrayList<>();

        for ( Word word : wordArray){

            positionArray.add(FileManager.getFileManagerSharedInstance().
                    filePostitionStringGenerator(word.getFileNumber()  , word.getWordNumber()));

        }

        return positionArray;




    }





    }


package invertedIndex.utils.datastructres;

import invertedIndex.Word;


import java.util.ArrayList;

/**
 * Created by sina on 12/28/16.
 */
public abstract class DataStructre<T> {
    public abstract void add(T data , String fileName , String wordPosition) ;
    public abstract  void update (ArrayList<Word> wordsInFile , String filename);
    public abstract boolean  delete(T word) ;
    public abstract DataStructreNode<T> get(T data);
    public abstract ArrayList<String>traverse(ArrayList<String> array );


}

package invertedIndex.utils.datastructres;

import invertedIndex.utils.datastructres.linkedList.LinkedList;

import java.util.ArrayList;

/**
 * Created by sina on 12/28/16.
 */
public class DataStructreNode <T>    {

    protected T data;
    protected LinkedList<String> listOfFiles;
    private ArrayList<String> wordsPosition;

    public void addToFiles(String fileName){
        if( listOfFiles == null)
            listOfFiles = new LinkedList();
        if (! this.listOfFiles.contains(fileName)){
            this.listOfFiles.add(fileName);
        }
    }

    public ArrayList<String> getWordsPosition() {
        return wordsPosition;
    }

    public void makeListOfFilesEmpty(){
        this.listOfFiles = null;
    }
    public void removeFromFile(String fileName){
        if( listOfFiles == null)
            return;

        this.listOfFiles.delete(fileName);
    }

    public LinkedList getListOfFiles() {
        return listOfFiles;
    }



    public void addToWordsPosition(String wordPosition){
        if (this.wordsPosition == null)
            this.wordsPosition = new ArrayList<>();
        if ( ! this.wordsPosition.contains(wordPosition))
            this.wordsPosition.add(wordPosition);
    }

    public void removeFromWordPosition(String wordPosition){
        if (this.wordsPosition != null)
            this.wordsPosition.remove(wordPosition);
    }
    public void removeFromWordPosition(int wordPositionIndex){
        if (this.wordsPosition != null)
            this.wordsPosition.remove(wordPositionIndex);
    }
    public void setListOfFiles(LinkedList listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    public T getData() {
        return data;
    }


    public void makeWordPositionsEmpty(){


        this.wordsPosition = null;
    }

}

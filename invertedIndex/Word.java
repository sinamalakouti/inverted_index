package invertedIndex;

import invertedIndex.utils.datastructres.linkedList.LinkedList;

import java.util.ArrayList;

/**
 * Created by sina on 12/11/16.
 */
public class Word {


    String data ;
    int fileNumber ;
    int wordNumber;
    LinkedList listOfFiles;
    ArrayList<String> wordsPosition;

    public Word(String data  , int wordNumber) {

        this.data = data;
//        this.lineNumber = lineNumber;
        this.wordNumber = wordNumber;

    }

    public String getData() {
        return data;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public int getWordNumber() {
        return wordNumber;
    }


    public void addToFiles(String fileName){
        if( listOfFiles == null)
            listOfFiles = new LinkedList();
        if (! this.listOfFiles.contains(fileName)){
            this.listOfFiles.add(fileName);
        }
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



    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void addToWordsPosition(String wordPosition){
        if (this.wordsPosition == null)
            this.wordsPosition = new ArrayList<>();

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
}

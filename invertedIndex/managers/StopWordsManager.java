package invertedIndex.managers;


 import invertedIndex.utils.datastructres.trees.bst.balanceBst.BalancedBst;
 import invertedIndex.utils.fileutils.Fileutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by sina on 11/29/16.
 *
 *
 * // this is the manager of stop words
 */
public class StopWordsManager {

    public static StopWordsManager stopWordsInstance;
    private BalancedBst<String> stopWords;


    public StopWordsManager() {
        stopWords = new BalancedBst<String>();

    }

    public static StopWordsManager getStopWordsInstance() {

        if (stopWordsInstance == null) {
            stopWordsInstance = new StopWordsManager();

        }
        return stopWordsInstance;

    }


    public void makeStopWordsFromFile(String filePath) {
        String line = null;
        BufferedReader b = Fileutils.getBufferReader
                (Fileutils.getFileFomPath(filePath));
        try {
            while ((line = b.readLine()) != null) {
                String[] str;
                str = line.split("\\s");
                for (int i = 0; i < str.length; i++)
                    this.stopWords.add(str[i].toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public BalancedBst getStopWords() {
        return stopWords;
    }
    public boolean checkStopWord(String str){

        if( this.stopWords.contains(str))
            return  true;
        else return false;

    }
}

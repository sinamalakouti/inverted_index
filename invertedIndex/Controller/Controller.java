package invertedIndex.Controller;

import invertedIndex.gui.ProgramFrame;
import invertedIndex.managers.StopWordsManager;

/**
 * Created by sina on 11/29/16.
 */
public class Controller {

    private static Controller controller;

    private Controller() {
        this.controller = this;
        StopWordsManager.getStopWordsInstance().makeStopWordsFromFile("/Users/sina/Downloads/StopWords.txt");
//        System.out.println("size of stop word is \t" + StopWordsManager.getStopWordsInstance().getStopWords().getSize());
        new ProgramFrame();

    }


    public static void main(String[] args) {
        new Controller();
//        System.out.println("C is \t" + StopWordsManager.getStopWordsInstance().getStopWords().getSize());
    }
}


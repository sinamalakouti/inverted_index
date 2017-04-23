package invertedIndex.utils.fileutils;


import invertedIndex.Word;
import invertedIndex.managers.FileManager;

import invertedIndex.managers.StopWordsManager;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sina on 11/29/16.
 */

// in this class :
public class Fileutils {


    // file
    File file;

    // this funciton make new file object with the path : filePath
    public static File getFileFomPath(String filePath) {

        return new File(filePath);

    }


    // returning a new buffer reader from file
    public static BufferedReader getBufferReader(File file) {

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            return new BufferedReader(new InputStreamReader(fileInputStream));
        } catch (FileNotFoundException e) {


        }
        return null;

    }

    // reading files recursively -> if it is directory then calling this method for every files in the directory unless add the file to the file array
    public static void readingFiles(File file) {

        if (!file.isDirectory()) {

            if (!FileManager.getFileManagerSharedInstance().getFiles().contains(file))
                FileManager.getFileManagerSharedInstance().addToFiles(file);
            else {


            }


        } else {

            FileManager.getFileManagerSharedInstance().setFolderPath(file.getAbsolutePath());
            for (File s : file.listFiles()) {
                readingFiles(s);
            }
        }
    }

    // this function read text and return an array of Words made of file's content
    public static ArrayList<Word> txtReader(File file) {

        ArrayList<Word> words = new ArrayList<>();
        BufferedReader b = getBufferReader(file);
        String delim = "[^a-zA-Z]+";
//        String delim = "\\W+";
//        String delim  ="[^a-zA-Z'.]+";
        String line = "";
        int wordNumber = 0;
        try {
            while ((line = b.readLine()) != null) {
                String str[];
                str = line.split(delim);
                for (int i = 0; i < str.length; i++) {

                    if (StopWordsManager.getStopWordsInstance().checkStopWord(str[i].toLowerCase())) {
                        wordNumber++;
                        continue;
                    }
                    if (str[i].compareTo("") == 0)
                        continue;
//
                    Word word = new Word(str[i].toLowerCase(), wordNumber);
                    word.addToFiles(Fileutils.gettingNameOfFile(file));
                    word.setFileNumber(FileManager.getFileManagerSharedInstance().getIndexOfExecutedFile(file.getAbsolutePath()));
                    words.add(word);
                    wordNumber++;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;


    }

    // this method read a text file but don't filter the words by stopWords
    public static ArrayList<String> readSentenceFromTxtFile(File file) {

        ArrayList<String> words = new ArrayList<>();
        BufferedReader b = getBufferReader(file);

        String delim = "[^a-zA-Z]+";
//        String delim = "\\W+";
        String line = "";


        try {
            while ((line = b.readLine()) != null) {
                String wordsInFile[];
                wordsInFile = line.split(delim);
                for (int i = 0; i < wordsInFile.length; i++) {
                    if (wordsInFile[i].compareTo("") == 0)
                        continue;
                    words.add(wordsInFile[i].toLowerCase());

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        };
        return words;


    }

    // This method : get a file make a fileInputStream and then returns its bufferReader
    public static String gettingNameOfFile(File file) {
        String[] str;
        str = file.getAbsolutePath().split("\\/");


        return str[str.length - 1];
    }

    public File getFile() {

        return file;
    }

    public void setFile(String filePath) {
        this.file = new File(filePath);
    }


    // this function is for making a chose file frame for user in user_interface
    public static String chooseFile(Component parent, String directory) {

        JFileChooser fileChooser = new JFileChooser(directory);
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int result = fileChooser.showOpenDialog(parent);

        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                return fileChooser.getSelectedFile().getAbsolutePath();
            default:
                return null;
        }
    }

    // this method gives the root path of a file (does not include the name )
    public static String gettingTheRootPath(File file) {
        String[] str;
        String tmp = "";
        str = file.getAbsolutePath().split("/");
        for (int i = 0; i < str.length - 1; i++)
            tmp = str[i] + "/";


        return tmp;

    }


}

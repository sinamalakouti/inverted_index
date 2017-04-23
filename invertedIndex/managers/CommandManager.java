package invertedIndex.managers;


import invertedIndex.Word;
import invertedIndex.utils.Constants;
import invertedIndex.utils.datastructres.DataStructre;
import invertedIndex.utils.datastructres.DataStructreNode;
import invertedIndex.utils.datastructres.linkedList.LinkedList;
import invertedIndex.utils.datastructres.stack.Stack;
import invertedIndex.utils.datastructres.trees.Node;
import invertedIndex.utils.datastructres.trees.Tree;
import invertedIndex.utils.fileutils.Fileutils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sina on 12/9/16.
 * this class is a manager of commands that user can write; -> single tone
 */
public class CommandManager {

    Stack previousCommands;
    Stack nextCommands;
    static CommandManager commandManagerInstance;


    private CommandManager() {
        // stack  of the commands that user has written
        this.previousCommands = new Stack();
        this.nextCommands = new Stack();
    }

    // single tone
    public static CommandManager getSharedInstance() {
        if (commandManagerInstance == null)
            commandManagerInstance = new CommandManager();
        return commandManagerInstance;
    }

    // function that process commands and return the result of the process as a string
    public String commandProcessor(String[] strings) {

        // if the command is add
        if (strings[0].compareTo("add") == 0) {

            try {
                Fileutils.readingFiles(Fileutils.getFileFomPath(FileManager.getFileManagerSharedInstance().getFolderPath()));
            } catch (Exception e) {

                return "no folder chosed yet !";
            }

            if (FileManager.getFileManagerSharedInstance().allFilesContains(strings[1])) {

                if (!FileManager.getFileManagerSharedInstance().executedFilesContains(strings[1])) {

                    //processing the file
                    File tempFile = FileManager.getFileManagerSharedInstance().getAllFilesByName(strings[1]);
                    FileManager.getFileManagerSharedInstance().addToExecutedFiles(tempFile);
                    this.addCommand(tempFile);
                    return strings[1] + "  successfully added ";
                } else
                    return " err: already exists, you may want to update.";

            } else {

                return "err: document not found!";
            }

            // if the commend is delete a file
        } else if (strings[0].compareTo("del") == 0) {

            if (FileManager.getFileManagerSharedInstance().allFilesContains(strings[1])) {

                if (FileManager.getFileManagerSharedInstance().executedFilesContains(strings[1])) {

                    this.deleteCommand(FileManager.getFileManagerSharedInstance().getExecutedFileByName(strings[1]));
                    return strings[1] + "successfully removed from lists.";
                } else
                    return "err: This document has not processed yexxt!";
            } else
                return "err: document not found!";
            // if the command is to update a file
        } else if (strings[0].compareTo("update") == 0) {
            if (FileManager.getFileManagerSharedInstance().allFilesContains(strings[1])) {
                if (FileManager.getFileManagerSharedInstance().executedFilesContains(strings[1])) {
                    try {
                        this.updateCommand(FileManager.getFileManagerSharedInstance().getExecutedFileByName(strings[1]));
                        return strings[1] + "  successfully updated";
                    }catch (Exception e){

                         this.deleteCommand(FileManager.getFileManagerSharedInstance().getExecutedFileByName(strings[1]));
                        return "sucessfully updated";
                    }
                } else
                    return "err : this document is not processed!";
            } else
                return "err : document not exists!";



            // if the command is listing
        } else if (strings[0].compareTo("list") == 0) {

            switch (strings[1]) {

                // if it is listing the listed files
                case "-l":
                    return this.listOfExecutedFiles();

                // if it is listing all the words
                case "-w":

                    return this.listOfWordsCommand();

                // if it is listing all the documents

                case "-f":
                    return this.listOfAllDocs();


                default:
                    break;

            }
            // if the commend is searching
        } else if (strings[0].compareTo("search") == 0) {
            switch (strings[1]) {
                // if it is searching for a sentence
                case "-s":

                    String str = "";
                    for (int i = 2; i < strings.length; i++)
                        str = str + " " + strings[i];

                    return this.searchSentenceCommand(str);


                // if it is searching for a word
                case "-w":

                    String st = "";
                    String[] string = strings[2].split("[^a-zA-z]+");
                    for (int i = 0; i < string.length; i++)
                        st = st + string[i];
//                        return   ( (Node)Constants.tree.get(st)).getListOfFiles().toString() ;
                    try {

                        String str1 = "";
                        long start = System.currentTimeMillis();
                        str1=  ((Node) Constants.dataStructre.get(st.toLowerCase())).getListOfFiles().toString();
                        long elapsed = System.currentTimeMillis() - start;
                        System.out.println("time is \t" + elapsed);
                        return str1;
                    } catch (NullPointerException e1) {
                        return st + " does not exits!";
                    }
                default:
                    break;

            }
        }
        return "err: invalid command!";
    }

    // TODO : handle everything with class Word
    // this function add a new file's content to the tree
    public void addCommand(File file) {
        ArrayList<Word> words;
        words = Fileutils.txtReader(file);
        for (int i = 0; i < words.size(); i++) {

                Constants.dataStructre.add(words.get(i).getData(), Fileutils.gettingNameOfFile(file),
                        FileManager.getFileManagerSharedInstance().filePostitionStringGenerator
                                (words.get(i).getFileNumber(), words.get(i).getWordNumber()));
        }

    }

    // this function delete a file's information from the tree
    private void deleteCommand(File file) {

        ArrayList<Word> wordsInFile;

        wordsInFile = Fileutils.txtReader(file);

        for (Word word : wordsInFile) {
            System.out.println("here");
            DataStructreNode node = (DataStructreNode) Constants.dataStructre.get(word.getData());
//            System.out.println("word's positions are1111111 \t" + node.getWordsPosition() + "word's data is \t" + node.getData());
            if (node != null) {
                node.removeFromFile(Fileutils.gettingNameOfFile(file));
                if (node.getListOfFiles() == null || node.getListOfFiles().getSize() == 0) {
//                    System.out.println("We are deleting word with data \t" + word.getData());
                    Constants.dataStructre.delete(word.getData());
                } else {
                    node.removeFromWordPosition(FileManager.getFileManagerSharedInstance().
                            filePostitionStringGenerator(word.getFileNumber(), word.getWordNumber()));
//                    System.out.println("word's positions are2222222  \t" + node.getWordsPosition() + "word's data is \t" + node.getData());


                }


            }


        }
        FileManager.getFileManagerSharedInstance().removeFromExecutedFiles(Fileutils.gettingNameOfFile(file));

    }

    // this funtion update a file's content in tree
    private void updateCommand(File file) throws NullPointerException {

        ArrayList<Word> wordsInFile;


            wordsInFile = Fileutils.txtReader(file);

            Constants.dataStructre.update(wordsInFile, Fileutils.gettingNameOfFile(file));



    }

    // this function list all the words and their files and return it in the form of on string
    private String listOfWordsCommand() {

        ArrayList<String> wordsOfTree = new ArrayList<>();
        String result = "";

        wordsOfTree = Constants.dataStructre.traverse(wordsOfTree);

//        System.out.println("words array is \t" + wordsOfTree);
        for (String word : wordsOfTree) {
//            System.out.println(wordsOfTree.size());
            result = result + word + "  ->  " + Constants.dataStructre.get(word).getListOfFiles() + "\n\t";
        }
        return result;
    }

    // this function  return the number of listed files in the program
    public String listOfExecutedFiles() {
        String str = "";
        for (int i = 0; i < FileManager.getFileManagerSharedInstance().getExecutedFiles().size(); i++) {

            str = str + Fileutils.gettingNameOfFile(FileManager.getFileManagerSharedInstance().getExecutedFiles().get(i));
            if (i != FileManager.getFileManagerSharedInstance().getExecutedFiles().size() - 1)
                str = str + ",";

        }
        return str + "\n" +
                "Number of listed docs is :\t" + FileManager.getFileManagerSharedInstance().getExecutedFiles().size();
    }

    // this function returns all the document that are added to the program
    public String listOfAllDocs() {
        String str = "";

        for (int i = 0; i < FileManager.getFileManagerSharedInstance().getFiles().size(); i++) {
            str = str + FileManager.getFileManagerSharedInstance().getFiles().get(i);
            if (i != FileManager.getFileManagerSharedInstance().getFiles().size() - 1)
                str = str + ",";
        }
        return FileManager.getFileManagerSharedInstance().getFiles() + "\n"
                + "Number of  docs is :\t" + FileManager.getFileManagerSharedInstance().getFiles().size();
    }

    // this function process the search of the sentence command
    private String searchSentenceCommand(String str) {
        ArrayList<String> words = new ArrayList<>();


        String[] strings = str.split("[^a-zA-Z]+");
        for (String word : strings) {
            if (!StopWordsManager.getStopWordsInstance().getStopWords().contains(word.toLowerCase()) && !word.toLowerCase().equals(""))
                words.add(word.toLowerCase());

        }
        LinkedList[] files = new LinkedList[words.size()];
        ArrayList[] wordsPositions = new ArrayList[words.size()];
        for (int i = 0; i < words.size(); i++) {
//            System.out.println("word is " + words.get(i));
            try {

                    DataStructreNode node = ( Constants.dataStructre).get(words.get(i));

                    files[i] = new LinkedList(node.getListOfFiles());


                    wordsPositions[i] = new ArrayList(node.getWordsPosition());   //node.getWordsPosition();

            } catch (NullPointerException e) {
                return "word " + words.get(i) + "is not exits in documents";
            }
        }
            // get their unity
        for (int i = 0; i < files.length - 1; i++) {
            files[i + 1] = this.unityOfFiles(files[i], files[i + 1]);
        }

//        System.out.println(files[files.length - 1]);
        if (files[files.length - 1] != null && files[files.length - 1].getSize() != 0) {
      // send the common finals  of words a with words and their positions then get the result statement :))
            ArrayList<String> results = this.sentenceCommandResultStatementGenerator(files[files.length - 1], words, wordsPositions);
            String string = "";
            for (int i = 0; i < results.size(); i++) {
                str = str + results.get(i);

            }
            return str;
//            return files[files.length - 1].toString();
        } else return "no common files ";
    }

    public Stack getPreviousCommands() {
        return previousCommands;
    }

    // eshterak
    public LinkedList unityOfFiles(LinkedList list1, LinkedList list2) {
        LinkedList unity = new LinkedList();

        invertedIndex.utils.datastructres.linkedList.Node node = list1.getFirst().getNext();

        while (node != null) {

            if (list2.contains(node.getData())) {
                unity.add(node.getData());
            }
            node = node.getNext();

        }
        return unity;
    }

    private ArrayList<String> sentenceCommandResultStatementGenerator(LinkedList filesNames, ArrayList<String> words, ArrayList<String>[] wordsPositions) {
        // words position is an array of arraylist which each section is based on words ArrayList's content and its consists of its positions

        ArrayList<String> sentences = new ArrayList<>();
        invertedIndex.utils.datastructres.linkedList.Node node = filesNames.getFirst().getNext();
        while (node != null) {
            // getting all the words in the file
            File file = FileManager.getFileManagerSharedInstance()
                    .getExecutedFileByName((String) node.getData());

            ArrayList<String> wordsInFile = Fileutils.readSentenceFromTxtFile(file);
            int fileNumber = FileManager.getFileManagerSharedInstance().
                    getIndexOfExecutedFile(FileManager.getFileManagerSharedInstance().getExecutedFileByName((String) node.getData()).getAbsolutePath());

            String str = "\n" + node.getData() + ":\n(....  ";

            for (int i = 0; i < words.size(); i++) {

                for (int j = 0; i < words.size() && j < wordsPositions[i].size(); j++) {
                    ArrayList<Integer> position = FileManager.getFileManagerSharedInstance().parsingWordPositionString(wordsPositions[i].get(j));

                    if (position.get(0) == fileNumber) {
                        // now we can search for words in its surrounding
                        str = str;
                        for (int k = -3; k < 4; k++) {
                            // if its in range
                            if (position.get(1) + k >= 0 && position.get(1) + k < wordsInFile.size()) {
                                str = str + wordsInFile.get(position.get(1) + k) + " ";

//                                System.out.println("words in file is  \t " + wordsInFile.get(position.get(1) + k) + "\t " + words.contains(wordsInFile.get(position.get(1) + k)));
                                if (words.contains(wordsInFile.get(position.get(1) + k))) {
                        // deleting that word's positions if there is no positions left then deleting word form its array
                        // if the word is the same word we are now iterating in then counter should decremented
                                    System.out.println(wordsPositions[words.indexOf(wordsInFile.get(position.get(1) + k))]);
//                                    wordsPositions[i].remove(wordsPositions[words.indexOf(wordsInFile.get(position.get(i) + k)].remove())
//                                    wordsPositions[i].remove(j);
//                                    System.out.println(fileNumber + "," + (position.get(1) + k));
                                    System.out.println(wordsPositions[words.indexOf(wordsInFile.get(position.get(1) + k))].remove(fileNumber + "," + (position.get(1) + k)));
                                    if (wordsInFile.get(position.get(1) + k).equals(words.get(i)) && j > 0) {
                                        j--;
                                        if (wordsPositions[words.indexOf(wordsInFile.get(position.get(1) + k))].size() == 0) {
                                        words.remove(wordsInFile.get(position.get(1) + k));
                                            i++;
                                        }


                                    }


                                }

                            }


                        }
                        str = str + "  ....) , ";
                        sentences.add(str);
                        str = "  (....  ";


                    }


                }


                // check for all the words in the one sentence
                // check for n-1
                // ....
                // check for 2 words in sentence
                // each words in the different sentences
            }


            node = node.getNext();
        }


        return sentences;
    }

// function which its result is union of files
    public ArrayList<String> unionOfFiles(ArrayList<String> arr1, ArrayList<String> arr2) {


        for (String file : arr1) {

            if (!arr2.contains(file))
                arr2.add(file);

        }

        return arr2;


    }

    public void setPreviousCommands(Stack commands) {
        this.previousCommands = commands;
    }

    public Stack getNextCommands() {
        return nextCommands;
    }

    public void setNextCommands(Stack nextCommands) {
        this.nextCommands = nextCommands;
    }

}
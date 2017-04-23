package invertedIndex.managers;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by sina on 11/29/16.
 *
 *
 * // this is the manager of the all files that user will give to program to analyse and store
 */
public class FileManager {




    public static FileManager fileManagerInstance;

    private ArrayList<File> files;
    private ArrayList<File> executedFiles;
    private String folderPath;




    public FileManager() {

        this.files = new ArrayList<>();
        this.executedFiles = new ArrayList<>();
    }

    public boolean allFilesContains(String fileName){

            String url = this.getFolderPath() + "/"+ fileName;
        for ( File file : this.files){


            if( file.getAbsolutePath().equals(url))
                return true;


        }
        return false;
    }

    public boolean executedFilesContains(String fileName){

        String url = this.getFolderPath() + "/"+ fileName;

        for (File file : executedFiles) {


            if (file.getAbsolutePath().equals(url))
                return true;

        }


        return false;
    }

    public static FileManager getFileManagerSharedInstance() {

        if (fileManagerInstance == null)

        {
            fileManagerInstance = new FileManager();
        }
        return fileManagerInstance;
    }

    public  void removeFromExecutedFiles(String fileName){
        if ( executedFiles == null || executedFiles.size() == 0)
            return;
        String url = this.getFolderPath() + "/"+ fileName;
        File file =    this.getExecutedFileByName(fileName);
        this.executedFiles.remove(file);


    }

    public  File getExecutedFileByName(String fileName){

        String url =this.getFolderPath() + "/"+ fileName;

        for (File file : this.executedFiles){

            if(file.getAbsolutePath().equals(url))
                return file;


        }
        return  null;

    }
    public  File getAllFilesByName(String fileName){

        String url =this.getFolderPath() + "/"+ fileName;

        for (File file : this.files){

            if(file.getAbsolutePath().equals(url))
                    return file;


        }
        return  null;

    }
    // generating string which is in this form : "file number", line number", "word number"
    public String filePostitionStringGenerator(int  fileNumber , int wordNumber){



     return     fileNumber + ","+ wordNumber ;
    }
    public ArrayList<File> getFiles() {
        return files;
    }
    // function which add a new file to all the files of the folder
    public void addToFiles(File file){


        this.files.add(file);

    }

    public ArrayList<File> getExecutedFiles() {
        return executedFiles;
    }

    public void setExecutedFiles(ArrayList<File> executedFiles) {
        this.executedFiles = executedFiles;
    }
    // fucntion which add a new file to the file that are executed  and added to tree
    public void addToExecutedFiles(File file){
        if (executedFiles == null )
            executedFiles=  new ArrayList<>();

        this.executedFiles.add(file);

    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public int getIndexOfAllFiles(String file){

        for (int i =0 ; i< this.files.size() ; i++){

            if ( file.equals(files.get(i).getAbsolutePath()))
                return i;
        }

        return -1;
    }

    public int getIndexOfExecutedFile(String fileAbsoulute){

        for (int i =0 ; i< this.executedFiles.size() ; i++){

            if ( fileAbsoulute.equals(executedFiles.get(i).getAbsolutePath()))
                return i;
        }

        return -1;


    }
    public ArrayList<Integer> parsingWordPositionString(String wordPostion){

        ArrayList<Integer> numbers = new ArrayList<>();
        String[] strings = wordPostion.split(",");
        for ( int i =0 ; i< strings.length ; i++){


            if ( strings[i] != "")
                numbers.add(Integer.parseInt(strings[i]));
        }
        return numbers;


    }

}

package invertedIndex.utils.datastructres.stack;

import invertedIndex.utils.datastructres.DataStructre;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * Created by sina on 11/29/16.
 *
 *
 * stack
 */
public class Stack <T> {


    private int MaxSize;
    private ArrayList stackArray;
    private int size;


    public Stack(int size) {
        this.MaxSize = size;
        this.size =0;
        stackArray = new ArrayList<T>(size);
    }

    public int getSize() {
        return size;
    }

    public Stack() {
        stackArray = new ArrayList<T>();
        this.size = 0;
    }

    public boolean isEmpty()
    {

        if ( size == 0 && stackArray.isEmpty()) return true;
        return  false;

    }
    public boolean isFull(){

        if ( this.size == stackArray.size())
            return true;
        return false;

    }

    public void push(Object data){

        stackArray.add(data);

    }


    public Object get(int index){


        if ( index>= stackArray.size())
            return  new IndexOutOfBoundsException();
        return stackArray.get(index);

    }
    public Object pop(){

        if(stackArray.isEmpty())
            throw  new EmptyStackException();

        return  stackArray.remove(stackArray.size()-1);
    }
    public boolean remove(int index){

        if ( index >= stackArray.size())
            return  false;
        stackArray.remove(index);
        return true;
    }

    public boolean contains(Object data){
        return  stackArray.contains(data);

    }
    public Object peek(){
        Object temp = this.pop();
        this.push(temp);
        return temp;
    }


}


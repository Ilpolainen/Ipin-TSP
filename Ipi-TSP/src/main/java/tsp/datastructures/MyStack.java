/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

/**
 *
 * @author omistaja
 */
public class MyStack {

    private int arraySize;
    private Object[] stackArray;
    private int size;

    public MyStack() {
        this.arraySize = 100;
        this.stackArray = new Object[this.arraySize];
        this.size = 0;
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getSize() {
        return size;
    }

    public Object[] getStackArray() {
        return stackArray;
    }

    public boolean empty() {
        return this.size == 0;
    }

    /**
     * Kuten growingArrayssa, tämän tila- ja aikavaativuus on keskimäärin O(1).
     * @param o Stackin alkio.
     */
    
    public void push(Object o) {
        if (this.size == this.arraySize) {
            Object[] newArray = new Object[size * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = this.stackArray[i];
            }
            this.stackArray = newArray;
        }
        this.stackArray[size] = o;
        this.size = this.size + 1;
    }

    /**
     * Selvä O(1).
     * @return Pinon päällimmäinen alkio.
     */
    public Object pop() {
        if (this.size == 0) {
            return null;
        }
        this.size = this.size - 1;
        return this.stackArray[this.size];
    }
}

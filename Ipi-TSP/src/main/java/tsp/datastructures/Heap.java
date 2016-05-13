/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

import tsp.citytools.Connection;
import java.util.Comparator;

/**
 * Tämä luokka on Tirasta tuttu keko, jonka alkioita ovat Connection -oliot ja
 * kekoavaimena niiden comparatorin arvot. Sen kaikki operaatiot
 * ovat aikavaativuudeltaan O(log(n)). Tämä on selitetty Tiran materiaalissa,
 * enkä jaksa sitä tähän toisintaa. Käytännössä tämä johtuu siitä, että keko on
 * puu joka pysyy koko ajan melkein täydellisenä.
 *
 * @author omistaja
 */
public class Heap {

    private Connection[] array;
    private int heapSize;
    private Comparator comp;

    /**
     * Uuden taulukon luonti on tila- ja aikavaativuudeltaan O(n).
     *
     * @param size tarvittavan keon koko
     * @param comp Comparator joka päättää, kekoavainten suuruusjärjestyksen.
     */
    public Heap(int size, Comparator comp) {
        this.heapSize = 0;
        this.array = new Connection[size];
        this.comp = comp;
    }

    public Connection[] getArray() {
        return array;
    }

    public Comparator getComp() {
        return comp;
    }

    public int getHeapSize() {
        return heapSize;
    }

    public Connection peek() {
        return array[0];
    }

    public int left(int i) {
        return i * 2;
    }

    public int right(int i) {
        return i * 2 + 1;
    }

    public int parent(int i) {
        return i / 2;
    }

    public void heapify(int i) {
        int l = left(i);
        int r = right(i);
        if (r <= this.heapSize) {
            int largest;
            if (comp.compare(array[l - 1], array[r - 1]) < 0) {
                largest = l;
            } else {
                largest = r;
            }
            if (comp.compare(array[i - 1], array[largest - 1]) > 0) {
                swap(i, largest);
                heapify(largest);
            }
        } else if (l == this.heapSize && comp.compare(array[i - 1], array[l - 1]) > 0) {
            swap(i, l);
        }
    }

    public void swap(int a, int b) {
        Connection temp = array[a - 1];
        array[a - 1] = array[b - 1];
        array[b - 1] = temp;
    }

    public void heapInsert(Connection con) {
        if (this.heapSize >= this.array.length) {
            growArray();
        }
        this.heapSize = this.heapSize + 1;
        int i = this.heapSize;
        while (i > 1 && comp.compare(con, this.array[parent(i) - 1]) < 0) {
            this.array[i - 1] = this.array[parent(i) - 1];
            i = parent(i);
        }
        this.array[i - 1] = con;
    }

    public void growArray() {
        Connection[] newArray = new Connection[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
    }

    public Connection heapDelmax() {
        if (this.heapSize == 0) {
            return null;
        }
        Connection max = this.array[0];
        this.array[0] = this.array[this.heapSize - 1];
        this.heapSize = this.heapSize - 1;
        heapify(1);
        return max;
    }

    public boolean isEmpty() {
        return this.heapSize == 0;
    }
}

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
/**
 * Tämä luokka on teoriassa ääretön -kokoinen taulukko, johon voi lisätä
 * alkioita ainoastaan sen loppuun. Lisäys -operaatio on useimmiten O(1), mutta
 * aina kun array tulee täyteen, luodaan uusi n*2 -kokoinen taulukko ja
 * kopioidaan se, mikä on O(n) -operaatio. Lisäys on kuitenkin keskimäärin O(1)
 * -operaatio, sillä 2n kokoinen taulukko pitää luoda puolet edellistä harvemmin
 * uuden taulukon ollessa edellistä kaksi kertaa tilavampi.
 *
 * @author omistaja
 */
public class GrowingArray {

    private Object[] array;
    private int size;

    /**
     * Selvä O(1) (tila- ja aikavaativuus).
     */
    public GrowingArray() {
        this.size = 0;
        this.array = new Object[100];
    }

    public Object[] getArray() {
        return array;
    }

    public int size() {
        return size;
    }

    /**
     * checksize() määrää aika- ja tilavaativuuksiksi keskimäärin O(1).
     * @param o Taulukkoon lisättävä objekti.
     */
    public void add(Object o) {
        checkSize();
        this.array[this.size] = o;
        this.size = this.size + 1;
    }

    /**
     * Aikavaativuus keskimäärin O(1) (selitetty luokan JavaDocissa).
     * Tilavaativuus on samoin perustein keskimäärin O(1).
     */
    public void checkSize() {
        if (this.size == array.length) {
            Object[] newList = new Object[size * 2];
            for (int i = 0; i < size; i++) {
                newList[i] = array[i];
            }
            this.array = newList;
        }
    }

    /**
     * Selvä O(1) aikavaativuus. Tilavaativuus O(0).
     * @param i
     * @return 
     */
    
    public Object get(int i) {
        if (i >= size || i < 0) {
            return null;
        }
        return array[i];
    }
}

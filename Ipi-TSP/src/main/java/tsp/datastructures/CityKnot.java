/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

/**
 * Rengaslistan tarvitsema solmumuoto kaupungeista. Sisältää ainoastaan
 * kaupungin numeron ja viitteet sitä seuraavan ja sitä edeltävän kaupungin
 * solmuolioihin. Kaikki tämän luokan metodit ovat itsestäänselviä O(1)
 * -operaatioita (settereitä ja gettereitä), joita en sen kummemmin analysoi.
 *
 * @author omistaja
 */
public class CityKnot {

    private int number;
    private CityKnot next;
    private CityKnot prev;

    public CityKnot(int number, CityKnot next, CityKnot prev) {
        this.number = number;
        this.next = next;
        this.prev = prev;
    }

    public CityKnot(int number) {
        this.number = number;
        this.next = null;
        this.prev = null;
    }

    public int getNumber() {
        return number;
    }

    public CityKnot getNext() {
        return next;
    }

    public CityKnot getPrev() {
        return prev;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrev(CityKnot prev) {
        this.prev = prev;
    }

    public void setNext(CityKnot next) {
        this.next = next;
    }

}

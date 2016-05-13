/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

/**
 * Kahteen suuntaan linkitetty rengaslista joka on hyödyllinen apuväline
 * Brute-Force metodin toteuttamisessa. Lista tietää ainoastaan kokonsa ja yhden
 * CityKnot -olion, joka on nimetty headiksi. Listan järjestys prev ja next
 * -navigoinneilla on toistaiseksi nurinkurinen, mutta silti johdonmukainen.
 * Refaktorointia odotellesa.
 *
 * @author omistaja
 */
public class WheelList {

    private CityKnot head;
    private int size;

    public WheelList() {
        head = null;
        size = 0;
    }

    /**
     * Konstruktori O(n) rakentaa uuden rengaslistan siten, että headiksi jää
     * numero 0 ja previous -kommennoilla pääsee aina yhden solmun "isommaksi".
     * Kuva liitteenä selventämään rakennetta. Insert-metodi O(1) suoritetaan
     * n-kertaa, jossa n edustaa kaupunkien määrää. Uusia kaupunkisolmuja
     * luodaan "size" kappaletta, joka tekee metodin tilavaativuudesta O(n).
     *
     * @param size
     */
    public WheelList(int size) {
        if (size < 0) {
            size = 0;
        }
        for (int i = 0; i < size; i++) {
            this.insert(new CityKnot(size - 1 - i));
        }
    }

    public void setHead(CityKnot head) {
        this.head = head;
    }

    public CityKnot getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public boolean empty() {
        return this.head == null;
    }

    /**
     * Asettaa uuden solmun listaan siten, että tästä itsestään tulee uusi head
     * ja edellisestä headista tulee tämän prev. Listan "viimeinen" pysyy
     * samana, eli on siis headin next.
     *
     * Metodi käyttää vakiomäärän settereitä, gettereitä ja muita O(1)
     * -komentoja ja on näin ollen itsekin O(1).
     *
     * @param knot Tästä solmusta tulee listan uusi head.
     */
    public void insert(CityKnot knot) {
        size = size + 1;
        if (empty()) {
            this.head = knot;
            knot.setPrev(knot);
            knot.setNext(knot);
            return;
        }
        knot.setPrev(head);
        knot.setNext(head.getNext());
        this.head.getNext().setPrev(knot);
        this.head.setNext(knot);
        this.head = knot;
    }

    /**
     * Poistaa headin listasta ja palautaa sen. Tämän headin prev:istä tulee
     * uusi head ja "viimeisestä" solmusta tulee uuden headin next.
     *
     * @return Palauttaa juuri irroitetun headin.
     */
    public CityKnot takeHead() {
        if (empty()) {
            return null;
        }
        size = size - 1;
        CityKnot y = this.head;
        CityKnot prev = head.getPrev();
        prev.setNext(head.getNext());
        head.getNext().setPrev(prev);
        head = prev;
        return y;
    }

    /**
     * "Pyöräyttää rengasta yhden pykälän nimeämällä headiksi sitä edellisen
     * solmun."
     */
    public void rotate() {
        head = head.getPrev();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

/**
 * Luokka vastaa tismalleen Tirassa esitellyn pienimmän virittävän puun
 * rakentavan Kruskalin algoritmin optimointia. Haluaisin siirtää tämän
 * datastructures packageen. Tässä toteutetaan myös poluntiivistys. Luokkaa
 * käytetään tarkistamaan syklittömyyttä Clarke-Wright-algoritmin
 * polunmuodostuksessa.
 *
 * @author omistaja
 */
public class UnionFinder {

    private int[] parents;
    private int[] heights;

    public UnionFinder(int size) {
        this.parents = new int[size];
        for (int i = 0; i < size; i++) {
            this.parents[i] = i;
        }
        this.heights = new int[size];
    }

    public int[] getHeights() {
        return heights;
    }
    
    

    public int[] getParents() {
        return parents;
    }
    
    

    /**
     * Tutkii muodostaako yhteys syklin jo hyväksyttyjen yhteyksien kanssa.
     * O(log n), missä n on kaupunkien lukumäärä. Tämän metodin kutsuminen
     * kuitenkin tiivistää polun siten, ja pitää kaupungit puuna siten, että m
     * kertaa kutsuttuna tämä on vain O(m * alpha(n)), missä alpha on niin
     * hitaasti kasvava funktio, että sitä voidaan pitää vakioaikaisena.
     * (alpha(n) pienempää tai yhtäsuurta kuin 4 kun kun n on alle 10^80 ja näin
     * suuria syötteitä ei ohjelma koskaan voi saada). Kun Find-operaatio
     * kutsutaan kerran, sen jälkeen kaikki findit, joita kutsutaan samaan
     * palaan kuuluville kaupungeille pääsevät suoraan juureensa. Union taas
     * pitää huolen siitä, että maksimikorkeus pysyy logaritmisena. (Itse
     * heights -taulukko ei findin takia välttämättä pysy ajantasalla, mutta
     * tämä ei silti aiheuta puun korkeuden kasvamista vaan pitää ainoastaan
     * ylärajan aisoissa).
     *
     * @param con
     * @return Palauttaa true, jos yhteys muodostaa syklin.
     */
    public boolean formingCycle(Connection con) {
        int x = con.getStart().getNumber();
        int y = con.getEnd().getNumber();
        if (find(x) == find(y)) {
            return true;
        } else {
            union(find(x), find(y));
        }
        return false;
    }

    /**
     * Yhdistää kaksi kaupunki-puuta samaan puuhun, pitäen puun korkeuden
     * logaritmisena asettamalla pienemmän puun suuremman alipuuksi. Selvä O(1).
     *
     * @param x toisen puun juuri
     * @param y toisen puun juuri
     */
    public void union(int x, int y) {
        if (heights[x] < heights[y]) {
            parents[x] = y;
        } else if (heights[x] > heights[y]) {
            parents[y] = x;
        } else {
            parents[x] = y;
            heights[y] = heights[x] + 1;
        }
    }

    /**
     * Etsii annetun kaupunkinumeron puun juuren ja tiivistää polun siten, että
     * kaikki matkallalöydetyt solmut laitetaan osoittamaan suoraan juureen.
     * Reitti on korkeintaan puun korkuinen. Matkalla kutsutaan ainoastaan O(1)
     * -operaatioita. Poluntiivistyksen takia myöhemmät kutsut ovat huomattavsti
     * nopeampia. Pahimman tapauksen aikavaativuus on siis O(log n), missä n on
     * puun solmujen lukumäärä, joka tässä viittaa kaupunkien lukumäärään. On
     * kuitenkin osoitettu, että k-alkioisessa perusjoukossa (tässä n = k) m
     * kertaa kutsuttuna ylärajaksi saadaan O(m * alpha(n)), missä alpha on
     * äärimmäisen hitaasti kasvava funktio, jota voidaan käytännössä pitää
     * huoletta vakiona 4. Tässä ohjelmassa m viittaa kaikkiin mahdollisiin
     * "savings -short-cutteihin", joita on hiukan alle n^2 kpl.
     *
     * @param city kaupunki jonka puun juurta etsitään
     * @return puun juuri
     */
    public int find(int city) {
        int z = city;
        while (parents[z] != z) {
            z = parents[z];
        }
        int y = z;
        while (parents[z] != z) {
            int temp = z;
            z = parents[z];
            parents[temp] = y;
        }
        return y;
    }

}

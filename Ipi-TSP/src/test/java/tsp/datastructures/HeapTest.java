/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

import tsp.citytools.City;
import tsp.citytools.Connection;
import tsp.citytools.SavingsComparator;
import tsp.datastructures.Heap;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author omistaja
 */
public class HeapTest {

    private Heap heap;
    private SavingsComparator comp;

    public HeapTest() {
    }

    @Before
    public void setUp() {
        comp = new SavingsComparator();
        this.heap = new Heap(1, comp);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void konstruktoriAsettaaTaulukonKoonYkköseksi() {
        assertEquals(1, heap.getArray().length);
    }

    @Test
    public void konstruktoriTekeTyhjanKeon() {
        assertEquals(0, heap.getHeapSize());
        assertEquals(true, heap.isEmpty());
        Object o = heap.getArray()[0];
        boolean totuus = o == null;
        assertEquals(true, totuus);
    }
    
    
    @Test
    public void growArrayToimiiOikein() {
        int prev = heap.getArray().length;
        for (int i = 2; i < 9; i++) {
            heap.growArray();
            int current = heap.getArray().length;
            assertEquals(prev*2, current);
            prev = current;
        }
    }

    @Test
    public void vasenLapsiAntaaOikeitaArvoja() {
        for (int i = 1; i < 20; i++) {
            assertEquals(i * 2, heap.left(i));
        }
    }

    @Test
    public void oikeaLapsiAntaaOikeitaArvoja() {
        for (int i = 1; i < 20; i++) {
            assertEquals(i * 2 + 1, heap.right(i));
        }
    }

    @Test
    public void parentAntaaOikeitaArvoja() {
        for (int i = 2; i < 20; i++) {
            assertEquals(i / 2, heap.parent(i));
        }
    }

    @Test
    public void heapInsertKasvattaaKokoa() {
        City[] cities = new City[20];
        for (int i = 0; i < 20; i++) {
            City c1 = new City(i, i, i);
            cities[i] = c1;
        }
        for (int i = 0; i < 18; i++) {
            Connection con = new Connection(cities[i], cities[i + 1]);
            heap.heapInsert(con);
            assertEquals(i + 1, heap.getHeapSize());
        }
    }

    @Test
    public void heapInsertToimiiOikein() {
        City[] cities = new City[20];
        for (int i = 0; i < 20; i++) {
            City c1 = new City(i, i, i);
            cities[i] = c1;
        }
        for (int i = 0; i < 7; i++) {
            Connection con = new Connection(cities[i], cities[i + 1]);
            con.setSavingsWeight(i + 1);
            heap.heapInsert(con);
            assertEquals(i + 1, heap.getHeapSize());
        }
        Connection c1 = heap.getArray()[0];
        Connection c2 = heap.getArray()[1];
        Connection c3 = heap.getArray()[2];
        Connection c4 = heap.getArray()[3];
        Connection c5 = heap.getArray()[4];
        Connection c6 = heap.getArray()[5];
        Connection c7 = heap.getArray()[6];
        assertEquals(7, (int) c1.getSavingsWeight());
        assertEquals(4, (int) c2.getSavingsWeight());
        assertEquals(6, (int) c3.getSavingsWeight());
        assertEquals(1, (int) c4.getSavingsWeight());
        assertEquals(3, (int) c5.getSavingsWeight());
        assertEquals(2, (int) c6.getSavingsWeight());
        assertEquals(5, (int) c7.getSavingsWeight());
    }

    @Test
    public void heapifyToimiiOikein() {
        City[] cities = new City[20];
        for (int i = 0; i < 20; i++) {
            City c1 = new City(i, i, i);
            cities[i] = c1;
        }
        for (int i = 0; i < 18; i++) {
            Connection con = new Connection(cities[i], cities[i + 1]);
            con.setSavingsWeight(i);
            heap.heapInsert(con);
        }
        Connection head = heap.peek();
        head.setSavingsWeight(1);
        heap.heapify(1);
        head = heap.peek();
        int a = (int) head.getSavingsWeight();
        assertEquals(16, a);
        Connection con = heap.getArray()[17];
        int b = (int) con.getSavingsWeight();
        assertEquals(1, b);
    }

    public void delMaxAntaaNullinTyhjastaKeosta() {
        Connection con = heap.heapDelmax();
        assertEquals(true, con == null);
    }
    
    public void kekoAntaaLuvutOikeassaJarjestuksessa() {
        City[] cities = new City[103];
        for (int i = 0; i < 103; i++) {
            City c1 = new City(i, i, i);
            cities[i] = c1;
        }
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            Connection con = new Connection(cities[i], cities[i + 1]);
            con.setSavingsWeight(r.nextInt(100));
            heap.heapInsert(con);
        }
        int prev = 1000;
        for (int i = 0; i < 100; i++) {
            Connection con = heap.heapDelmax();
            int current = (int) con.getSavingsWeight();
            assertEquals(true, prev >= current);
            prev = current;
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

/**
 * @file ./src/dominio/tests/junits/TestSlotRanking.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.junits;

import org.junit.Test;
import src.dominio.clases.SlotRanking;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * @class TestSlotRanking
 * @brief Classe per testejar la classe SlotRanking.
 */
public class TestSlotRanking {
    /**
     * Test pel getter de user
     */
    @Test
    public void testgetUser() {
        SlotRanking s = new SlotRanking("tester",0,0,0);
        assertEquals("tester", s.getUser());
        SlotRanking s2 = new SlotRanking("tester2",0,0,0);
        assertEquals("tester2", s2.getUser());
        assertNotEquals(s.getUser(),s2.getUser());
    }
    /**
     * Test pel getter de hours
     */
    @Test
    public void testgetHours() {
        SlotRanking s = new SlotRanking("tester",0,0,0);
        assertEquals(0, s.getHours());
        SlotRanking s2 = new SlotRanking("tester2",1,0,0);
        assertEquals(1, s2.getHours());
        assertNotEquals(s.getHours(),s2.getHours());
    }
    /**
     * Test pel getter de minutes
     */
    @Test
    public void testgetMinutes() {
        SlotRanking s = new SlotRanking("tester",0,0,0);
        assertEquals(0, s.getMinutes());
        SlotRanking s2 = new SlotRanking("tester2",0,30,0);
        assertEquals(30, s2.getMinutes());
        assertNotEquals(s.getMinutes(),s2.getMinutes());
    }
    /**
     * Test pel getter de seconds
     */
    @Test
    public void testgetSeconds() {
        SlotRanking s = new SlotRanking("tester",0,0,0);
        assertEquals(0, s.getSeconds());
        SlotRanking s2 = new SlotRanking("tester2",0,0,59);
        assertEquals(59, s2.getSeconds());
        assertNotEquals(s.getSeconds(),s2.getSeconds());
    }
    /**
     * Test pel setter de user
     */
    @Test
    public void testsetUser() {
        SlotRanking s = new SlotRanking("x",0,0,0);
        s.setUser("tester");
        assertEquals("tester", s.getUser());
        s = new SlotRanking();
        s.setUser("tester2");
        assertEquals("tester2", s.getUser());
    }
    /**
     * Test pel setter de hours
     */
    @Test
    public void testsetHours() {
        SlotRanking s = new SlotRanking("x",0,0,0);
        s.setHours(1);
        assertEquals(1, s.getHours());
        s = new SlotRanking();
        s.setHours(2);
        assertEquals(2, s.getHours());
    }
    /**
     * Test pel setter de minutes
     */
    @Test
    public void testsetMinutes() {
        SlotRanking s = new SlotRanking("x",0,0,0);
        s.setMinutes(1);
        assertEquals(1, s.getMinutes());
        s = new SlotRanking();
        s.setMinutes(30);
        assertEquals(30, s.getMinutes());
    }
    /**
     * Test pel setter de seconds
     */
    @Test
    public void testsetSeconds() {
        SlotRanking s = new SlotRanking("x",0,0,0);
        s.setSeconds(1);
        assertEquals(1, s.getSeconds());
        s = new SlotRanking();
        s.setSeconds(30);
        assertEquals(30, s.getSeconds());
    }

}

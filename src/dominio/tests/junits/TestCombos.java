/**
 * @file ./src/dominio/tests/junits/TestCombos.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.junits;

import org.junit.Test;
import src.dominio.clases.*;
import java.io.FileNotFoundException;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @class TestCombos
 * @brief Classe per testejar la classe Combos.
 */
public class TestCombos {

    /**
     * Test per la funcio exists_sum_of_values
     * @throws  FileNotFoundException if the combos file is not loaded.
     */
    @Test
    public void testExists_sum_of_cells() throws FileNotFoundException {
        Combos c = new Combos();
        c.getInstance();
        assertTrue(c.exists_sum_of_cells(24, 3));
        assertTrue(c.exists_sum_of_cells(6, 3));
        assertFalse(c.exists_sum_of_cells(5, 3));
        assertFalse(c.exists_sum_of_cells(25, 3));
    }




}


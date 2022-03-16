/**
 * @file ./src/dominio/tests/junits/TestSumCell.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.junits;

import org.junit.Test;
import src.dominio.clases.Cell;
import src.dominio.clases.SumCell;
import src.dominio.clases.ExceptionKakuro;
import src.dominio.clases.kakuro.Kakuro;

import static org.junit.Assert.*;

/**
 * @class TestSumCell
 * @brief Classe per testejar la classe SumCell.
 */
public class TestSumCell {

    /**
     * Kakuro 5x5 amb una unica solucio
     */
    String[][] prueba = new String[][] { new String[]{"*", "*", "*", "C24", "C7"},
            new String[] {"*", "C6", "C24F8", "?", "?"},
            new String[] {"F18", "?", "?", "?", "?"},
            new String[]{"F23", "?", "?", "?", "?"},
            new String[] { "F12", "?", "?", "*", "*"}};

    /**
     * Test construtora de SumCell
     */
    @Test
    public void testCreator() {
        SumCell cell = new SumCell();
        assertEquals(-1, cell.get_sum_row());
        //assertEquals(-1, cell.get_num_cells_column());
        //assertEquals(-1, cell.get_num_cells_row());
        assertFalse(cell.has_sum_column());
        assertFalse(cell.has_sum_row());
    }

    /**
     * Test per saber si SumCell te suma (vertical o horitzonal)
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testHas_sum() throws ExceptionKakuro {
        Kakuro kakuro = new Kakuro(5,5,prueba);
        Cell c1 = kakuro.get_cell(1,1);
        assertTrue(c1.has_sum_column());
        assertFalse(c1.has_sum_row());
        Cell c2 = kakuro.get_cell(2,0);
        assertFalse(c2.has_sum_column());
        assertTrue(c2.has_sum_row());
        Cell c3 = kakuro.get_cell(1,2);
        assertTrue(c3.has_sum_column());
        assertTrue(c3.has_sum_row());

    }

    /**
     * Test Setters de SumCell
     */
    @Test
    public void testSetters() {
        SumCell c = new SumCell();
        /*c.set_num_cells_column(9);
        assertEquals(9, c.get_num_cells_column());
        c.set_num_cells_row(9);
        assertEquals(9, c.get_num_cells_row()); */
        c.set_sum_column(1);
        c.set_sum_row(2);
        assertEquals(1, c.get_sum_column());
        assertEquals(2,c.get_sum_row());
    }

    /**
     * Test Getters de SumCell
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testGetters() throws ExceptionKakuro {
        /*Kakuro kakuro = new Kakuro(5,5,prueba);
        Cell c1 = kakuro.get_cell(1,1);
        assertEquals(6, c1.get_sum_column());
        assertEquals(3, c1.get_num_cells_column());
        Cell c2 = kakuro.get_cell(2,0);
        assertEquals(18, c2.get_sum_row());
        assertEquals(4, c2.get_num_cells_row());
        Cell c3 = kakuro.get_cell(1,2);
        assertEquals(24, c3.get_sum_column());
        assertEquals(8, c3.get_sum_row());
        assertEquals(2, c3.get_num_cells_row());
        assertEquals(3, c3.get_num_cells_column());
      */
    }

}

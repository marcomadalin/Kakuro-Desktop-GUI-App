/**
 * @file ./src/dominio/tests/junits/TestWhiteCell.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.junits;

import org.junit.Test;
import src.dominio.clases.Cell;
import src.dominio.clases.WhiteCell;
import src.dominio.clases.ExceptionKakuro;
import src.dominio.clases.kakuro.Kakuro;

import static org.junit.Assert.*;

/**
 * @class TestWhiteCell
 * @brief Classe per testejar la classe WhiteCell.
 */
public class TestWhiteCell {

    /**
     * Kakuro 5x5 amb una unica solucio
     */
    String[][] prueba = new String[][] { new String[]{"*", "*", "*", "C24", "C7"},
            new String[] {"*", "C6", "C24F8", "?", "?"},
            new String[] {"F18", "?", "?", "?", "?"},
            new String[]{"F23", "?", "?", "?", "?"},
            new String[] { "F12", "?", "?", "*", "*"}};

    /**
     * Test creadora de WhiteCell
     */
    @Test
    public void testCreator() {
        WhiteCell c = new WhiteCell();
        assertEquals(0,c.get_value());
        assertTrue(c.is_unassigned());
    }

    /**
     * Test constructora de WhiteCell
     */
    @Test
    public void testSet_value() {
        WhiteCell c = new WhiteCell();
        c.set_value(8);
        assertEquals(8, c.get_value());
    }

    /**
     * Test per saber si la casella blanca esta sense assignar
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testIs_unassigned() throws ExceptionKakuro {
        Kakuro kakuro = new Kakuro(5, 5, prueba);
        Cell c = kakuro.get_cell(1, 3);
        assertTrue(c.is_unassigned());
        kakuro.set_result(1, 3, 7);
        c = kakuro.get_cell(1, 3);
        assertFalse(c.is_unassigned());
    }

    /**
     * Test Getter per defecte del valor de WhiteCell
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testGet_value() throws ExceptionKakuro {
        Kakuro kakuro = new Kakuro(5, 5, prueba);
        kakuro.set_result(1, 3, 7);
        Cell c = kakuro.get_cell(1, 3);
        assertEquals(7, c.get_value());


    }
    @Test (expected = ExceptionKakuro.class)
    public void testerroget_value() throws ExceptionKakuro {
        Kakuro kakuro = new Kakuro(5, 5, prueba);
        kakuro.set_result(1, 3, 10);
        Cell c = kakuro.get_cell(1, 3);
        assertEquals(0,c.get_value());
        kakuro.set_result(1, 3, 10);
         c = kakuro.get_cell(1, 3);
        assertEquals(0,c.get_value());
    }


}

/**
 * @file ./src/dominio/tests/junits/TestGenerator.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.junits;

import org.junit.Test;
import src.dominio.clases.ExceptionKakuro;
import src.dominio.clases.kakuro.Generator;
import src.dominio.clases.kakuro.Kakuro;
import src.dominio.clases.kakuro.Solver;

import static org.junit.Assert.*;
/**
 * @class TestSolver
 * @brief Classe per testejar la classe Generator.
 */
public class TestGenerator {
    /**
     * Test per la funcio generate_kakuro amb dificultat facil
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testgenerate_kakuroEasy() throws ExceptionKakuro {
        Generator g = new Generator();
        Solver s = new Solver();
        Kakuro k = g.generate_kakuro(4,4,1);
        assertTrue(s.validate_kakuro(k));
        assertEquals(4,k.get_columns());
        assertEquals(4,k.get_rows());
        assertEquals(1, k.get_difficulty());
    }
    /**
     * Test per la funcio generate_kakuro amb dificultat mitjana
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testgenerate_kakuroMedium() throws ExceptionKakuro {
        Generator g = new Generator();
        Solver s = new Solver();
        Kakuro k = g.generate_kakuro(6,6,2);
        assertTrue(s.validate_kakuro(k));
        assertEquals(6,k.get_columns());
        assertEquals(6,k.get_rows());
        assertEquals(2, k.get_difficulty());
    }
    /**
     * Test per la funcio generate_kakuro amb dificultat dificil
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testgenerate_kakuro() throws ExceptionKakuro {
        Generator g = new Generator();
        Solver s = new Solver();
        Kakuro k = g.generate_kakuro(5,6,2);
        assertTrue(s.validate_kakuro(k));
        assertEquals(6,k.get_columns());
        assertEquals(5,k.get_rows());
        assertEquals(2, k.get_difficulty());
    }



}

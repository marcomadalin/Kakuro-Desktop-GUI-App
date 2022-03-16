/**
 * @file ./src/dominio/tests/junits/TestSolver.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.junits;

import org.junit.Test;
import src.dominio.clases.ExceptionKakuro;
import src.dominio.clases.kakuro.Kakuro;
import src.dominio.clases.kakuro.Solver;

import static org.junit.Assert.*;
/**
 * @class TestSolver
 * @brief Classe per testejar la classe Solver.
 */
public class TestSolver {
    //jocs de proves
    /**
     * Kakuro 5x5 amb una unica solucio
     */
    String[][] prueba = new String[][] { new String[]{"*", "*", "*", "C24", "C7"},
            new String[] {"*", "C6", "C24F8", "?", "?"},
            new String[] {"F18", "?", "?", "?", "?"},
            new String[]{"F23", "?", "?", "?", "?"},
            new String[] { "F12", "?", "?", "*", "*"}};

    /**
     * Kakuro 3x3 amb mes d'una solucio
     */
    String[][] pruebaNoUnico = new String[][] { new String[]{"*","C4","C5"},
            new String[] {"F4","?","?"},
            new String[] {"F5","?","?"}};
    /**
     * Kakuro 13x13 amb una unica solucio
     */
    String[][] prueba3 = new String[][] { new String[] {"*","*","*","*","*","C16","C3","*","*","*","*","*","*"},
            new String[] {"*","C6","C21","*","F11","?","?","C28","*","C29","C17","*","*"},
            new String[] {"F4","?","?","*","F12","?","?","?","C7F14","?","?","*","*" },
            new String[] {"F7","?","?","*","C16","C4","F26","?","?","?","?","*","*"},
            new String[] {"F3","?","?","C23F12","?","?","C29F11","?","?","?","*","*","*" },
            new String[] {"*","C16F41","?","?","?","?","?","?","?","?","C7","C38","C3"},
            new String[] {"F17","?","?","?","*","F15","?","?","*","F6","?","?","?" },
            new String[] {"F21","?","?","?","C29","C23F8","?","?","C16","C4F14","?","?","?" },
            new String[] {"*","*","*","F39","?","?","?","?","?","?","?","?","C6"},
            new String[] {"*","*","*","C4F14","?","?","?","F8","?","?","F6","?","?"},
            new String[] {"*","*","F20","?","?","?","?","C17","C16","*","F9","?","?" },
            new String[] {"*","*","F12","?","?","F21","?","?","?","*","F10","?","?" },
            new String[] {"*","*","*","*","*","*","F17","?","?","*","*","*","*" }};
    /**
     * Test per la funcio validate_kakuro amb un kakuro valid
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testvalidate() throws ExceptionKakuro {
        Solver s = new Solver();
        assertTrue(s.validate_kakuro(new Kakuro(5,5,prueba)));
    }

    /**
     * Test per la funcio validate_kakuro amb un kakuro no valid
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test (expected = ExceptionKakuro.class)
    public void testvalidateNO() throws ExceptionKakuro {
        Solver s = new Solver();
        s.validate_kakuro(new Kakuro(3,3,pruebaNoUnico));
    }

    /**
     * Test per la funcio validate_kakuro amb un kakuro valid gran
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testvalidateBIG() throws ExceptionKakuro {
        Solver s = new Solver();
        s.validate_kakuro(new Kakuro(13,13,prueba3));
    }

}

/**
 * @file ./src/dominio/tests/junits/TestKakuro.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.junits;

import org.junit.Test;
import src.dominio.clases.ExceptionKakuro;
import src.dominio.clases.kakuro.Kakuro;
import static org.junit.Assert.*;

/**
 * @class TestKakuro
 * @brief Classe per testejar la classe Kakuro.
 */
public class TestKakuro {

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
     * Kakuro 13x13 amb una unica solucio mitja
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
     * Kakuro 9x9 amb una unica solucio dificil
     */
    String[][] prueba4 = new String[][]{new String[]{"*", "*", "*", "C16", "C7", "*", "*", "C16", "C7"},
            new String[]{"*", "C10", "C19F15", "?", "?", "C17", "C16F8", "?", "?"},
            new String[]{"F43", "?", "?", "?", "?", "?", "?", "?", "?"},
            new String[]{"F13", "?", "?", "C22", "F10", "?", "?", "C17", "*"},
            new String[]{"*", "F6", "?", "?", "*", "F17", "?", "?", "*"},
            new String[]{"*", "F10", "?", "?", "C5", "F5", "?", "?", "C13"},
            new String[]{"*", "C5", "C10F9", "?", "?", "C3", "C15F10", "?", "?"},
            new String[]{"F37", "?", "?", "?", "?", "?", "?", "?", "?"},
            new String[]{"F4", "?", "?", "*", "F11", "?", "?", "*", "*"},
    };
    /**
     * Kakuro 3x3 amb mes d'una solucio
     */
    String[][] pruebaNoUnico = new String[][] { new String[]{"*","C4","C5"},
                            new String[] {"F4","?","?"},
                            new String[] {"F5","?","?"}};

    /**
     * Test per contructora de Kakuro on el Kakuro te una unica solucio
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testvalidate() throws ExceptionKakuro {

        //valido
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);

    }
    /**
     * Test Getter del numero de files del kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testget_rows() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        assertEquals(5, kakuro.get_rows());
    }
    /**
     * Test Getter del numero de columnes del kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testget_columns() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        assertEquals(5, kakuro.get_columns());
    }

    /**
     * Test Getter de la mida del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testget_size() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        Integer[] result = kakuro.getSize();
        Integer[] expected = {5,5};
        assertEquals(expected[0], result[0]);
        assertEquals(expected[1], result[1]);
    }
    /**
     * Test Getter de la dificultat del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testget_difficulty() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        assertEquals(1,kakuro.get_difficulty());
        n = m = 13;
        kakuro = new Kakuro(n,m,prueba3);
        assertEquals(2,kakuro.get_difficulty());
        n = m = 9;
        kakuro = new Kakuro(n,m,prueba4);
        assertEquals(3,kakuro.get_difficulty());
    }


    /**
     * Test de constructora de Kakuro on el kakuro te mes d'una solucio
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test (expected = ExceptionKakuro.class)
    public void testno_unica_solucion() throws ExceptionKakuro {
        int n,m;
        n = m = 3;
        Kakuro kakuro = new Kakuro(n,m,pruebaNoUnico);
    }

    /**
     * Test de Donar una pista de la casella amb fila r i columna c del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testsend_help() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        assertEquals(7,kakuro.send_help(1,3));
        assertEquals(9,kakuro.send_help(3,3));

    }
    @Test(expected = ExceptionKakuro.class)
    public void testerrorhelp() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        //posicio no valida o casella no blanca
        assertEquals(-1,kakuro.send_help(0,0));
        assertEquals(-1,kakuro.send_help(-1,0));
        assertEquals(-1,kakuro.send_help(6,0));
        assertEquals(-1,kakuro.send_help(0,2));
    }

    /**
     * Test d'escriure el Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testwrite() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        assertEquals(prueba, kakuro.write(1));
    }

    /**
     * Test Setter per defecte del nom del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testset_name() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        kakuro.set_name("valido");
        assertEquals("valido", kakuro.get_name());
    }

    /**
     * Test d'assginar valor a una casella blanca
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test (expected = ExceptionKakuro.class)
    public void testSet_result() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        //casella invalida
        assertFalse(kakuro.set_result(5,0,4));
        //casella no blanca
        assertFalse(kakuro.set_result(0,0,4));
        //valor no valid
        assertFalse(kakuro.set_result(1,3,10));
        assertFalse(kakuro.set_result(1,3,0));
        //valor repetit
        assertTrue(kakuro.set_result(1,3,7));
        assertFalse(kakuro.set_result(1,4,7));
        assertFalse(kakuro.set_result(2,3,7));
        //suma de fila o columna sobrepasada
        assertFalse(kakuro.set_result(1,4,8));
        assertTrue(kakuro.set_result(2,3,9));
        assertTrue(kakuro.set_result(3,3,8));
        assertFalse(kakuro.set_result(4,3,6));
    }

    /**
     * Test Getter per defecte de qualsevol casella de grid[][]
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testGet_cell() throws ExceptionKakuro {
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        //cel·la negra
        assertTrue(kakuro.get_cell(0,0).get_value() == -1);
        assertFalse(kakuro.get_cell(0,0).has_sum_column());
        assertFalse(kakuro.get_cell(0,0).has_sum_row());
        //cel·la de suma de columna
        assertTrue(kakuro.get_cell(0,3).has_sum_column());
        assertFalse(kakuro.get_cell(0,3).has_sum_row());
        //cel·la de suma de fila
        assertFalse(kakuro.get_cell(2,0).has_sum_column());
        assertTrue(kakuro.get_cell(2,0).has_sum_row());
        //cel·la de suma de fila i columna
        assertTrue(kakuro.get_cell(1,2).has_sum_column());
        assertTrue(kakuro.get_cell(1,2).has_sum_row());
        assertEquals(0,kakuro.get_cell(1,3).get_value());
        assertFalse(kakuro.get_cell(1,3).has_sum_column());
        assertFalse(kakuro.get_cell(1,3).has_sum_row());
    }

    /**
     * Test Getter de numero de caselles blanques
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    @Test
    public void testGet_white_cells() throws ExceptionKakuro{
        int n,m;
        n = m = 5;
        Kakuro kakuro = new Kakuro(n,m,prueba);
        assertEquals(12, kakuro.get_white_cells());
    }





}

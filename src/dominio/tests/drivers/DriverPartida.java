/**
 * @file ./src/dominio/tests/drivers/DriverPartida.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.drivers;
import src.dominio.clases.Partida;
import src.dominio.clases.ExceptionKakuro;


import java.util.Scanner;

/**
 * @class DriverPartida
 * @brief Classe que representa el Driver de Partida.
 */
public class DriverPartida {

    /**
     * Constructora per defecte de DriverPartida
     */
    public DriverPartida() {
    }

    /**
     * Main del DriverPartida
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    public static void main(String[] args) throws ExceptionKakuro {
        DriverPartida dp = new DriverPartida();
        Scanner reader = new Scanner(System.in);
        int op;
        printUsage();
        op = reader.nextInt();
        while (op != 0) {

            switch (op) {
                case 1:{
                    dp.testinitialize();
                } break;
                case 2: {
                    dp.testname();
                } break;
                case 3:{
                    dp.testsize();
                } break;
                case 4:{
                    dp.testwrite();
                } break;
                case 5:{
                    dp.testset_value();
                } break;
                case 6:{
                    dp.testget_sol();
                }break;
                case 7:{
                    dp.testsol();
                }break;
                case 8:{
                    dp.testgenerate();
                }break;
                case 9:{
                    dp.testgetdifficulty();
                }break;
                default:
                    throw new IllegalStateException("Unexpected value: " + op);

            }
            printUsage();
            op = reader.nextInt();

        }
    }

    //Auxiliar functions
    /**
     * Metode per imprimir les comandes disponibles per l'usuari
     */
    private static void printUsage(){
        System.out.println("******************************************************");
        System.out.println("Driver of the Partida class, what do you want to test?");
        System.out.println("1. Initialize new game");
        System.out.println("2. Change Kakuro name");
        System.out.println("3. Ask for kakuro size");
        System.out.println("4. Get kakuro");
        System.out.println("5. Set a single value");
        System.out.println("6. Ask for a cell solution");
        System.out.println("7. Ask for a complete solution");
        System.out.println("8. Order a randomly generated kakuro");
        System.out.println("------");
        System.out.println("0. EXIT");
        System.out.println("******************************************************");
    }

    /**
     * Test per inicialitzar Partida amb un nou Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testinitialize() throws ExceptionKakuro {
        int test_set;
        Scanner reader = new Scanner(System.in);
        System.out.println("enter the number of the test set (1,2,3)");
        test_set = reader.nextInt();
        Partida p;
        if (test_set == 1) {
            p = new Partida(3,3,prueba1);
            p.validate();
        }
        else if (test_set == 2) {
            p = new Partida(5,5,prueba2);
            p.validate();
            System.out.println("OK");
        }
        else if (test_set == 3) {
            p = new Partida(13,13,prueba3);
            p.validate();
            System.out.println("OK");
        }

    }

    /**
     * Test del nom del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testname() throws ExceptionKakuro {
        Scanner reader = new Scanner(System.in);
        System.out.println("enter name");
        Partida p;

        String name = reader.nextLine();
            p = new Partida(5,5,prueba2);
            p.setName(name);
        name =p.getKakuroName();
        System.out.println(name);

    }

    /**
     * Test del size del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testsize() throws ExceptionKakuro {

        int test_set;
        Scanner reader = new Scanner(System.in);
        System.out.println("enter the number of the test set (2,3)");
        test_set = reader.nextInt();
        Partida p;

        if (test_set == 2) {
            p = new Partida(5,5,prueba2);
            Integer[] s = p.sizeKakuro();
            System.out.println(s[0] + "x" + s[1]);
        }
        else if (test_set == 3) {
            p = new Partida(13,13,prueba3);
            Integer[] s = p.sizeKakuro();
            System.out.println(s[0] + "x" + s[1]);
        }


    }

    /**
     * Test per escriure el Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testwrite() throws ExceptionKakuro {
        int test_set;
        Scanner reader = new Scanner(System.in);
        System.out.println("enter the number of the test set (2,3)");
        test_set = reader.nextInt();
        Partida p;

        if (test_set == 2) {
            p = new Partida(5,5,prueba2);
            String[][] matrix = p.writeKakuro(1);
            Integer [] size = {5,5};
            print(size,matrix);
        }
        else if (test_set == 3) {
            p = new Partida(13,13,prueba3);
            Integer [] size = {13,13};
            String[][] matrix = p.writeKakuro(1);
            print(size,matrix);
            }
    }

    /**
     * Test d'assignar un valor a una casella blanca d'un Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testset_value() throws ExceptionKakuro {
        System.out.println("With this kakuro you can test all the exceptions:");
        Partida p = new Partida(5,5,prueba2);
        String[][] matrix = p.writeKakuro(1);
        Integer [] size = {5,5};
        print(size,matrix);
        System.out.println("USAGE: (row) (col) (value) ");
        Scanner reader = new Scanner(System.in);
        int r = reader.nextInt();
        int c = reader.nextInt();
        int value = reader.nextInt();
        p.setValue(r,c,value);
    }

    /**
     * Test per escriure la solucio d'una Partida
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testget_sol() throws ExceptionKakuro {
        System.out.println("With this kakuro you can test all the exceptions:");
        Partida p = new Partida(5,5,prueba2);
        String[][] matrix = p.writeKakuro(1);
        Integer [] size = {5,5};
        print(size,matrix);
        System.out.println("USAGE: (row) (col) ");
        Scanner reader = new Scanner(System.in);
        int r = reader.nextInt();
        int c = reader.nextInt();
        int result = p.send_help(r,c);
        if (result > 0) System.out.println(result);
    }

    /**
     * Test per escriure la solucio d'un Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testsol() throws ExceptionKakuro{
        System.out.println("enter the number of the test set (2,3)");
        Partida p;
        Scanner reader = new Scanner(System.in);
        int test_set = reader.nextInt();

        if (test_set == 2) {
            p = new Partida(5,5,prueba2);
            String[][] matrix = p.writeKakuro(0);
            Integer [] size = {5,5};
            print(size,matrix);
        }
        else if (test_set == 3) {
            p = new Partida(13,13,prueba3);
            Integer [] size = {13,13};
            String[][] matrix = p.writeKakuro(0);
            print(size,matrix);
        }


    }
    /**
     * Test per generar un kakuro aleatori
     */
    private void testgenerate() {
        System.out.println("Introduce (n m difficulty):");
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        int m = reader.nextInt();
        int diff = reader.nextInt();
        Integer[] size = {n,m};
        Partida p = new Partida(n,m,diff);
        print(size,p.writeKakuro(1));

    }

    private void testgetdifficulty() throws ExceptionKakuro {
        int test_set;
        Scanner reader = new Scanner(System.in);
        System.out.println("enter the number of the test set (2,3)");
        test_set = reader.nextInt();
        Partida p;

        if (test_set == 2) {
            p = new Partida(5,5,prueba2);
            Integer[] s = p.sizeKakuro();
            System.out.println(s[0] + "x" + s[1]);
        }
        else if (test_set == 3) {
            p = new Partida(13,13,prueba3);
            int diff = p.get_difficulty();
            System.out.println("difficulty = " + diff);
        }
    }

    /**
     * Imprimir el Kakuro
     * @param size La mida en un Integer "nm", on n son les files i m son les columnes
     * @param matrix El kakuro en forma de matriu String[][]
     */
    private void print (Integer[] size, String[][] matrix) {
        for (int i = 0; i < size[0]; ++i) {
            for (int j = 0; j < size[1]; ++j) {
                System.out.print(matrix[i][j] + ' ');
            }
            System.out.println();
        }
    }


    //*********jocs de proves********

    //prova1
    /**
     * Kakuro 3x3 amb mes d'una solucio
     */
    String[][] prueba1 = new String[][] { new String[]{"*", "C4", "C5"},
                                            new String[] {"F4", "?", "?"},
                                            new String[] {"F5", "?", "?"}};

    //prova2
    /**
     * Kakuro 5x5 amb una unica solucio
     */
    String[][] prueba2 = new String[][] { new String[]{"*", "*", "*", "C24", "C7"},
                                            new String[] {"*", "C6", "C24F8", "?", "?"},
                                            new String[] {"F18", "?", "?", "?", "?"},
                                            new String[]{"F23", "?", "?", "?", "?"},
                                            new String[] { "F12", "?", "?", "*", "*"}};

    //prova3
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
}








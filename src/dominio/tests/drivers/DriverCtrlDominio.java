/**
 * @file ./src/dominio/tests/drivers/DriverCtrlDominio.java
 * @author Gerard Caravaca
 */

package src.dominio.tests.drivers;

import src.dominio.clases.ExceptionKakuro;
import src.dominio.controladores.CtrlDominio;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Scanner;


public class DriverCtrlDominio {

    /**
     * Constructora per defecte de DriverCtrlDominio
     */
    public void DriverCtrlDominio() {};

    /**
     * Main del DriverCtrlDominio
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     * @throws FileNotFoundException if file does not exist.
     */
    public static void main(String[] args) throws Exception {
        src.dominio.tests.drivers.DriverCtrlDominio dc = new src.dominio.tests.drivers.DriverCtrlDominio();
        Scanner reader = new Scanner(System.in);
        int op;
        printUsage();
        op = reader.nextInt();
        while (op != 0) {

            switch (op) {
                case 1:{
                    dc.testIniPartida();
                } break;
                case 2: {
                    dc.testIniPartidaRepo();
                } break;
                case 3:{
                    dc.testIniKakuro();
                } break;
                case 4:{
                    dc.testSize();
                } break;
                case 5:{
                    dc.testWrite();
                } break;
                case 6:{
                    dc.testlistofiles();
                }break;
                case 7:{
                    dc.testSetvalue();
                }break;
                case 8:{
                    dc.testHelp();
                }break;
                case 9:{
                    dc.testValidategame();
                }break;
                case 10:{
                    dc.testGetsol();
                }break;
                case 11:{
                    dc.testSave();
                }break;
                case 12:{
                    dc.testDelete();
                }break;
                case 13: {
                    dc.testgenerate();
                }break;
                case 14: {
                    dc.testhaspartida();
                }break;
                case 15: {
                    dc.testgetDuration();
                }break;
                case 16: {
                    dc.testexistsUser();
                }break;
                case 17: {
                    dc.testsaveUser();
                }break;
                case 18: {
                    dc.testdeleteUser();
                }break;
                case 19: {
                    dc.testgetpassword();
                }break;
                case 20: {
                    dc.testgetdifficulty();
                }break;
                case 21: {
                    dc.testgetuserstatistics();
                }break;
                case 22: {
                    dc.testupdatestats();
                }break;
                default:
                    throw new IllegalStateException("Unexpected value: " + op);

            }
            printUsage();
            op = reader.nextInt();

        }
    }

    /**
     * Metode per imprimir les comandes disponibles per l'usuari
     */
    private static void printUsage() {
        System.out.println("*****************************************");
        System.out.println("Driver of the CtrlDomain class, what do you want to test?");
        System.out.println("1. Start a new game");
        System.out.println("2. Start a new game from the repository");
        System.out.println("3. Inicialize a kakuro");
        System.out.println("4. Game size");
        System.out.println("5. Get kakuro matrix");
        System.out.println("6. Request file list");
        System.out.println("7. Play inserting a single value");
        System.out.println("8. Play asking for a hint");
        System.out.println("9. Validate game");
        System.out.println("10. Get solution");
        System.out.println("11. Save game");
        System.out.println("12. Delete game");
        System.out.println("13. Generate random game");
        System.out.println("14. Ask about saved game");
        System.out.println("15. Get game duration");
        System.out.println("16. Ask about the existence of a user in the system");
        System.out.println("17. Save new user");
        System.out.println("18. Delete user");
        System.out.println("19. Get password");
        System.out.println("20. Get game difficulty");
        System.out.println("21. Get User statistics");
        System.out.println("22. Update User statistics");
        System.out.println("------");
        System.out.println("0. SORTIR");
        System.out.println("*****************************************");

    }

    /**
     * Test per inicialitzar una Partida
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testIniPartida() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        System.out.println("Enter a test set number (1,2,3)");
        int test_set;
        Scanner reader = new Scanner(System.in);
        test_set = reader.nextInt();
        switch(test_set) {
            case 1:{
                c.readPartida(3,3,prueba1);
                System.out.println("ok");
            }break;
            case 2:{
                c.readPartida(5,5,prueba2);
                System.out.println("ok");
            }break;
            case 3:{
                c.readPartida(13,13,prueba3);
                System.out.println("ok");
            }break;
            default:
                throw new IllegalStateException("Unexpected value: " + test_set);
        }

    }

    /**
     * Test Getter mida del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testSize() throws ExceptionKakuro {

        CtrlDominio c = new CtrlDominio();
        System.out.println("Enter a test set number (2,3)");
        int test_set;
        Scanner reader = new Scanner(System.in);
        test_set = reader.nextInt();
        Integer[] size;
        switch(test_set) {
            case 2:{
                c.readPartida(5,5,prueba2);
                size = c.sizeKakuro();
            }break;
            case 3:{
                c.readPartida(13,13,prueba3);
                size = c.sizeKakuro();
            }break;
            default:
                throw new IllegalStateException("Unexpected value: " + test_set);
        }
        System.out.println(size[0] + "x" + size[1]);

    }

    /**
     * Test d'escriure un Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testWrite() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        System.out.println("Enter a test set number (2,3)");
        int test_set;
        Scanner reader = new Scanner(System.in);
        test_set = reader.nextInt();
        String[][] k;
        Integer[] size;
        switch (test_set) {
            case 2: {
                c.readPartida(5, 5, prueba2);
            }
            break;
            case 3: {
                c.readPartida(13, 13, prueba3);
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + test_set);
        }
        k = c.writeKakuro();
        size = c.sizeKakuro();
        printmatrix(size, k);
    }

    /**
     * Test d'obtenir la llista de fitxers
     */
    private void testlistofiles()  {
        CtrlDominio c = new CtrlDominio();
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter difficulty (1,2,3) or 0 for all the files");
        int diff = reader.nextInt();
        String[] s = c.getListOfFiles(diff);
        printstring(s);
    }

    /**
     * Test d'inicialitzar una Partida amb un Kakuro guardat al repositori
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     * @throws FileNotFoundException if file does not exist.
     */
    private void testIniPartidaRepo() throws FileNotFoundException, ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a difficulty number (1,2,3) or 0 if you want to see all");
        int diff = reader.nextInt();
        printstring(c.getListOfFiles(diff));
        System.out.println("enter a file ID number");
        int n = reader.nextInt();
        c.readRepoKakuro(n,diff);
        System.out.println("ok");
    }

    /**
     * Test d'inicialitzar un Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testIniKakuro() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        System.out.println("Enter a test set number (1,2,3)");
        int test_set;
        Scanner reader = new Scanner(System.in);
        test_set = reader.nextInt();
        switch(test_set) {
            case 1:{
                c.readKakuro(3,3,prueba1);
                System.out.println("ok");
            }break;
            case 2:{
                c.readKakuro(5,5,prueba2);
                System.out.println("ok");
            }break;
            case 3:{
                c.readKakuro(13,13,prueba3);
                System.out.println("ok");
            }break;
            default:
                throw new IllegalStateException("Unexpected value: " + test_set);
        }


    }

    /**
     * Test d'assignar un valor a una casella blanca d'un Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testSetvalue() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        int row,col,value;
        System.out.println("With this kakuro you can test all the exceptions:");
        c.readPartida(5,5,prueba2);
        Integer[] size = {5, 5};
        printmatrix(size, c.writeKakuro());
        System.out.println("USAGE: (row) (column) (value)");
        Scanner reader = new Scanner(System.in);
        row = reader.nextInt();
        col = reader.nextInt();
        value = reader.nextInt();
        c.setValue(row,col,value);
        printmatrix(size, c.writeKakuro());

    }

    /**
     * Test de Donar una pista de la casella amb fila r i columna c del Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testHelp() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        int row,col,value;
        System.out.println("With this kakuro you can test all the exceptions:");
        c.readPartida(5,5,prueba2);
        Integer[] size = {5, 5};
        printmatrix(size, c.writeKakuro());
        System.out.println("USAGE: (row) (column)");
        Scanner reader = new Scanner(System.in);
        row = reader.nextInt();
        col = reader.nextInt();
        int h = c.sendHelp(row,col);
        System.out.println(h);
    }

    /**
     * Test per validar si el Kakuro te una unica solucio
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testValidategame() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        System.out.println("Enter a test set number (1,2,3)");
        int test_set;
        Scanner reader = new Scanner(System.in);
        test_set = reader.nextInt();
        switch(test_set) {
            case 1:{
                c.readPartida(3,3,prueba1);
                if (c.validarPartida()) System.out.println("ok");
            }break;
            case 2:{
                c.readPartida(5,5,prueba2);
                if (c.validarPartida()) System.out.println("ok");
            }break;
            case 3:{
                c.readPartida(13,13,prueba3);
                if (c.validarPartida()) System.out.println("ok");

            }break;
            default:
                throw new IllegalStateException("Unexpected value: " + test_set);
        }

    }

    /**
     * Test d'obtenir la solucio d'un Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     * @throws FileNotFoundException if file does not exist.
     */
    private void testGetsol() throws FileNotFoundException, ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        String[][] k;
        Integer[] size;
        c.readPartida(5, 5, prueba2);
        k = c.writeKakuroSol();
        size = c.sizeKakuro();
        printmatrix(size, k);
    }

    /**
     * Test de guardar un Kakuro en un fitxer
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    private void testSave() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        String name;
        Scanner reader = new Scanner(System.in);
        c.readPartida(13, 13, prueba3);
        System.out.println("Introduce a name");
        name = reader.next();
        try {
            c.saveKakuro(name,false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("New State");
        printstring(c.getListOfFiles(0));
    }

    /**
     * Test d'eliminar un fitxer
     * @throws FileNotFoundException if file does not exist.
     */
    private void testDelete() throws FileNotFoundException {
        CtrlDominio c = new CtrlDominio();
        printstring(c.getListOfFiles(0));
        System.out.println("enter a file ID number");
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        try {
            c.deleteKakuro(n);
        } catch (Exception exceptionKakuro) {
            exceptionKakuro.printStackTrace();
        }
    }

    /**
     * Test per generar una partida amb un kakuro aleatori
     */
    private void testgenerate() {
        CtrlDominio c = new CtrlDominio();
        System.out.println("Introduce (n m diff):");
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        int m = reader.nextInt();
        int diff = reader.nextInt();
        Integer[] size = {n,m};
        c.generatePartida(n,m,diff);
        printmatrix(size,c.writeKakuro());

    }
    /**
     * Test de la funcio hasPartida
     */
    private void testhaspartida() {
        CtrlDominio c = new CtrlDominio();
        String user;
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce an username");
        user = reader.next();
        if (c.hasPartida(user)) System.out.println(user + " has a saved game");
        else System.out.println(user + " does not have a saved game");
    }
    /**
     * Test del getter de la duracio de una partida
     * @throws ExceptionKakuro si el kakuro de la partida no es valid
     */
    private void testgetDuration() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        System.out.println("starting game");
        c.readPartida(5,5,prueba2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long time = c.getDuration();
        System.out.println("game duration : " + time + " ms");
    }
    /**
     * Test de la funcio existsUser()
     */
    private void testexistsUser() {
        CtrlDominio c = new CtrlDominio();
        String user;
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce an username");
        user = reader.next();
        if (c.existsUser(user)) System.out.println(user + " exists");
        else System.out.println(user + " does not exists");
    }
    /**
     * Test de la funcio per guardar un usuari
     */
    private void testsaveUser() throws Exception {
        CtrlDominio c = new CtrlDominio();
        String user;
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce an username");
        user = reader.next();
        String password;
        System.out.println("Introduce a password");
        password = reader.next();
        if (c.existsUser(user)) System.out.println(user + " already exists");
        else
        {
            System.out.println(user + " does not exists");
            c.saveUser(user,password);
            System.out.println("user saved correctly");
        }
        if (c.existsUser(user)) System.out.println(user + " already exists now");
    }
    /**
     * Test de la funcio per eliminar un usuari
     * @throws Exception si fitxer de usuari no es troba
     */
    private void testdeleteUser() throws Exception {
        CtrlDominio c = new CtrlDominio();
        String user;
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce an username");
        user = reader.next();
        String password;
        System.out.println("Introduce the password");
        password = reader.next();
        if (c.existsUser(user)) {
            System.out.println(user + " already exists");
            c.deleteUser(user,password);
            System.out.println("user deleted correctly");
        }
        else
        {
            System.out.println(user + " does not exists");
        }
        if (c.existsUser(user)) System.out.println(user + " already exists now");
        else System.out.println(user + " does not exists now");
    }
    /**
     * Test del getter de la contrasenya de un usuari
     * @throws FileNotFoundException si fitxer de usuari no es troba
     */
    private void testgetpassword() throws FileNotFoundException {
        CtrlDominio c = new CtrlDominio();
        String user;
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce an username");
        user = reader.next();
        if (c.existsUser(user)) {
            String password = c.getPassword(user);
            System.out.println("Password: " + password);
        }
        else
        {
            System.out.println("User " + user + " does not exists");
        }

    }
    /**
     * Test del getter de la dificultat de una partida
     * @throws ExceptionKakuro si fel kakuro de la partida no es valid
     */
    private void testgetdifficulty() throws ExceptionKakuro {
        CtrlDominio c = new CtrlDominio();
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a test set number (2,3,4)");
        int test_set = reader.nextInt();
        switch(test_set) {

            case 2:{
                c.readPartida(5,5,prueba2);
            }break;
            case 3:{
                c.readPartida(13,13,prueba3);
            }break;
            case 4:{
                c.readPartida(9,9,prueba4);
            }break;
            default:
                throw new IllegalStateException("Unexpected value: " + test_set);
        }
        int diff = c.get_difficulty();
        System.out.println("Difficulty: " + diff);
    }
    /**
     * Test del getter de les estadistiques de un usuari
     */
    private void testgetuserstatistics()  {
        CtrlDominio c = new CtrlDominio();
        String user;
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce an username");
        user = reader.next();
        if (c.existsUser(user)) {
            String[] stats = c.getUserStatistics(user);
            printstats(stats);
        }
        else
        {
            System.out.println("User " + user + " does not exists");
        }

    }
    /**
     * Test de la funcio updateStatistics()
     */
    private void testupdatestats() {
        CtrlDominio c = new CtrlDominio();
        String user;
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce an username");
        user = reader.next();
        if (c.existsUser(user)) {
            System.out.println("Current statistics");
            String[] stats = c.getUserStatistics(user);
            printstats(stats);
            System.out.println();
            System.out.println("Enter data of statistics you want to update");
            System.out.println("help(0,1) duration(in seconds) difficulty(1,2,3)");
            int h = reader.nextInt();
            int s = reader.nextInt();
            int diff = reader.nextInt();
            boolean help = true;
            if (h == 0) help = false;
            Duration duration = Duration.ofSeconds(s);
            c.updateStatistics(user,help,duration,diff);
            System.out.println("New statistics");
            printstats(c.getUserStatistics(user));
        }
        else
        {
            System.out.println("User " + user + " does not exists");
        }
    }
    /**
     * Imprimir el Kakuro
     * @param size La mida en un Integer "nm", on n son les files i m son les columnes
     * @param matrix El kakuro en forma de matriu String[][]
     */
    private void printmatrix (Integer[] size, String[][] matrix) {
        for (int i = 0; i < size[0]; ++i) {
            for (int j = 0; j < size[1]; ++j) {
                System.out.print(matrix[i][j] + ' ');
            }
            System.out.println();
        }
    }

    /**
     * Imprimir la llista de noms de fitxers
     * @param list La llista de noms de fitxers
     */
    private void printstring (String[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.println(i + "-->" + list[i]);
        }
    }
    /**
     * Funcio per Imprimir estadistiques
     * @param statistics La llista de estadistiques a imprimir
     */
    private void printstats (String[] statistics) {
        System.out.println("Easy Kakuros solved: " + statistics[0]);
        System.out.println("Medium Kakuros solved: " + statistics[1]);
        System.out.println("Hard Kakuros solved: " + statistics[2]);
        System.out.println("EasyKakuros solved without help: " + statistics[3]);
        System.out.println("Medium Kakuros solved without help: " + statistics[4]);
        System.out.println("Hard Kakuros solved without help: " + statistics[5]);
        System.out.print("Average time to solve a kakuro without help: ");
        int hours, minutes, seconds, totalGames;
        totalGames = Integer.parseInt(statistics[3]) + Integer.parseInt(statistics[4]) + Integer.parseInt(statistics[5]);
        if (totalGames != 0) {
            hours = Integer.parseInt(statistics[6]);
            minutes = Integer.parseInt(statistics[7]);
            seconds = Integer.parseInt(statistics[8]);
            seconds = (hours * 3600 + minutes * 60 + seconds) / totalGames;
            if (seconds / 3600 < 10) {
                System.out.print("0" + seconds / 3600 + ":");
            } else {
                System.out.print(seconds / 3600 + ":");
            }

            if ((seconds % 3600) / 60 < 10) {
                System.out.print("0" + (seconds % 3600) / 60 + ":");
            } else {
                System.out.print((seconds % 3600) / 60 + ":");
            }

            if ((seconds % 3600) % 60 < 10) {
                System.out.println("0" + (seconds % 3600) % 60);
            } else {
                System.out.println((seconds % 3600) % 60);
            }
        } else System.out.println("00:00:00");
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
     * Kakuro 5x5 amb una unica solucio facil
     */
    String[][] prueba2 = new String[][] { new String[]{"*", "*", "*", "C24", "C7"},
            new String[] {"*", "C6", "C24F8", "?", "?"},
            new String[] {"F18", "?", "?", "?", "?"},
            new String[]{"F23", "?", "?", "?", "?"},
            new String[] { "F12", "?", "?", "*", "*"}};

    //prova3
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

}
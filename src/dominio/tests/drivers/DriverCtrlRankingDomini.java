/**
 * @file ./src/dominio/tests/drivers/DriverCtrlRankingDomini.java
 * @author Gerard Caravaca
 */
package src.dominio.tests.drivers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;
import src.dominio.controladores.CtrlRankingDomini;



/**
 * @class DriverCtrlDominio
 * @brief Classe que representa el Driver de CtrlRankingDomini.
 */
public class DriverCtrlRankingDomini {
    /**
     * Constructora per defecte de DriverCtrlDominio
     */
    public void DriverCtrlRankingDomini() {};

    /**
     * Main del DriverCtrlRankingDomini
     */
    public static void main(String[] args) throws Exception {
        DriverCtrlRankingDomini dc = new DriverCtrlRankingDomini();
        Scanner reader = new Scanner(System.in);
        int op;
        printUsage();
        op = reader.nextInt();
        while (op != 0) {

            switch (op) {
                case 1:{
                    dc.testIniCtrl();
                } break;
                case 2: {
                    dc.testUpdate();
                } break;
                case 3:{
                    dc.testgetRank();
                } break;
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
        System.out.println("Driver of the CtrlRankingDomini class, what do you want to test?");
        System.out.println("1. Initialize CtrlRankingDomini");
        System.out.println("2. Update Ranking status");
        System.out.println("3. Show the Ranking status");
        System.out.println("------");
        System.out.println("0. EXIT");
        System.out.println("*****************************************");

    }

    /**
     * Test per inicialitzar CtrlRankingDomini
     */
    private void testIniCtrl()  {
        CtrlRankingDomini r = new CtrlRankingDomini();
        r.inicializarCtrlRankingDomini();
        System.out.println("Correctly initialized controller");
    }

    /**
     * Test per la funcio update
     */
    private void testUpdate() throws Exception {
        CtrlRankingDomini r = new CtrlRankingDomini();
        r.inicializarCtrlRankingDomini();
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce time in seconds and difficulty(1,2,3)");
        long seconds = reader.nextInt();
        int diff = reader.nextInt();
        Duration d = Duration.ofSeconds(seconds);
        System.out.println("Current status:");
        printRanking(r.getRankingPerDiff(diff));
        r.updateRanking("tester",d,diff);
        System.out.println("New status:");
        printRanking(r.getRankingPerDiff(diff));
    }
    /**
     * Test per la funcio getRankingPerDiff
     */
    private void testgetRank() throws Exception {
        CtrlRankingDomini r = new CtrlRankingDomini();
        r.inicializarCtrlRankingDomini();
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduce difficulty(1,2,3)");
        int diff = reader.nextInt();
        printRanking(r.getRankingPerDiff(diff));
    }

    /**
     * Funcio per Imprimir el ranking per terminal
     * @param ranking arraylist que volem imprimir
     */
    public void printRanking(ArrayList<ArrayList<String>> ranking) {
        for (int i = 0; i < ranking.size(); ++i) {
            for (int j = 0; j < ranking.get(i).size(); ++j) {
                if (j == 0) System.out.print(i + 1 + ". " + ranking.get(i).get(j) + ": ");
                else if (j == 1 || j == 2) {
                    if (Integer.parseInt(ranking.get(i).get(j)) < 10) {
                        System.out.print("0" + ranking.get(i).get(j) + ":");
                    } else {
                        System.out.print(ranking.get(i).get(j) + ":");
                    }
                } else {
                    if (Integer.parseInt(ranking.get(i).get(j)) < 10) {
                        System.out.println("0" + ranking.get(i).get(j));
                    } else {
                        System.out.println(ranking.get(i).get(j));
                    }
                }
            }
        }
    }


}

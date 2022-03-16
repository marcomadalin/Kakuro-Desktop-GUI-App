/**
 * @file ./src/dominio/tests/drivers/DriverSortrbyTime.java
 * @author Gerard Caravaca
 */
package src.dominio.tests.drivers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import src.dominio.clases.SlotRanking;
import src.dominio.clases.SortbyTime;
/**
 * @class DriverSortbyTime
 * @brief Classe que representa el Driver de SortrbyTime.
 */
public class DriverSortrbyTime {
    /**
     * Constructora per defecte de DriverSortrbyTime
     */
    public void DriverSortbyTime() {};

    /**
     * Main del DriverSortrbyTime
     */
    public static void main(String[] args) {
        DriverSortrbyTime dc = new DriverSortrbyTime();
        Scanner reader = new Scanner(System.in);
        int op;
        printUsage();
        op = reader.nextInt();
        while (op != 0) {

            switch (op) {
                case 1:{
                    dc.testcompare();
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
        System.out.println("Driver of the SortbyTime class, what do you want to test?");
        System.out.println("1. That the function compares the slots in the correct order");
        System.out.println("------");
        System.out.println("0. EXIT");
        System.out.println("*****************************************");

    }

    /**
     * Test per inicialitzar CtrlRankingDomini
     */
    private void testcompare() {
        SlotRanking s1 = new SlotRanking();
        SlotRanking s2 = new SlotRanking();
        System.out.println("Enter the parameters of the first Slot:");
        System.out.println("user:");
        Scanner reader = new Scanner(System.in);
        s1.setUser(reader.next());
        System.out.println("hours minutes seconds:");
        s1.setHours(reader.nextInt());
        s1.setMinutes(reader.nextInt());
        s1.setSeconds(reader.nextInt());
        System.out.println("Enter the parameters of the second Slot:");
        System.out.println("user:");
        s2.setUser(reader.next());
        System.out.println("hours minutes seconds:");
        s2.setHours(reader.nextInt());
        s2.setMinutes(reader.nextInt());
        s2.setSeconds(reader.nextInt());
        ArrayList<SlotRanking> l = new ArrayList<>();
        l.add(s1);
        l.add(s2);
        Collections.sort(l, new SortbyTime());
        printcomparison(l);

    }
    /**
     * Funcio auxiliar per pintar per pantalla un SlotRanking
     */
    private void printslot(SlotRanking s) {
        System.out.println(s.getUser() + " " + s.getHours() + ":" + s.getMinutes() + ":" + s.getSeconds());
    }
    /**
     * Funcio auxiliar per pintar per pantalla un array de SlotRanking
     */
    private void printcomparison(ArrayList<SlotRanking> l) {
        System.out.println("Increasing order by time:");
        System.out.print("1. ");
        printslot(l.get(0));
        System.out.print("2. ");
        printslot(l.get(1));
    }

}

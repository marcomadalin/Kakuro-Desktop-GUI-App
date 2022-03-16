/**
 * @file ./src/Main.java
 * @author Sergi Berdor
 */

package src;


import src.presentacion.controladores.CtrlPresentacion;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 @class Main
 @brief Punt d'inici del thread que executa el programa Kakuro.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        if (args.length == 0 || (Integer.parseInt(args[0]) != 0 && Integer.parseInt(args[0]) != 1))  {
            System.out.println("USAGE: java Main opt");
            System.out.println("opt = 0 -> GUI execution");
            System.out.println("opt = 1 -> Console execution");
        }
        else {
            int gui = Integer.parseInt(args[0]);
            CtrlPresentacion var1 = new CtrlPresentacion();
            if (gui == 0) var1.inicializarPresentacionGUI();
            else {
                var1.inicializarPresentacionTerminal();
            }
        }
    }

}

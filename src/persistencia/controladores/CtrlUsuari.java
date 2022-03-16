/**
 * @file ./src/persistencia/controladores/CtrlUsuari.java
 * @author
 */

package src.persistencia.controladores;

import java.io.*;
import java.util.Scanner;
import java.time.Duration;

/**
 * @class CtrlUsuari
 * @brief Classe del Controlador de Usuari. S'encarrega de la comunicació amb CtrlDominio i de la gestio d'usuaris.
 */
public class CtrlUsuari {

    //ATRIBUTS

    /**
     * Path dels usuaris
     */
    private String path = "./data/usuaris/";

    /**
     * La classe CtrlUsuari és Singleton
     */
    private static CtrlUsuari singletonObject;

    //CONSTRUCTORS

    /**
     * Constructora per defecte de CtrlUsuari
     */
    private CtrlUsuari() {}

    //GETTERS I SETTERS

    /**
     * Getter per defecte del CtrlUsuari, si no existeix el crea
     * @return Retorna el CtrlUsuari
     */
    public static CtrlUsuari getInstance() {
        if (singletonObject == null) {
            singletonObject = new CtrlUsuari() {
            };
        }

        return singletonObject;
    }

    //METODES

    /**
     * Saber si un usuari existeix o no
     * @param name El nom del fitxer
     * @return Retorna true si el fitxer existeix, altrament retorna false
     */
    public boolean userExists(String name) {
        File existsName = new File(path+name+".txt");
        return existsName.exists();
    }

    /**
     * Guardar un usuari en un fitxer
     * @param name El nom del fitxer a crear
     * @param password La contrasenya de l'usuari
     */
    public void saveUser(String name, String password) throws Exception {
        //update();
        if (userExists(name)) {
            throw new Exception("ERROR:There is already an user with this name");
        }
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            String filename = path + name + ".txt";
            file = new FileWriter(filename);
            pw = new PrintWriter(file);
            pw.println(password);

            pw.println();
            pw.println("0");
            pw.println("0");
            pw.println("0");
            pw.println("0");
            pw.println("0");
            pw.println("0");
            pw.println("0");
            pw.println("0");
            pw.println("0");

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (null != file) file.close();
                if (null != pw) pw.close();
            } catch (Exception e2) {
                throw new Exception(e2.getMessage());
            }

        }
    }

    /**
     * Eliminar un usuari
     * @param name El nom de l'usuari a eliminar
     * @param password La contrasenya de l'usuari
     * @return Retorna true si s'ha eliminat l'usuari, altrament retorna false
     */
    public void deleteUser(String name, String password) throws Exception {
        //update();
        if (!userExists(name)) throw new Exception("ERROR:An user with this name does not exist");

        else {
            File pathuser = new File(path + name + ".txt");
            String pass = getPassword(name);
            if (password.equals(pass)) {
                if (!pathuser.delete()) throw new Exception("File cannot be deleted");
            }
            else {
                throw new Exception("ERROR:Incorrect password");
            }

        }
    }

    /**
     * Getter de la constrasenya de l'usuari
     * @param name El nom de l'usuari
     * @return Retorna la contrasenya de l'usuari
     */
    public String getPassword(String name) throws FileNotFoundException {

        Scanner in = new Scanner(new FileReader(path + name + ".txt"));
        String password = in.next();
        in.close();

        return password;
    }

    /**
     * Actualitzar estadistiques d'usuari
     * @param user El nom de l'usuari
     * @param ajuda Si ha demanat ajuda o no
     * @param d El temps que ha tardat en resoldre el Kakuro
     * @param diff La dificultat del Kakuro resolt
     */
    public void updateStatistics (String user, boolean ajuda, Duration d, int diff) {
        String filename = path + user + ".txt";
        String filetemp = path + user + ".temp";

        PrintWriter pw = null;
        BufferedReader reader = null;
        try {
            int j, k;
            if (diff == 1) {
                j = 2;
                k = 5;
            } else if (diff == 2) {
                j = 3;
                k = 6;
            } else {
                j = 4;
                k = 7;
            }
            pw = new PrintWriter(new FileWriter(filetemp));
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            int i = 0, number;
            int hours, minutes, seconds, newSeconds;

            while (line != null) {
                if (i == j) {
                    number = Integer.parseInt(line);
                    ++number;
                    pw.println(number);
                } else if (i == k && !ajuda) {
                    number = Integer.parseInt(line);
                    ++number;
                    pw.println(number);
                } else if (i == 8 && !ajuda) {
                    hours = Integer.parseInt(line);
                    ++i;
                    line = reader.readLine();
                    minutes = Integer.parseInt(line);
                    ++i;
                    line = reader.readLine();
                    seconds = Integer.parseInt(line);
                    seconds = hours * 3600 + minutes * 60 + seconds;
                    newSeconds = (int) d.getSeconds();
                    newSeconds += seconds;

                    pw.println(newSeconds/3600);
                    pw.println((newSeconds%3600)/60);
                    pw.println((newSeconds%3600)%60);
                }
                else pw.println(line);

                ++i;
                line = reader.readLine();
            }
            reader.close();
            pw.close();

            File realName = new File(filename);
            realName.delete();
            new File(filetemp).renameTo(realName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter de les estadistiques d'usuari
     * @param user El nom de l'usuari
     * @return Retorna les estadistiques de l'usuari
     */
    public String[] getUserStatistics(String user) {
        String filename = path + user + ".txt";
        BufferedReader reader = null;
        String[] statistics = new String[9];
        try {
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            int i = 0;

            while (line != null) {
                if (i >= 2) statistics[i-2] = line;
                ++i;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return statistics;
        }
    }
}

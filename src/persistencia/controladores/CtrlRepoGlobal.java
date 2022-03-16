/**
 * @file ./src/persistencia/controladores/CtrlRepoGlobal.java
 * @author Sergi Berdor
 */

package src.persistencia.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @class CtrlRepoGlobal
 * @brief Classe del Controlador de Repositori Global. S'encarrega de la comunicació amb CtrlDominio i de
 * l'emmagatzematge de dades.
 */
public class CtrlRepoGlobal {

    //ATRIBUTS

    /**
     * Path del repositori amb tots els kakuros i dificultats
     */

    final private String[] paths = {"./data/repo/global/todos/",
            "./data/repo/global/facil/",
            "./data/repo/global/medio/",
            "./data/repo/global/dificil/"};

    final private String pathPartida = "./data/repo/partidas/";
    final private String pathSolutions = "./data/repo/solution/";

    /**
     * Llista de fitxers existents en el path
     */
    private File[] listOfFiles;

    /**
     * La classe CtrlRepoGlobal és Singleton
     */
    private static CtrlRepoGlobal singletonObject;

    //CONSTRUCTORS

    /**
     * Constructora per defecte de CtrlRepoGlobal
     */
    private CtrlRepoGlobal() {
        update(0);
    }

    //GETTERS I SETTERS

    /**
     * Getter per defecte del CtrlRepoGlobal, si no existeix el crea
     * @return Retorna el CtrlRepoGlobal
     */
    public static CtrlRepoGlobal getInstance() {
        if (singletonObject == null) {
            singletonObject = new CtrlRepoGlobal() {
            };
        }

        return singletonObject;
    }

    /**
     * Getter per defecte del listOfFiles de CtrlRepoGlobal
     * @param diff dificultat de la carpeta que es volen veure els arxius
     * @return Retorna el nom de tots els fitxers existents en el path segons diff
     */
    public String[] listOfFiles(int diff) {
        update(diff);
        String[] files = new String[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            String fichero = listOfFiles[i].getName();
            files[i] = fichero.substring(0, fichero.length()-4);
        }
        return files;
    }

    //METODES

    /**
     * Actualitzar listOfFiles segons la carpeta de dificultat
     * @param diff dificultat de la carpeta que es volen veure els arxius
     */
    private void update(int diff) {
        File folder = new File(paths[diff]);
        listOfFiles = folder.listFiles();
    }

    /**
     * Obtenir la mida del Kakuro des d'un fitxer del repositori global
     * @param num El numero del fitxer a obrir
     * @param diff dificultat de la carpeta que es volen veure els arxius
     * @return Retorna la mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @throws FileNotFoundException if file does not exist.
     */
    public Integer[] openFileSize(int num, int diff) throws FileNotFoundException {
        update(diff);
        File f;
        f = new File(String.valueOf(listOfFiles[num]));
        Scanner reader = new Scanner(f);
        Integer[] size = new Integer[2];
        Scanner sc = new Scanner(reader.next());
        sc.useDelimiter(",| +\n");

        size[0] = sc.nextInt();
        size[1] = sc.nextInt();
        reader.close();
        return size;
    }

    /**
     * Obtenir el kakuro en forma de matriu String[][] des d'un fitxer del repositori global
     * @param size La mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @param num El numero del fitxer a obrir
     * @param diff dificultat de la carpeta que es volen veure els arxius
     * @return Retorna el kakuro en forma de matriu String[][]
     * @throws FileNotFoundException if file does not exist.
     */
    public String[][] openFileMatrix(Integer[] size, int num, int diff) throws FileNotFoundException {
        update(diff);
        String s;
        File f;
        f = new File(String.valueOf(listOfFiles[num]));
        Scanner reader = new Scanner(f);
        reader.nextLine();
        reader.useDelimiter(",| |\n");
        String[][] matrix = new String[size[0]][size[1]];
        for (int i = 0; i < size[0]; ++i) {
            for (int j = 0; j < size[1]; ++j) {
                s = reader.next();
                matrix[i][j] = s;
            }
            s = reader.next();
        }
        reader.close();
        return matrix;
    }

    /**
     * Saber si un fitxer amb el mateix nom existeix o no al repositori global
     * @param name El nom del fitxer
     * @return Retorna true si el fitxer ja existeix, altrament retorna false
     */
    public boolean fileExists(String name) {
        File existsName = new File(paths[0].concat(name).concat(".txt"));
        return existsName.exists();
    }

    /**
     * Guardar un Kakuro en un fitxer
     * @param name El nom del fitxer a crear
     * @param difficulty Difficultat del kakuro que es vol guardar
     * @param size La mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @param matrix El kakuro en forma de matriu String[][]
     * @throws Exception si existeix un kakuro amb el mateix nom al repo
     */
    public boolean saveKakuro(String name, int difficulty, Integer[] size, String[][] matrix) throws Exception {
        update(0);

        if (fileExists(name)) {
            throw new Exception("ERROR:There is already a kakuro with this name");
        }
        writeKakuro(paths[difficulty].concat(name).concat(".txt"), size, matrix,0, false);
        writeKakuro(paths[0].concat(name).concat(".txt"), size, matrix, 0, false);
        return true;
    }

    /**
     * Guardar un kakuro o partida al repositori
     * @param name El nom del fitxer a crear
     * @param size La mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @param matrix El kakuro en forma de matriu String[][]
     * @param d Si es vol guardar un kakuro d = 0, si es vol guardar una partida d > 0
     * @param ajuda Si s'ha demanat ajuda o no
     * @throws Exception si no s'ha guardat el kakuro correctament
     */
    private void writeKakuro(String name, Integer[] size, String[][] matrix, long d, boolean ajuda) throws Exception {
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(name);
            pw = new PrintWriter(file);

            if(d != 0) pw.println(Integer.toString(size[0]) + ',' + size[1] + ',' + d + ',' + ajuda + " ");
            else pw.println(Integer.toString(size[0]) + ',' + size[1] + " ");
            for (int i = 0; i < size[0]; ++i) {
                for (int j = 0; j < size[1]; ++j) {
                    pw.print(matrix[i][j]);
                    if(j != size[1]-1) pw.print(",");
                }
                pw.println(' ');
            }
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            try {
                if(null != file) file.close();
                if(null != pw) pw.close();
            } catch (Exception e2) {
                throw new Exception(e2.getMessage());
            }

        }
    }

    /**
     * Eliminar un fitxer que guarda un kakuro
     * @param i El numero del fitxer a eliminar
     * @param diff dificultat de la carpeta que es volen veure els arxius
     * @return Retorna true si s'ha eliminat el fitxer
     * @throws Exception si el fitxer no existeix o no s'ha pogut eliminar correctament
     */
    public boolean deleteKakuro(int i, int diff) throws Exception {
        if (i >= 0 && i < listOfFiles.length) {
            File name = new File(paths[0].concat(listOfFiles[i].getName()));
            File nameCopy = new File(paths[diff].concat(listOfFiles[i].getName()));
            File solCopy = new File(pathSolutions.concat("sol-").concat(listOfFiles[i].getName()));
            if (name.delete() && nameCopy.delete() && solCopy.delete()) {
                return true;
            }
            else {
                throw new Exception("File cannot be deleted");
            }
        }
        else {
            throw new Exception("Incorrect selection");
        }


    }

    /**
     * Guardar partida a mig jugar
     * @param user Usuari que esta jugant la partida
     * @param size La mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @param matrix El kakuro en forma de matriu String[][]
     * @param d Temps que el jugador porta jugant la partida (mills)
     * @param ajuda Si s'ha demanat ajuda o no
     * @throws Exception si no s'ha guardat el kakuro correctament
     */
    public void saveGame(String user, Integer[] size, String[][] matrix, long d, boolean ajuda) throws Exception {
        writeKakuro(pathPartida.concat(user).concat(".txt"), size, matrix, d, ajuda);
    }

    /**
     * Obtenir la mida de la partida des d'un fitxer
     * @param user Usuari de la partida a mig jugar que es vol llegir
     * @return Retorna la mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @throws FileNotFoundException if file does not exist.
     */
    public Integer[] openFileSizeGame(String user) throws FileNotFoundException {
        File f;
        f = new File(pathPartida.concat(user).concat(".txt"));
        Scanner reader = new Scanner(f);
        Integer[] size = new Integer[2];
        Scanner sc = new Scanner(reader.next());
        sc.useDelimiter(",| +\n");

        size[0] = sc.nextInt();
        size[1] = sc.nextInt();
        reader.close();
        return size;
    }

    /**
     * Obtenir el temps de la partida a mig jugar
     * @param user Usuari de la partida a mig jugar que es vol llegir
     * @return El temps que l'usuari porta jugant aquesta partida
     * @throws FileNotFoundException if file does not exist.
     */
    public long openFileTimeGame(String user) throws FileNotFoundException {
        File f;
        f = new File(pathPartida.concat(user).concat(".txt"));
        Scanner reader = new Scanner(f);
        Scanner sc = new Scanner(reader.next());
        sc.useDelimiter(",| +\n");
        sc.nextInt();sc.nextInt();
        long t = sc.nextLong();
        return t;
    }

    /**
     * Obtenir si s'ha demanat ajuda a la partida
     * @param user Usuari de la partida a mig jugar que es vol llegir
     * @return Retorna true si ha demanat ajuda, altrament retorna false
     * @throws FileNotFoundException if file does not exist.
     */
    public boolean openFileHelp(String user) throws FileNotFoundException {
        File f;
        f = new File(pathPartida.concat(user).concat(".txt"));
        Scanner reader = new Scanner(f);
        Scanner sc = new Scanner(reader.next());
        sc.useDelimiter(",| +\n");
        sc.nextInt();sc.nextInt();sc.nextLong();
        boolean b = sc.nextBoolean();
        return b;
    }


    /**
     * Obtenir el kakuro a mig jugar en forma de matriu String[][] des d'un fitxer
     * @param size La mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @param user Usuari de la partida a mig jugar que es vol llegir
     * @return Retorna el kakuro en forma de matriu String[][]
     * @throws FileNotFoundException if file does not exist.
     */
    public String[][] openFileMatrixGame(Integer[] size, String user) throws FileNotFoundException {
        String s;
        File f;
        f = new File(pathPartida.concat(user).concat(".txt"));
        Scanner reader = new Scanner(f);
        reader.nextLine();
        reader.useDelimiter(",| |\n");
        String[][] matrix = new String[size[0]][size[1]];
        for (int i = 0; i < size[0]; ++i) {
            for (int j = 0; j < size[1]; ++j) {
                s = reader.next();
                matrix[i][j] = s;
            }
            s = reader.next();
        }
        reader.close();
        return matrix;
    }

    /**
     * Mirar si l'usuari te una partida a meitat
     * @param user Usuari de la partida a mig jugar que es vol llegir
     * @return true si te una partida a mig jugar, fals si no en te
     */
    public boolean hasPartida(String user) {
        File f = new File(pathPartida.concat(user).concat(".txt"));
        return f.exists();
    }

    /**
     * Guardar la solucio del kakuro
     * @param name El nom del fitxer a crear
     * @param size La mida del Kakuro en un Integer "nm", on n son les files i m son les columnes
     * @param matrix El kakuro en forma de matriu String[][]
     * @throws Exception si no s'ha guardat el kakuro correctament
     */
    public void saveSolution(String name, Integer[] size, String[][] matrix) throws Exception {
        writeKakuro(pathSolutions.concat("sol-").concat(name).concat(".txt"), size, matrix,0, false);
    }


}

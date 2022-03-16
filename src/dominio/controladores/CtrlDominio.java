/**
 * @file ./src/dominio/controladores/CtrlDominio.java
 * @author Sergi Berdor
 */

package src.dominio.controladores;

import src.dominio.clases.*;
import src.dominio.clases.kakuro.*;
import src.persistencia.controladores.CtrlRepoGlobal;
import src.persistencia.controladores.CtrlUsuari;

import java.io.FileNotFoundException;
import java.time.Duration;

/**
 * @class CtrlDominio
 * @brief Classe del Controlador de Domini. Conte el nucli del programa, s'encarrega de la comunicacio amb
 * CtrlPresentacion i amb CtrlRepoGlobal i CtrlUsuari, del manteniment basic de les dades i de la implementacio d'una
 * part de les funcionalitats principals (casos d'us) que corresponen a la capa de domini.
 */
public class CtrlDominio {

    //ATRIBUTS

    /**
     * Controlador del Repositori Global
     */
    private CtrlRepoGlobal repo;

    /**
     * Controlador de Usuari
     */
    private CtrlUsuari usu;

    /**
     * La partida
     */
    private Partida partida;

    /**
     * El Kakuro
     */
    private Kakuro kakuro;

    //CONSTRUCTORS

    /**
     * Constructora per defecte
     */
    public CtrlDominio() {
        this.inicializarCtrlDominio();
    }

    //FUNCIONS

    /**
     * Inicialitzar Controlador de Domini
     */
    public void inicializarCtrlDominio() {
        this.repo = CtrlRepoGlobal.getInstance();
        this.usu = CtrlUsuari.getInstance();
    }

    /**
     * Crear nova Partida amb un nou Kakuro
     * @param n Les files del Kakuro de la Partida
     * @param m Les columnes de Kakuro de la Partida
     * @param matrix El kakuro de la Partida en forma de matriu String[][]
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    public void readPartida(int n, int m, String[][] matrix) throws ExceptionKakuro {
        this.partida = new Partida(n, m, matrix);
    }

    /**
     * Obtenir la llista de fitxers del Repositori Global
     * @return Retorna el conjunt de fitxer en un array de Strings
     */
    public String[] getListOfFiles(int diff) {
        return repo.listOfFiles(diff);
    }

    /**
     * Crear una nova partida a partir d'un Kakuro que esta guardat en un fitxer del repositori
     *
     * @param file El nom del fitxer
     * @throws FileNotFoundException if file does not exist.
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    public void readRepoKakuro(int file, int diff) throws FileNotFoundException, ExceptionKakuro {
        Integer[] size = repo.openFileSize(file, diff);
        String[][] matrix = repo.openFileMatrix(size, file, diff);
        readPartida(size[0], size[1], matrix);
    }

    public void readRepoGame(String user) throws FileNotFoundException, ExceptionKakuro {
        Integer[] size = repo.openFileSizeGame(user);
        String[][] matrix = repo.openFileMatrixGame(size, user);
        readPartida(size[0], size[1], matrix);
        this.partida.setDuration(this.repo.openFileTimeGame(user));
        this.partida.setHelp(this.repo.openFileHelp(user));
    }

    /**
     * Obtenir la mida del Kakuro
     * @return Retorna la mida en un Integer "nm", on n son les files i m son les columnes
     */
    public Integer[] sizeKakuro() {
        return this.partida.sizeKakuro();
    }

    /**
     * Obtenir el kakuro en forma de matriu String[][]
     * @return Retorna el kakuro en forma de matriu String[][]
     */
    public String[][] writeKakuro() {
        return this.partida.writeKakuro(2);
    }

    /**
     * Assginar valor a una casella blanca del Kakuro
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @param value El valor per modificar
     * @return Retorna true si s'ha pogut modificat, altrament retorna false
     */
    public boolean setValue(int r, int c, int value) throws ExceptionKakuro {
        return this.partida.setValue(r, c, value);
    }

    /**
     * Donar una pista de la casella amb fila r i columna c del Kakuro
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @return Retorna el numero correcte en la posicio r i c; si la posicio es incorrecta, retorna -1
     */
    public int sendHelp(int r, int c) throws ExceptionKakuro {
        return this.partida.send_help(r, c);
    }

    /**
     * Validar si un Kakuro te soluco unica
     * @return Retorna ture si el Kakuro te una unica solucio, altrament retorna false
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    public boolean validarPartida() throws ExceptionKakuro {
        return this.partida.validate();
    }

    /**
     * Obtenir la solucio del Kakuro de la Partida
     * @return Retorna la solucio del kakuro en forma de matriu String[][]
     */
    public String[][] writeKakuroSol() {
        return this.partida.writeKakuro(0);
    }

    /**
     * Crear nou Kakuro
     * @param n Les files del Kakuro
     * @param m Les columnes de Kakuro
     * @param matrix El kakuro en forma de matriu String[][]
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    public void readKakuro(int n, int m, String[][] matrix) throws ExceptionKakuro {
        this.kakuro = new Kakuro(n, m, matrix);
    }

    /**
     * Guardar el Kakuro i la seva solucio en dos fitxers separats del repositori
     * @param name El nom dels fitxers
     * @return Retorna true si s'ha gurdat amb exit, altrament retorna false
     */
    public boolean saveKakuro(String name, boolean gameOn) throws Exception {
        if (gameOn) {
            this.repo.saveKakuro(name, this.partida.get_difficulty(), this.partida.sizeKakuro(), this.partida.writeKakuro(1));
            this.repo.saveSolution(name, this.partida.sizeKakuro(), this.partida.writeKakuro(0));
        }
        else {
            this.repo.saveKakuro(name, this.kakuro.get_difficulty(), this.kakuro.getSize(), this.kakuro.write(1));
            this.repo.saveSolution(name, this.kakuro.getSize(), this.kakuro.write(0));
        }
        return true;
    }

    /**
     * Guardar la Partida en un fitxer
     * @param name El nom del fitxer
     * @param ajuda Si s'ha demanat ajuda o no
     */
    public void savePartida(String name, boolean ajuda) throws Exception {
        this.repo.saveGame(name, sizeKakuro(), this.partida.writeKakuro(2), this.partida.getDuration(), ajuda);
    }

    public boolean hasPartida(String user) {return this.repo.hasPartida(user);}

    /**
     * Eliminar un fitxer que guarda un Kakuro
     * @param i El numero del fitxer a eliminar
     * @return Retorna true si s'ha eliminat el fitxer, altrament retorna false
     */
    public boolean deleteKakuro(int i) throws Exception {
        Integer[] size = repo.openFileSize(i, 0);
        String[][] matrix = repo.openFileMatrix(size, i, 0);
        this.kakuro = new Kakuro(size[0], size[1], matrix);
        return this.repo.deleteKakuro(i, this.kakuro.get_difficulty());
    }

    /**
     * Generar un nou Kakuro en una nova Partida
     * @param n Les files del Kakuro
     * @param m Les columnes de Kakuro
     * @param difficulty La dificultat del Kakuro
     */
    public void generatePartida(int n, int m, int difficulty) {
        this.partida = new Partida(n, m, difficulty);
    }

    /**
     * Obtenir la duracio d'una Partida
     * @return Retorna la duracio d'una Partida
     */
    public long getDuration() {return this.partida.getDuration();}

    /**
     * Saber si un usuari existeix o no
     * @param user El nom del fitxer
     * @return Retorna true si el fitxer existeix, altrament retorna false
     */
    public boolean existsUser(String user) {
        return this.usu.userExists(user);
    }

    /**
     * Guardar un usuari en un fitxer
     * @param user El nom del fitxer a crear
     * @param password La contrasenya de l'usuari
     */
    public void saveUser(String user, String password) throws Exception {
        this.usu.saveUser(user, password);
    }

    /**
     * Eliminar un usuari
     * @param user El nom de l'usuari
     * @param password La contrasenya de l'usuari
     */
    public void deleteUser(String user, String password) throws Exception {
        this.usu.deleteUser(user, password);
    }

    /**
     * Getter de la contrasenya de l'usuari
     * @param user El nom de l'usuari
     * @return Retorna la contrasenya de l'usuari
     */
    public String getPassword(String user) throws FileNotFoundException {
        return this.usu.getPassword(user);
    }

    /**
     * Actualitzar les estadistiques de l'usuari
     * @param user El nom de l'usuari
     * @param ajuda Si ha demanat ajuda o no
     * @param d El temps que ha tardat en resoldre el Kakuro
     * @param diff La dificultat del Kakuro resolt
     */
    public void updateStatistics(String user, boolean ajuda, Duration d, int diff) {
        this.usu.updateStatistics(user, ajuda, d, diff);
    }

    /**
     * Obtenir les estadistiques d'usuari
     * @param user El nom de l'usuari
     * @return Retorna un Array d'Strings amb les estadisdiques d'usuari
     */
    public String[] getUserStatistics(String user) {
        return this.usu.getUserStatistics(user);
    }

    /**
     * Getter de la dificultat
     * @return Retorna la dificultat del Kakuro de la Partida
     */
    public int get_difficulty() { return this.partida.get_difficulty(); }

    /**
     * Getter de ajuda
     * @return Retorna true si s'ha demanat ajuda, altrament false
     */
    public boolean getHelp() { return this.partida.getHelp(); }
}

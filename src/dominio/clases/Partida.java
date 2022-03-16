/**
 * @file ./src/dominio/clases/Partida.java
 * @author Zhongkai Dai
 */

package src.dominio.clases;


import src.dominio.clases.kakuro.Generator;
import src.dominio.clases.kakuro.Kakuro;
import src.dominio.clases.kakuro.Solver;

/**
 * @class Partida
 * @brief Classe per representar una Partida.
 */
public class Partida {

    //ATRIBUTS

    /**
     * El Kakuro de la Partida
     */
    Kakuro k;

    // Profile user;

    /**
     * Si s'ha acabat o no
     */
    private boolean finished;

    /**
     * Duracio de la Partida
     */
    private long duration;

    /**
     * Si s'ha demanat ajuda a la Partida
     */
    private boolean ajuda;

    /**
     * El temps actual
     */
    private long timeNow;

    /**
     * El nombre de caselles blanques
     */
    private int white_cells;

    /**
     * El nombre de caselles ocupades per un valor valid
     */
    private int current_cells;

    //CONSTRUCTORS

    /**
     * Constructora de Partida a partir de un kakuro donat
     * @param n El nombre de files del Kakuro
     * @param m El nombre de columnes del Kakuro
     * @param matrix El kakuro en forma de matriu String[][]
     */
    public Partida(int n, int m, String[][] matrix) throws ExceptionKakuro {
        k = new Kakuro(n, m, matrix);
        finished = false;
        duration = 0;
        timeNow = System.currentTimeMillis();
        white_cells = this.k.get_white_cells() - this.k.getCellsIniValor();
        current_cells = 0;
        ajuda = false;
    }

    /**
     * Constructora de Partida a partir del generador
     * @param n El nombre de files del Kakuro
     * @param m El nombre de columnes del Kakuro
     * @param difficulty dificultat del kakuro
     */
    public Partida(int n, int m, int difficulty) {
        Generator g = new Generator();
        k = g.generate_kakuro(n, m, difficulty);
        finished = false;
        duration = 0;
        timeNow = System.currentTimeMillis();
        white_cells = this.k.get_white_cells();
        current_cells = 0;
        ajuda = false;
    }

    //METODES

    /**
     * Obtenir la mida del Kakuro
     * @return Retorna la mida en un Integer "nm", on n son les files i m son les columnes
     */
    public Integer[] sizeKakuro() {
        return this.k.getSize();
    }

    /**
     * Getter per defecte de la duracio
     * @return Retorna la duracio
     */
    public long getDuration() {
        return this.duration + (System.currentTimeMillis() - this.timeNow);
    }

    /**
     * Escriure el Kakuro
     * @return Retorna el kakuro en forma de matriu String[][]
     */
    public String[][] writeKakuro(int type) {
        return this.k.write(type);
    }

    /**
     * Assginar valor a una casella blanca del Kakuro
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @param value El valor per modificar
     */
    public boolean setValue(int r, int c, int value) throws ExceptionKakuro {
        if(value == -1) {
            if(this.k.get_cell(r,c).get_value() == 0) return false;
            --current_cells;
            this.k.del_result(r,c);
        }
        else if (this.k.set_result(r, c, value)) {
            ++current_cells;
            if(current_cells == white_cells) {
                finished = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Donar una pista de la casella amb fila r i columna c del Kakuro
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @return Retorna el numero correcte en la posicio r i c; si la posicio es incorrecta, retorna -1
     */
    public int send_help(int r, int c) throws ExceptionKakuro {
        return this.k.send_help(r, c);
    }

    /**
     * Validar si un Kakuro te soluco unica
     * @return Retorna si el Kakuro te una unica solucio o no
     */
    public boolean validate() throws ExceptionKakuro {
        Solver s = new Solver();
        return s.validate_kakuro(k);
    }

    /**
     * Obtenir el nom del Kakuro
     * @return Retorna nom del Kakuro
     */
    public String getKakuroName() {
        return this.k.get_name();
    }

    /**
     * Setter del nom del Kakuro
     * @param name El nom del Kakuro per modificar
     */
    public void setName(String name){
        this.k.set_name(name);
    }

    /**
     * Getter per defecte de la dificultat del Kakuro
     * @return Retorna la dificultat del Kakuro
     */
    public int get_difficulty() { return this.k.get_difficulty(); }

    /**
     * Setter per defecte de la duracio
     * @param t La duracio
     */
    public void setDuration(long t) { duration = t; }

    /**
     * Setter per defecte de ajuda
     * @param b Si s'ha demanat ajuda o no
     */
    public void setHelp(boolean b) { ajuda = b; }

    /**
     * Getter per defecte de ajuda
     * @return Retorna true si s'ha demanat ajuda, altrament false
     */
    public boolean getHelp() { return ajuda; }
}

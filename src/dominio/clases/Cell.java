/**
 * @file ./src/dominio/clases/Cell.java
 * @author Marco Madalin
 */

package src.dominio.clases;

/**
 * @class Cell
 * @brief Classe abstracta per representar una casella.
 */
public abstract class Cell {

    //GETTERS I SETTERS

    /**
     * Getter per defecte del valor de Cell
     * @return Retorna el valor de Cell
     */
    public int get_value() {
        return -1;
    }

    /**
     * Getter per defecte de la suma horitzonal de Cell
     * @return Retorna la suma horitzonal de Cell
     */
    public int get_sum_row() { return -1; }

    /**
     * Getter per defecte de la suma vertical de Cell
     * @return Retorna la suma vertical de Cell
     */
    public int get_sum_column() {
        return -1;
    }

    /**
     * Setter per defecte del valor de Cell
     * @param x El valor de Cell per modificar
     */
    public void set_value(int x) {}

    /**
     * Setter per defecte de la suma horitzonal de Cell
     * @param r La suma horitzonal de Cell per modificar
     */
    public void set_sum_row(int r) {}

    /**
     * Setter per defecte de la suma vertical de Cell
     * @param c La suma vertical de Cell per modificar
     */
    public void set_sum_column(int c) {}

    //METODES

    /**
     * Saber si Cell esta sense assignar, es a dir, igual a 0
     * @return Retorna true si esta sense assignar, altrament retorna false
     */
    public boolean is_unassigned() {
        return false;
    }

    /**
     * Saber si Cell te suma horitzonal
     * @return Retorna true si te suma horitzonal, altrament retorna false
     */
    public boolean has_sum_row() { return false; }

    /**
     * Saber si Cell te suma vertical
     * @return Retorna true si te suma vertical, altrament retorna false
     */
    public boolean has_sum_column() { return false; }
}

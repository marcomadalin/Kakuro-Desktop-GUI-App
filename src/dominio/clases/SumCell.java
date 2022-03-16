package src.dominio.clases; /**
 * @file ./src/dominio/clases/SumCell.java
 * @author Marco Madalin
 */


/**
 * @class SumCell
 * @brief Classe per representar les sumes horitzontal i vertical i el numero de caselles blanques a la dreta i a sota
 * d'una casella negra.
 */
public class SumCell extends BlackCell {

    //ATRIBUTS

    /**
     * La suma horitzonal de SumCell
     */
    private int sum_row;


    /**
     * La suma vertical de SumCell
     */
    private int sum_column;

    //CONSTRUCTORS

    /**
     * Constructora per defecte de SumCell
     */
    public SumCell() {
        super();
        this.sum_row = this.sum_column = -1;
    }

    //GETTERS I SETTERS

    /**
     * Getter per defecte de la suma horitzonal de SumCell
     * @return Retorna la suma horitzonal de SumCell
     */
    public int get_sum_row() {
        return this.sum_row;
    }

    /**
     * Getter per defecte de la suma vertical de SumCell
     * @return Retorna la suma vertical de SumCell
     */
    public int get_sum_column() {
        return this.sum_column;
    }

    /**
     * Setter per defecte de la suma horitzonal de SumCell
     * @param r La suma horitzonal de SumCell per modificar
     */
    public void set_sum_row(int r) {
        this.sum_row = r;
    }

    /**
     * Setter per defecte de la suma vertical de SumCell
     * @param c La suma vertical de SumCell per modificar
     */
    public void set_sum_column(int c) {
        this.sum_column = c;
    }


    //METODES

    /**
     * Saber si SumCell te suma horitzonal
     * @return Retorna true si te suma horitzonal, altrament retorna false
     */
    public boolean has_sum_row() { return this.sum_row != -1; }

    /**
     * Saber si SumCell te suma vertical
     * @return Retorna true si te suma vertical, altrament retorna false
     */
    public boolean has_sum_column() {
        return this.sum_column != -1;
    }
}

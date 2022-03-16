/**
 * @file ./src/dominio/clases/WhiteCell.java
 * @author Marco Madalin
 */

package src.dominio.clases;

/**
 * @class WhiteCell
 * @brief Classe per representar una casella blanca.
 */
public class WhiteCell extends Cell {

    //ATRIBUTS

    /**
     * El valor de WhiteCell
     */
    private int value;

    //CONTRUCTORS

    /**
     * Constructora per defecte de WhiteCell
     */
    public WhiteCell() {
        this.value = 0;
    }

    //GETTERS I SETTERS

    /**
     * Getter per defecte del valor de WhiteCell
     * @return Retorna el valor de WhiteCell
     */
    public int get_value() {
        return this.value;
    }

    /**
     * Setter per defecte del valor de WhiteCell
     * @param x Valor de WhiteCell per modificar
     */
    public void set_value(int x) {
        this.value = x;
    }

    //METODES

    /**
     * Saber si la casella blanca esta sense assignar, es a dir, igual a 0
     * @return Retorna true si esta sense assignar, altrament retorna false
     */
    public boolean is_unassigned() {
        return (this.value == 0);
    }
}

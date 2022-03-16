/**
 * @file ./src/dominio/clases/ExceptionKakuro.java
 * @author Zhongkai Dai
 */

package src.dominio.clases;

/**
 * @class ExceptionKakuro
 * @brief Classe per les excepcions del kakuro.
 */
public class ExceptionKakuro extends Exception{

    /**
     * Missatge d'error
     */
    String errorMessage;

    /**
     * Constructora per defecte de ExceptionKakuro
     */
    public ExceptionKakuro() {
        super();
    }

    /**
     * Constructora de ExceptionKakuro
     * @param s El missatge d'error
     */
    public ExceptionKakuro(String s) {
        super(s);
        errorMessage = s;
    }
}

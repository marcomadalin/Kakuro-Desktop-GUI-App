/**
 * @file ./src/dominio/clases/SlotRanking.java
 * @author Zhongkai Dai
 */
package src.dominio.clases;

/**
 * @class SlotRanking
 * @brief Classe per representar cada posicio del ranking.
 */
public class SlotRanking {
    /**
     * El nom de l'usuari del record
     */
    private String user;

    /**
     * Les hores del record
     */
    private int hours;

    /**
     * Els minuts del record
     */
    private int minutes;

    /**
     * Els segons del record
     */
    private int seconds;

    /**
     * Constructora per defecte
     */
    public SlotRanking() {}

    /**
     * Constructora amb valors incials
     * @param user El nom de l'usuari del record
     * @param hours Les hores del record
     * @param minutes Els minuts del record
     * @param seconds Els segons del record
     */
    public SlotRanking(String user, int hours, int minutes, int seconds) {
        this.user = user;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Getter per defecte del nom de l'usuari
     * @return Retorna el nom de l'usuari
     */
    public String getUser() { return user; }

    /**
     * Getter per defecte de les hores
     * @return Retorna les hores
     */
    public int getHours() { return hours; }

    /**
     * Getter per defecte dels minuts
     * @return Retorna els minuts
     */
    public int getMinutes() { return minutes; }

    /**
     * Getter per defecte dels segons
     * @return Retorna els segons
     */
    public int getSeconds() { return seconds; }

    /**
     * Setter per defecte del nom de l'usuari
     * @param user El nom de l'usuari
     */
    public void setUser(String user) { this.user = user; }

    /**
     * Setter per defecte de les hores
     * @param hours Les hores
     */
    public void setHours(int hours) { this.hours = hours; }

    /**
     * Setter per defecte dels minuts
     * @param minutes Els minuts
     */
    public void setMinutes(int minutes) { this.minutes = minutes; }

    /**
     * Setter per defecte dels segons
     * @param seconds Els segons
     */
    public void setSeconds(int seconds) { this.seconds = seconds; }
}

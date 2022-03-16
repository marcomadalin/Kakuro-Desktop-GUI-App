/**
 * @file ./src/dominio/controladores/CtrlRankingDomini.java
 * @author
 */
package src.dominio.controladores;

import src.dominio.clases.SortbyTime;
import src.persistencia.controladores.CtrlRanking;
import src.dominio.clases.SlotRanking;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @class CtrlRankingDomini
 * @brief Classe del Controlador de Ranking de Domini. S'encarrega de la comunicacio amb CtrlPresentacion i amb
 * CtrlRanking, del manteniment basic de les dades i de la implementacio d'una part de les funcionalitats principals
 * (casos d'us) que corresponen a la capa de domini.
 */
public class CtrlRankingDomini {
    /**
     * Controlador de Ranking
     */
    private CtrlRanking rank;

    /**
     * Llista de SlotRanking on cada SlotRanking conte el nom d'usuari, les hores, els minuts i els segons del record
     */
    private List<SlotRanking> ranking;

    //CONSTRUCTORS

    /**
     * Constructora per defecte
     */
    public CtrlRankingDomini() {
        this.inicializarCtrlRankingDomini();
    }

    //FUNCIONS

    /**
     * Inicialitzar Controlador de Domini
     */
    public void inicializarCtrlRankingDomini() {
        this.rank = CtrlRanking.getInstance();
        ranking = new ArrayList<SlotRanking>();
    }

    /**
     * Actualitzar el ranking
     * @param user El nom de l'usuari del record
     * @param d El temps del record
     * @param diff La dificultat del Kakuro resolt
     */
    public void updateRanking(String user, Duration d, int diff) throws Exception {
        ranking = new ArrayList<SlotRanking>();
        ArrayList<ArrayList<String>> currentRanking = this.rank.getRankingPerDiff(diff);
        setListRanking(currentRanking);

        String username = user;
        int seconds = (int) d.getSeconds();
        int hours = seconds/3600;
        int minutes = (seconds%3600)/60;
        seconds = (seconds%3600)%60;
        SlotRanking slot = new SlotRanking(username, hours, minutes, seconds);
        this.ranking.add(slot);

        Collections.sort(this.ranking, new SortbyTime());

        if (this.ranking.size() == 11) this.ranking.remove(10);
        ArrayList<ArrayList<String>> newRanking = setMatrixRanking(this.ranking);

        this.rank.updateRanking(newRanking, diff);
    }

    /**
     * Getter del ranking per dificultat
     * @param diff La dificultat del ranking que volem obtenir
     * @return La matriu d'Strings que conte tota la informacio del ranking que volem obtenir
     */
    public ArrayList<ArrayList<String>> getRankingPerDiff(int diff) throws Exception {
        return this.rank.getRankingPerDiff(diff);
    }

    /**
     * Setter de ranking
     * @param infoRanking La matriu d'Strings que conte la informacio del ranking
     */
    private void setListRanking(ArrayList<ArrayList<String>> infoRanking) {
        for (int i = 0; i < infoRanking.size(); ++i) {
            String username = null;
            int hours = 0, minutes = 0, seconds = 0;

            username = infoRanking.get(i).get(0);
            hours = Integer.parseInt(infoRanking.get(i).get(1));
            minutes = Integer.parseInt(infoRanking.get(i).get(2));
            seconds = Integer.parseInt(infoRanking.get(i).get(3));

            SlotRanking slot = new SlotRanking(username, hours, minutes, seconds);
            this.ranking.add(slot);
        }
    }

    /**
     * Posar la informacio del ranking a una matriu d'Strings
     * @param infoRanking La llista de SlotRanking que conte la informacio del ranking
     * @return Retorna la matriu d'Strings que conte la informacio del ranking
     */
    private ArrayList<ArrayList<String>> setMatrixRanking(List<SlotRanking> infoRanking) {
        ArrayList<ArrayList<String>> newRanking = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < infoRanking.size(); ++i) {
            ArrayList<String> slot = new ArrayList<>();
            slot.add(infoRanking.get(i).getUser());
            slot.add(String.valueOf(infoRanking.get(i).getHours()));
            slot.add(String.valueOf(infoRanking.get(i).getMinutes()));
            slot.add(String.valueOf(infoRanking.get(i).getSeconds()));
            newRanking.add(slot);
        }
        return newRanking;
    }
}

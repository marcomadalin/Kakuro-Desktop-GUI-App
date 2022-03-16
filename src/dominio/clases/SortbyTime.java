/**
 * @file ./src/dominio/clases/SortbyTime.java
 * @author Zhongkai Dai
 */
package src.dominio.clases;

import java.util.Comparator;

/**
 * @class SortbyTime
 * @brief Classe que implementa el comparador de SlotRanking.
 */
public class SortbyTime implements Comparator<SlotRanking> {

    /**
     * Compara un SlotRanking a un altre
     * @param o1 SlotRanking 1
     * @param o2 SlotRanking 2
     * @return Si les hores, els minuts i els segons son iguals, es retorna en ordre alfabetic; si les hores i els
     * minuts son iguals, es retorna qui te menor segons; si les hores son iguals, es retorna qui te menor minuts; sinó
     * es retorna qui te menor hores
     */
    @Override
    public int compare(SlotRanking o1, SlotRanking o2) {
        if (o1.getHours() == o2.getHours() && o1.getMinutes() == o2.getMinutes() && o1.getSeconds() == o2.getSeconds())
            return o1.getUser().compareTo(o2.getUser());
        else if (o1.getHours() == o2.getHours() && o1.getMinutes() == o2.getMinutes())
            return o1.getSeconds() - o2.getSeconds();
        else if (o1.getHours() == o2.getHours()) return o1.getMinutes() - o2.getMinutes();
        return o1.getHours() - o2.getHours();
    }
}

/**
 * @file ./src/persistencia/controladores/CtrlRanking.java
 * @author
 */

package src.persistencia.controladores;

import java.io.*;
import java.util.ArrayList;

/**
 * @class CtrlRanking
 * @brief Classe del Controlador de Ranking. S'encarrega de la comunicació amb CtrlRankingDomini i de la gestio de
 * ranking.
 */
public class CtrlRanking {
    //ATRIBUTS

    /**
     * Path dels rankings
     */
    private String path = "./data/rankings/";

    /**
     * La classe CtrlRanking és Singleton
     */
    private static CtrlRanking singletonObject;

    //CONSTRUCTORS

    /**
     * Constructora per defecte de CtrlRanking
     */
    private CtrlRanking() { initialize(); }

    //GETTERS I SETTERS

    /**
     * Getter per defecte del CtrlRanking, si no existeix el crea
     * @return Retorna el CtrlRanking
     */
    public static CtrlRanking getInstance() {
        if (singletonObject == null) {
            singletonObject = new CtrlRanking() {
            };
        }

        return singletonObject;
    }

    //METODES

    /**
     * Inicialitzar la carpeta de rankings, crear el ranking facil, mitja i dificil
     */
    private void initialize() {
        File file = new File(path+"facil.txt");
        File file2 = new File(path+"mitja.txt");
        File file3 = new File(path+"dificil.txt");
        try {
            file.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
        } catch (Exception ignored) {
        }
    }

    /**
     * Actualitzar el ranking
     * @param infoRanking La matriu d'Strings que conte tota la informacio del ranking
     * @param diff La dificultat del ranking
     */
    public void updateRanking(ArrayList<ArrayList<String>> infoRanking, int diff) throws Exception {
        String difficulty;
        if (diff == 1) difficulty = "facil";
        else if (diff == 2) difficulty = "mitja";
        else difficulty = "dificil";

        String filename = path + difficulty + ".txt";
        String filetemp = path + difficulty + ".temp";

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(filetemp));

            for(int i = 0; i < infoRanking.size(); ++i) {
                for (int j = 0; j < infoRanking.get(i).size(); ++j) {
                    pw.println(infoRanking.get(i).get(j));
                }
            }
            pw.close();

            File realName = new File(filename);
            realName.delete();
            new File(filetemp).renameTo(realName);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Getter del ranking per dificultat
     * @param diff La dificultat del ranking que volem obtenir
     * @return Retorna la matriu d'Strings que conte tota la informacio del ranking que volem obtenir
     */
    public ArrayList<ArrayList<String>> getRankingPerDiff(int diff) throws Exception {
        String difficulty;
        if (diff == 1) difficulty = "facil";
        else if (diff == 2) difficulty = "mitja";
        else difficulty = "dificil";

        ArrayList<ArrayList<String>> ranking = new ArrayList<ArrayList<String>>();
        String filename = path + difficulty + ".txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            int i = 0;
            ArrayList<String> slot = new ArrayList<>();
            while (line != null) {
                if (i % 4 == 0 && i != 0) {
                    ranking.add(slot);
                    slot = new ArrayList<>();
                }
                slot.add(line);

                ++i;
                line = reader.readLine();
            }
            ranking.add(slot);
            reader.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return ranking;
    }
}

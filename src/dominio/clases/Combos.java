/**
 * @file ./src/dominio/clases/Combos.java
 * @author Marco Madalin
 */

package src.dominio.clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @class Combos
 * @brief Classe per representar totes les combinacions possibles de numeros en una quantitat determinada de caselles
 * blanques.
 */
public class Combos {

    /**
     * La classe Combos és Singleton
     */
    private static Combos singletonObject;

    /**
     * Getter per defecte del CtrlRepoGlobal, si no existeix el crea
     * @return Retorna el CtrlRepoGlobal
     */
    public static Combos getInstance() throws FileNotFoundException {
        if (singletonObject == null) {
            singletonObject = new Combos() {
            };
        }
        return singletonObject;
    }

    /**
     * El primer ArrayList guarda el numero de caselles blanques, el segon ArrayList guarda la suma d'aquestes caselles
     * blanques i el tercer ArrayList guarda un HashSet de les combinacions possibles de numeros en aquestes caselles
     * blanques.
     */
    ArrayList<ArrayList<ArrayList<HashSet<Integer>>>>  values;

    /**
     * L'String guarda "caselles blanques+suma" i el HashSet guarda els valors no usats en el HashSet de values.
     */
    HashMap<String, HashSet<Integer>> unused_values;

    /**
     * Constructora de Combos
     * @throws FileNotFoundException if file does not exist.
     */
    public Combos() throws FileNotFoundException {
        read_combos();
        read_unused_values();
    }

    public HashSet<Integer> get_compatible_values(HashSet<Integer> used_values, int cell, int sum) {
        HashSet<Integer> possible_values = new HashSet<>();
        HashSet<Integer> combo;

        for (int i = 0; i < values.get(cell-2).get(sum-get_min_value(cell)).size(); ++i) {
            combo = new HashSet<>(values.get(cell-2).get(sum-get_min_value(cell)).get(i));
            if (combo.containsAll(used_values)) {
                combo.removeAll(used_values);
                if (!combo.isEmpty())possible_values.addAll(combo);
            }
        }
        return possible_values;
    }
    /**
     * Obtenir els possibles valors que poden anar en un conjunt de caselles blanques
     * @param cell El numero de caselles blanques
     * @param sum La suma d'aquestes caselles blanques
     */
    public HashSet<Integer> get_possible_values(int cell, int sum) {
        HashSet<Integer> possible_values = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        HashSet<Integer> unused = unused_values.get(Integer.toString(cell) + sum);
        if (unused != null) possible_values.removeAll(unused);
        return possible_values;
    }

    /**
     * Saber si una suma en una quantitat de caselles blanques es possible
     * @param sum La suma d'aquestes caselles blanques
     * @param cell El numero de caselles blanques
     * @return Retorna true si es possible, altrament retorna false
     */
    public boolean exists_sum_of_cells(int sum, int cell) {
        int index = sum - get_min_value(cell);
        if (cell == 1) return true;
        return (index >=0 && index < values.get(cell-2).size());
    }

    /**
     * Obtenir el numero maxim de sumes possibles en una quantitat determinada de caselles blanques
     * @param cell El numero de caselles blanques
     * @return Retorna el numero maxim de sumes possibles en una quantitat determinada de caselles blanques
     */
    private int compute_max_combinations(int cell) {
        return get_max_value(cell) - get_min_value(cell) + 1;
    }

    /**
     * Obtenir la suma minima en una quantitat determinada de caselles blanques
     * @param cell El numero de caselles blanques
     * @return Retorna la suma minima en una quantitat determinada de caselles blanques
     */
    private int get_min_value(int cell) {
        return (cell*(cell+1))/2;
    }

    /**
     * Obtenir la suma maxima en una quantitat determinada de caselles blanques
     * @param cell El numero de caselles blanques
     * @return Retorna la suma maxima en una quantitat determinada de caselles blanques
     */
    private int get_max_value(int cell) {
        return 45 - ((9-cell)*(10-cell)/2);
    }

    /**
     * Llegir tots els combos possibles del fitxer combos.txt dins del directori data
     * @throws FileNotFoundException if file does not exist.
     */
    private void read_combos() throws FileNotFoundException {
        Scanner scline = new Scanner(new File("./data/combos/combos.txt"));
        scline.useDelimiter("\n");
        this.values = new ArrayList<>(8);
        for (int i = 2; i <= 9; ++i) {
            int combinations = compute_max_combinations(i);
            ArrayList<ArrayList<HashSet<Integer>>> sum_combos = new ArrayList<>(combinations);
            Scanner sc;
            String line;

            for (int j = 0; j < combinations; ++j) {
                ArrayList<HashSet<Integer>> combo_set = new ArrayList<>();
                line = scline.nextLine();
                sc = new Scanner(line);
                while (sc.hasNext()) combo_set.add(get_set_from_string(sc.next()));
                sum_combos.add(combo_set);
            }
            values.add(sum_combos);
        }
    }

    /**
     * Llegir tots el valors no utilitzats del fitxer unused.txt dins del directori data
     * @throws FileNotFoundException if file does not exist.
     */
    private void read_unused_values() throws FileNotFoundException {
        Scanner scline = new Scanner(new File("./data/combos/unused.txt"));
        Scanner sc;
        String line, key;
        scline.useDelimiter("\n");
        unused_values = new HashMap<>(64);

        for(int i = 0; i < 64; ++i) {
            line = scline.next();
            sc = new Scanner(line);
            key = sc.next();
            unused_values.put(key, get_set_from_string(sc.next()));
        }
    }

    /**
     * Convertir els numeros de un String en un HashSet de Integers
     * @param s L'String de numeros
     * @return Retorna un HashSet de Integers amb els numeros de l'String
     */
    private HashSet<Integer> get_set_from_string(String s) {
        HashSet<Integer> set = new HashSet<>();
        for (int k = 0; k < s.length(); ++k) set.add(Integer.valueOf(s.charAt(k) - '0'));
        return set;
    }
}

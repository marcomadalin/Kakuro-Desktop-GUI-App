/**
 * @file ./src/dominio/clases/Kakuro.java
 * @author Marco Madalin
 */
package src.dominio.clases.kakuro;

import src.dominio.clases.*;
import java.util.*;

/**
 * @class Kakuro
 * @brief Classe per representar un Kakuro
 */
public class Kakuro {

    //ATRIBUTS

    /**
     * El nom del Kakuro
     */
    private String name;

    int difficulty;

    /**
     * El nombre de files del Kakuro
     */
    int n;

    /**
     * El nombre de columnes del Kakuro
     */
    int m;

    /**
     * El nombre de caselles blanques del Kakuro
     */
    int white_cells;

    private int cellsIniValor;



    /**
     * La matriu per guardar totes les caselles del Kakuro
     */
    Cell[][] grid;

    /**
     * El map<K,V> per guardar la solució del Kakuro
     * K indica la posicio en "ij"
     * V indica el valor de la casella blanca
     */
    Map<String, Integer> solution;

    //CONTRUCTORS

    /**
     * Constructora de Kakuro
     * @param n El nombre de files
     * @param m El nombre de columnes
     * @param matrix La matriu que representa el Kakuro
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    public Kakuro(int n, int m, String[][] matrix) throws ExceptionKakuro {
        this.name = null;
        this.n = n;
        this.m = m;
        this.difficulty = 0;
        this.white_cells = 0;
        this.grid = null;
        this.solution = null;
        read(matrix);
    }

    /**
     * Constructora de Kakuro
     * @param n El nombre de files
     * @param m El nombre de columnes
     */
    public Kakuro(int n, int m) {
        this.name = null;
        this.n = n;
        this.m = m;
        this.difficulty = 0;
        this.white_cells = 0;
        this.grid = null;
        this.solution = null;
    }

    //GETTERS I SETTERS

    /**
     * Aconseguir la mida del Kakuro
     * @return Retorna la mida en un Integer "nm", on n son les files i m son les columnes
     */
    public Integer[] getSize() {
        Integer[] size = new Integer[2];
        size[0] = this.n;
        size[1] = this.m;
        return size;
    }

    /**
     * Getter del nom del Kakuro
     */
    public String get_name() {
        return name;
    }

    /**
     * Getter d'una casella del Kakuro
     */
    public Cell get_cell(int r, int c) {
        return grid[r][c];
    }

    /**
     * Getter dels nombres de caselles blanques del Kakuro
     */
    public int get_white_cells() {
        return white_cells;
    }

    public int get_rows() {
        return n;
    }

    public int get_columns() {
        return m;
    }

    public int get_difficulty() { return difficulty; }
    /**
     * Setter per defecte de la nom del Kakuro
     * @param nm El nom del Kakuro per modificar
     */
    public void set_name(String nm) {
        this.name = nm;
    }
    public int getCellsIniValor() { return cellsIniValor;}


    //METODES PUBLICS

    public void print() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] instanceof WhiteCell) System.out.print(grid[i][j].get_value() + ",");
                else if (grid[i][j] instanceof SumCell){
                    if (grid[i][j].has_sum_column()) System.out.print("C" + grid[i][j].get_sum_column());
                    if (grid[i][j].has_sum_row()) System.out.print("F" + grid[i][j].get_sum_row());
                    System.out.print(",");
                }
                else System.out.print("*,");
            }
            System.out.println();
        }
    }
    /**
     * Llegeix el kakuro
     * @param matrix El kakuro en forma de matriu String[][]
     * @throws ExceptionKakuro if the Kakuro is incorrect.
     */
    public void read(String[][] matrix) throws ExceptionKakuro {
        int strip_difficulty = 1;
        int count = 0;
        grid = new Cell[this.n][this.m];
        solution = new HashMap<>();

        ArrayList<Integer> listValues = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (matrix[i][j].charAt(0) == '*') {
                    grid[i][j] = new BlackCell();
                    count = 0;
                }
                else if (matrix[i][j].charAt(0) != 'C' &&
                        matrix[i][j].charAt(0) != 'F') {
                    ++count;
                    if (count > 4) {
                        strip_difficulty = 2;
                        if (count > 7) {
                            strip_difficulty = 3;
                            if (count > 9) throw new ExceptionKakuro("Kakuro with strip lenght > 9");
                        }
                    }
                    grid[i][j] = new WhiteCell();
                    ++this.white_cells;
                    solution.put(Integer.toString(i) + j, null);
                    if(matrix[i][j].charAt(0) != '?') {
                        listValues.add(i);
                        listValues.add(j);
                        cellsIniValor ++;
                    }
                }
                else {
                    grid[i][j] = new SumCell();
                    boolean has_column, has_row;
                    String chars = matrix[i][j].replaceAll("[\\d-]", "");

                    if (chars.length() == 2) has_column = has_row = true;
                    else if (chars.charAt(0) == 'F') {
                        has_row = true;
                        has_column = false;
                    } else {
                        has_row = false;
                        has_column = true;
                    }

                    Scanner nums = new Scanner(matrix[i][j]);
                    nums.useDelimiter("[^\\d-]");

                    if (has_column) grid[i][j].set_sum_column(Integer.valueOf(nums.next()));
                    if (has_row) grid[i][j].set_sum_row(Integer.valueOf(nums.next()));
                    count = 0;
                }
            }
            count = 0;
        }

        Solver s = new Solver();
        s.validate_kakuro(this);
        set_difficulty(strip_difficulty);

        for (int i = 0; i < listValues.size(); i += 2) {
            grid[listValues.get(i)][listValues.get(i+1)].set_value(Integer.parseInt(matrix[listValues.get(i)][listValues.get(i+1)]));
        }
    }


    /**
     * Escriure el kakuro
     * @param type 0 Si s'ha obtingut la solucio, 1 kakuro buit o  2 kakuro en l'estat actual.
     * @return Retorna el kakuro en forma de matriu String[][]
     */
    public String[][] write(int type) {
        String[][] matrix = new String[this.n][this.m];
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.m; ++j) {
                if (grid[i][j] instanceof SumCell) {
                    if (grid[i][j].has_sum_row() && grid[i][j].has_sum_column()) {
                        if (String.valueOf(grid[i][j].get_sum_column()).length() == 1
                                && String.valueOf(grid[i][j].get_sum_row()).length() == 1) {
                            matrix[i][j] = "C" + grid[i][j].get_sum_column() + "F" + grid[i][j].get_sum_row();
                        } else if ((String.valueOf(grid[i][j].get_sum_column()).length() == 1
                                && String.valueOf(grid[i][j].get_sum_row()).length() == 2)
                                || (String.valueOf(grid[i][j].get_sum_column()).length() == 2
                                && String.valueOf(grid[i][j].get_sum_row()).length() == 1)) {
                            matrix[i][j] = "C" + grid[i][j].get_sum_column() + "F" + grid[i][j].get_sum_row();
                        } else {
                            matrix[i][j] = "C" + grid[i][j].get_sum_column() + "F" + grid[i][j].get_sum_row();
                        }
                    } else if (grid[i][j].has_sum_column()) {
                        if (String.valueOf(grid[i][j].get_sum_column()).length() == 1)
                            matrix[i][j] = "C" + grid[i][j].get_sum_column();
                        else if (String.valueOf(grid[i][j].get_sum_column()).length() == 2)
                            matrix[i][j] = "C" + grid[i][j].get_sum_column();
                    } else if (grid[i][j].has_sum_row()) {
                        if (String.valueOf(grid[i][j].get_sum_row()).length() == 1)
                            matrix[i][j] = "F" + grid[i][j].get_sum_row();
                        else if (String.valueOf(grid[i][j].get_sum_row()).length() == 2)
                            matrix[i][j] = "F" + grid[i][j].get_sum_row();
                    }
                } else if (grid[i][j] instanceof BlackCell) matrix[i][j] = "*";
                else {
                    if (type == 0) matrix[i][j] = Integer.toString(this.solution.get(Integer.toString(i) + j));
                    else if (type == 1 || grid[i][j].is_unassigned()) matrix[i][j] ="?";
                    else matrix[i][j] = Integer.toString(grid[i][j].get_value());
                }

            }
        }
        return matrix;
    }



    /**
     * Donar una pista de la casella amb la fila r i columna c del Kakuro
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @return Retorna el numero correcte en la posicio r i c; si la posicio es incorrecta, retorna -1
     */
    public int send_help(int r, int c) throws ExceptionKakuro {
        if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] instanceof WhiteCell) {
            String rc = String.valueOf(r) + c;
            return solution.get(rc);
        } else {
            throw new ExceptionKakuro("Incorrect row and column values");
        }

    }


    /**
     * Assginar valor a una casella blanca
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @param value El valor per modificar
     * @return Retorna true si s'ha pogut modificat, altrament retorna false
     */
    public boolean set_result(int r, int c, int value) throws ExceptionKakuro {
        if (correct_value(r, c, value)) {
            if (grid[r][c].is_unassigned()) {
                grid[r][c].set_value(value);
                return true;
            }
            grid[r][c].set_value(value);
        }
        return false;
    }

    public void del_result(int r, int c) {
        grid[r][c].set_value(0);
    }



    //METODES PRIVATS

    private void set_difficulty(int strip_difficulty) throws ExceptionKakuro {
        int size_difficulty;

        if (n < 6 && m < 6) size_difficulty = 1;
        else if (n < 9 && m < 9) size_difficulty = 2;
        else {
            double proportion = (white_cells * 100) / ((n - 1) * (m - 1));
            if (proportion <= 35) size_difficulty = 1;
            else if (proportion <= 60) size_difficulty = 2;
            else size_difficulty = 3;
        }

        if (size_difficulty != 2 && strip_difficulty != 3) {
            int count = 0;

            outerloop:
            for (int j = 1; j < m; ++j) {
                for (int i = 1; i < n; ++i) {
                    if (grid[i][j] instanceof WhiteCell) {
                        ++count;
                        if (count > 4) {
                            strip_difficulty = 2;
                            if (n < 9 && m < 9) break outerloop;
                            if (count > 7) {
                                strip_difficulty = 3;
                                if (count > 9) throw new ExceptionKakuro("Kakuro with strip lenght > 9");
                                break outerloop;
                            }
                        }
                    }
                    else count = 0;
                }
                count = 0;
            }
        }

        if (size_difficulty == 1) {
            if (strip_difficulty == 3) difficulty = 2;
            else difficulty = 1;
        }
        else if (size_difficulty == 2) difficulty = 2;
        else {
            if (strip_difficulty == 1) difficulty = 2;
            else difficulty = 3;
        }
    }


    /**
     * Comprovar que el valor en una posicio es correcta
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @param value El valor per modificar
     * @return Retorna si es correcta o no
     */
    private boolean correct_value(int r, int c, int value) throws ExceptionKakuro {
        if (r >= n || c >= m || r < 0 || c < 0 || !(grid[r][c] instanceof WhiteCell)) {
            throw new ExceptionKakuro("Incorrect row and column values");
        }

        if (value > 9 || value < 0) {
            throw new ExceptionKakuro("The value must be between 0 and 9");
        }

        boolean[] full_up = {true}, full_down = {true};
        int sum_up, sum_down, sum_act;
        int[] sum_full = {0};

        sum_up = get_sum(r, c, value, true, -1, full_up, sum_full);
        if (sum_up == -1) {
            throw new ExceptionKakuro("Row repeat value");
        }

        sum_down = get_sum(r, c, value, true, 1, full_down, null);
        if (sum_down == -1) {
            throw new ExceptionKakuro("Row repeat value");
        }
        sum_act = sum_up + sum_down + value;
        if (sum_act > sum_full[0]) {
            throw new ExceptionKakuro("Row sum exceeded");
        }
        if (sum_act == sum_full[0]) {
            if (!(full_up[0] && full_down[0])) {
                throw new ExceptionKakuro("Row incomplete with equal sum");
            }
        }
        if((full_up[0] && full_down[0]) && sum_act < sum_full[0]) {
            throw new ExceptionKakuro("Row sum fell short");
        }

        full_up[0] = true;
        full_down[0] = true;
        sum_full[0] = 0;

        sum_up = get_sum(r, c, value,false, -1, full_up, sum_full);
        if (sum_up == -1) {
            throw new ExceptionKakuro("Column repeat value");
        }

        sum_down = get_sum(r, c, value,false, 1, full_down, null);
        if (sum_down == -1) {
            throw new ExceptionKakuro("Column repeat value");
        }
        sum_act = sum_up + sum_down + value;
        if (sum_act > sum_full[0]) {
            throw new ExceptionKakuro("Column sum exceeded");
        }
        if (sum_act == sum_full[0]) {
            if (!(full_up[0] && full_down[0])) {
                throw new ExceptionKakuro("Column incomplete with equal sum");
            }
        }
        if((full_up[0] && full_down[0]) && sum_act < sum_full[0]) {
            throw new ExceptionKakuro("Column sum fell short");
        }
        return true;
    }


    /**
     * Obtenir el resultat que hauria de tenir la suma de una fila o columna
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @param row Busqueda per fila
     * @return Retorna el resultat que hauria de tenir la suma de la fila o columna
     */
    private int get_sum(int r, int c, int value, boolean row, int sign, boolean[] is_full, int[] sum_full) {
        int i, j;
        int sum = 0;

        if (row) {
            i = 0;
            j = 1*sign;
        }
        else {
            i = 1*sign;
            j = 0;
        }

        r += i;
        c += j;
        while (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] instanceof WhiteCell) {
            if (grid[r][c].is_unassigned()) is_full[0] = false;
            else {
                if (grid[r][c].get_value() == value) return -1;
                else sum += grid[r][c].get_value();
            }
            r += i;
            c += j;
        }

        if (sign == -1) {
            if (row) sum_full[0] = grid[r][c].get_sum_row();
            else sum_full[0] = grid[r][c].get_sum_column();
        }
        return sum;
    }
}



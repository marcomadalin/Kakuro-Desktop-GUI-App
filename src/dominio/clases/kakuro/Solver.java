package src.dominio.clases.kakuro;

import src.dominio.clases.*;

import java.io.FileNotFoundException;
import java.util.*;

public class Solver {
    static Combos comb = null;
    private Kakuro k = null;
    private HashMap<Integer, HashSet<Integer>> white_cell_values = null;
    private HashMap<Integer,int[]> white_cell_info = null;
    HashMap<Integer,int[]> sum_cell_info = null;

    static {
        try {
            comb = Combos.getInstance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean validate_kakuro(Kakuro k) throws ExceptionKakuro {
        this.k = k;
        boolean[] solution = new boolean[] {false, false};

        if (sum_cell_info == null) {
            sum_cell_info = new HashMap<>();
            check_sumcells();
        }
        white_cell_info = new HashMap<>(k.white_cells);
        white_cell_values = new HashMap<>(k.white_cells);

        init_grid_info(true, k.n, k.m);
        init_grid_info(false, k.m, k.n);

        int cell = get_next_white_cell_pos(1, 0);
        ArrayList<Integer> cells_to_empty = new ArrayList();

        solve_clues(cell, cells_to_empty);
        solve_grid(cell, solution);
        free_memeory();

        if (!solution[0]) throw new ExceptionKakuro("Kakuro without solution");
        if (!solution[1]) throw new ExceptionKakuro("Kakuro with more than 1 solution");
        for (int i = 0; i < cells_to_empty.size(); ++i) k.grid[cells_to_empty.get(i)/k.m][cells_to_empty.get(i)%k.m].set_value(0);
        return true;
    }

    private void free_memeory() {
        k = null;
        white_cell_values = null;
        white_cell_info = null;
        sum_cell_info = null;
    }

    private void check_sumcells() throws ExceptionKakuro {
        for (int i = 0; i < k.n; ++i) {
            for (int j = 0; j < k.m; ++j) {
                if (k.grid[i][j] instanceof SumCell) {
                    sum_cell_info.put(i * k.m + j, new int[] {0, 0, 0, 0});
                    if (k.grid[i][j].has_sum_column()) check_sum_stride(i, j, 1, 0, k.grid[i][j].get_sum_column());
                    if (k.grid[i][j].has_sum_row()) j += check_sum_stride(i, j, 0, 1, k.grid[i][j].get_sum_row());
                }
            }
        }
    }

    private int check_sum_stride(int r, int c, int i, int j, int sum) throws ExceptionKakuro {
        int r_ini = r;
        int c_ini = c;
        int num_cells = 0;

        r += i;
        c += j;
        while (r < k.n && c < k.m && k.grid[r][c] instanceof WhiteCell) {
            ++num_cells;
            r += i;
            c += j;
        }

        if (!comb.exists_sum_of_cells(sum, num_cells )) {
            free_memeory();
            throw new ExceptionKakuro("Kakuro with impossible sum in strides");
        }

        sum_cell_info.get(r_ini * k.m + c_ini)[i] = num_cells;
        return num_cells;
    }

    private void init_grid_info(boolean row, int max_i, int max_j) {
        int r, c;

        for (int i = 0; i < max_i; ++i) {
            for (int j = 0; j < max_j; ++j) {
                if (row) {
                    r = i;
                    c = j;
                }
                else {
                    r = j;
                    c = i;
                }

                if (k.grid[r][c] instanceof SumCell) {
                    if (row)  j = init_stride_info(r, c, max_i, max_j, 0, 1);
                    else j = init_stride_info(r, c, max_j, max_i, 1 , 0);
                }
            }
        }
    }

    private int init_stride_info(int r, int c, int max_r, int max_c, int i, int j) {
        int r_ini = r;
        int c_ini = c;

        r += i;
        c += j;
        while (r < max_r && c < max_c && k.grid[r][c] instanceof WhiteCell) {
            int cell = r * k.m + c;
            if (i == 0) {
                white_cell_info.put(cell, new int[] {r_ini * k.m + c_ini, 0, get_next_white_cell_pos(r, c)});
                white_cell_values.put(cell, comb.get_possible_values(sum_cell_info.get(r_ini * k.m + c_ini)[0], k.grid[r_ini][c_ini].get_sum_row()));
            }
            else {
                int sum_cell = r_ini * k.m + c_ini;
                white_cell_info.get(cell)[1] = sum_cell;
                white_cell_values.get(cell).retainAll(comb.get_possible_values(sum_cell_info.get(sum_cell)[1], k.grid[r_ini][c_ini].get_sum_column()));
            }
            r += i;
            c += j;
        }
        if (i == 0) return --c;
        return --r;
    }

    private int get_next_white_cell_pos(int r, int c) {
        int pos = r * k.m + c;

        while (++pos < k.n * k.m) {
            r = pos / k.m;
            c = pos % k.m;
            if (k.grid[r][c] instanceof WhiteCell) break;
        }
        return pos;
    }

    private void solve_clues(int cell, ArrayList<Integer> cells_to_empty) throws ExceptionKakuro {
        int bound = k.m * k.n;

        while (cell < bound) {
            int i = cell / k.m;
            int j = cell % k.m;

            if (k.grid[i][j].is_unassigned()) {
                HashSet<Integer> values = white_cell_values.get(cell);
                if (values.isEmpty()) throw new ExceptionKakuro("Kakuro without solution");
                else if (values.size() == 1) {
                    int value = values.iterator().next();
                    k.grid[i][j].set_value(value);
                    cells_to_empty.add(cell);
                    sum_cell_info.get(white_cell_info.get(cell)[0])[2] += value;
                    sum_cell_info.get(white_cell_info.get(cell)[1])[3] += value;
                    k.solution.replace(Integer.toString(i) + j, value);
                    values.remove(value);
                    cell = update_strides(cell, value);
                }
                else cell = white_cell_info.get(cell)[2];
            }
            else cell = white_cell_info.get(cell)[2];
        }
    }

    private int update_strides(int cell, int value) throws ExceptionKakuro {
        int next_cell_row, next_cell_column;

        next_cell_row = update_stride_values(cell - 1, value, 0, -1);
        next_cell_column = update_stride_values(cell - k.m, value, -1, 0);
        update_stride_values(cell + 1, value, 0, 1);
        update_stride_values(cell + k.m, value, 1, 0);

        if (next_cell_column != -1) return next_cell_column;
        else if (next_cell_row != -1) return next_cell_row;
        else return white_cell_info.get(cell)[2];
    }

    private int update_stride_values(int cell, int value, int inci, int incj) throws ExceptionKakuro {
        int next_cell = -1;
        int i = cell / k.m;
        int j = cell % k.m;

        while (i >= 1 && j >= 1 && i < k.n && j < k.m && k.grid[i][j] instanceof WhiteCell) {
            cell = i * k.m + j;
            if (k.grid[i][j].is_unassigned()) {
                HashSet<Integer> values = white_cell_values.get(cell);
                values.remove(value);
                if (values.isEmpty()) throw new ExceptionKakuro("Kakuro without solution");
                else if (values.size() == 1) next_cell = cell;
            }
            i += inci;
            j += incj;
        }
        return next_cell;
    }

    private void print_info(int cell, int[] info_cell, int[] sum_cell_row, int[] sum_cell_column) {
        k.print();
        System.out.println("Cell = " + cell);
        System.out.println("VALUES = " + white_cell_values);
        System.out.println("Sum cell row = " + info_cell[0]);
        System.out.println("Sum cell col = " + info_cell[1]);
        System.out.println("Next = " + info_cell[2]);
        System.out.println("Acumumulated sum row = " + sum_cell_row[2]);
        System.out.println("Acumumulated sum col = " +  sum_cell_column[3]);
        System.out.println("////////////////////////////////////////////////////////////////////////");
    }

    /**
     * Solucionar el Kakuro
     * @return Retorna false si ha trobat una sola solucio, altrament retorna true.
     */
    private boolean solve_grid(int cell, boolean[] solution) {
        if (cell >= k.n * k.m) {
            if (!solution[0]) {
                solution[0] = solution[1] = true;
                return false;
            } else {
                solution[1] = false;
                return true;
            }
        } else {
            int i = cell / k.m;
            int j = cell % k.m;
            int[] info_cell = white_cell_info.get(cell);
            int[] info_sum_cell_row = sum_cell_info.get(info_cell[0]);
            int[] info_sum_cell_column = sum_cell_info.get(info_cell[1]);

            if (!k.grid[i][j].is_unassigned()) {
                if (valid_value(i, j, info_sum_cell_row[2], info_cell[0], true) &&
                        valid_value(i, j, info_sum_cell_column[3], info_cell[1], false)) {
                    if (solve_grid(info_cell[2], solution)) {
                        update_acumulated_sums(info_sum_cell_row, info_sum_cell_column, -k.grid[i][j].get_value());
                        return true;
                    }
                }
                else return false;
            }
            else {
                HashSet<Integer> values = white_cell_values.get(cell);
                Iterator<Integer> it = values.iterator();
                while (it.hasNext()) {
                    HashSet<Integer> cells_update_row = new HashSet<>();
                    HashSet<Integer> cells_update_col = new HashSet<>();
                    int value = it.next();
                    update_acumulated_sums(info_sum_cell_row, info_sum_cell_column, value);

                    if (valid_value(i, j, info_sum_cell_row[2], info_cell[0], true) &&
                            valid_value(i, j, info_sum_cell_column[3], info_cell[1], false)) {
                        if (check_pruning(cells_update_row, i, j, 0, 1, value, info_sum_cell_row[2], info_cell[0]) &&
                                check_pruning(cells_update_col, i, j, 1, 0, value, info_sum_cell_column[3], info_cell[1])) {
                            k.grid[i][j].set_value(value);

                            if (!solution[0]) k.solution.replace(Integer.toString(i) + j, value);
                            if (solve_grid(info_cell[2], solution)){
                                update_acumulated_sums(info_sum_cell_row, info_sum_cell_column, -value);
                                return true;
                            }
                            else k.grid[i][j].set_value(0);
                        }
                    }
                    update_acumulated_sums(info_sum_cell_row, info_sum_cell_column, -value);
                    undo_changes(cells_update_row, value);
                    undo_changes(cells_update_col, value);
                }
            }
            return false;
        }
    }

    private void update_acumulated_sums(int[] info_sum_cell_row, int[] info_sum_cell_column, int value) {
        info_sum_cell_row[2] += value;
        info_sum_cell_column[3] += value;
    }
    /**
     * Mètode encarregat de fer la poda i fer el forward checking, en un recorregut sobre les següents caselles blanques
     * en fila o columna eliminem el valor que hem assignat a la casella actual i comprovem que encara hi queden valors
     * per assignar
     * @param cells_update
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @param sum_act La suma actual
     * @return Retorna false si la suma maxima dels available_values de les caselles que queden mes la suma actual no
     * arriba a la suma que hauria de tenir la fila o columna o si la suma minima dels available_values de les caselles
     * que queden mes la suma actual es passa de la suma que hauria de tenir la fila o columna; altrament retorna true
     */
    private boolean check_pruning(HashSet<Integer> cells_update, int r, int c, int i, int j, int value, int sum_act, int sum_cell) {
        HashSet<Integer> pruning_values = new HashSet<>();
        int count = 0;

        r += i;
        c += j;
        while (r < k.n && c < k.m && k.grid[r][c] instanceof WhiteCell) {
            if (k.grid[r][c].is_unassigned()) {
                HashSet<Integer> values = white_cell_values.get(r * this.k.m + c);
                ++count;
                if (values.remove(value)) {
                    cells_update.add(r * this.k.m + c);
                    if (values.size() == 0) return false;
                }
                pruning_values.addAll(values);
            }
            r += i;
            c += j;
        }

        Integer[] aux = pruning_values.toArray(new Integer[pruning_values.size()]);
        Arrays.sort(aux);

        int sum_max;
        if (i == 0) sum_max = k.grid[sum_cell/k.m][sum_cell%k.m].get_sum_row();
        else sum_max = k.grid[sum_cell/k.m][sum_cell%k.m].get_sum_column();
        if (count == 1 && !pruning_values.contains(sum_max - sum_act)) return false;
        else {
            int max = 0;
            int min = 0;

            for (int k = 0; k < count && k < pruning_values.size(); ++k) {
                min += aux[k];
                max += aux[pruning_values.size() - k - 1];
            }
            if (max + sum_act < sum_max) return false;
            if (min + sum_act > sum_max) return false;
        }
        return true;
    }

    private void undo_changes(HashSet<Integer> cells_update, int value) {
        Iterator<Integer> it = cells_update.iterator();
        while (it.hasNext()) white_cell_values.get(it.next()).add(value);
    }

    /**
     * Comprova que la suma actual en fila o columna sigui menor a la total si no estem al final de la fila, altrament
     * comprova que sigui igual
     * @param r La fila del Kakuro
     * @param c La columna del Kakuro
     * @param is_row Si es fila o no
     * @return Si no estem a final de la fila, retorna true si la suma actual en fila o columna sigui menor a la total,
     * altrament retorna false. Si estem a final de la fila, retorna true si la suma actual en fila o columna es igual a
     * la total, altrament retorna false
     */
    private boolean valid_value(int r, int c, int sum_act, int sum_cell, boolean is_row) {
        boolean is_next_cell_white, exists_unassigned;
        int sum_max;

        if (is_row) {
            is_next_cell_white = ++c < k.m && k.grid[r][c] instanceof WhiteCell;
            sum_max = k.grid[sum_cell/k.m][sum_cell%k.m].get_sum_row();
            exists_unassigned = check_stride_values(r, c + 1, 0, 1);
        }
        else {
            is_next_cell_white = ++r < k.n && k.grid[r][c] instanceof WhiteCell;
            sum_max = k.grid[sum_cell/k.m][sum_cell%k.m].get_sum_column();
            exists_unassigned = check_stride_values(r + 1, c, 1, 0);
        }

        if (!is_next_cell_white) return (sum_act == sum_max);
        else {
            if (exists_unassigned) return sum_act < sum_max;
            else return sum_act <= sum_max;
        }
    }

    private boolean check_stride_values(int i, int j, int inci, int incj) {
        while (i < k.n && j < k.m && k.grid[i][j] instanceof WhiteCell) {
            if (k.grid[i][j].is_unassigned()) return true;
            i += inci;
            j += incj;
        }
        return false;
    }
}


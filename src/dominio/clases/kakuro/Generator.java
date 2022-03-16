package src.dominio.clases.kakuro;

import src.dominio.clases.*;

import java.util.*;

import static java.lang.Math.*;

public class Generator {

    private Kakuro k;
    private ArrayList<Integer> candidate_cells;
    private HashMap<Integer, boolean[]> cell_validity;
    private HashMap<Integer, Integer> cell_index;

    public Generator () {
        k = null;
        candidate_cells = null;
        cell_validity = null;
        cell_index = null;
    }

    public Kakuro generate_kakuro(int n, int m, int difficulty) {
        k = new Kakuro(n, m);
        k.difficulty = difficulty;
        Solver s = new Solver();
        int max_white_cells, max_kakuros = 169;

        if (difficulty == 1) max_white_cells = (int) ceil(((k.n-1) * (k.m-1) * (Math.random() * 15 + 15) / 100));
        else if (difficulty == 2) max_white_cells = (int) ceil(((k.n-1) * (k.m-1)* ((Math.random() * 20 + 36) / 100)));
        else max_white_cells = (int) ceil(((k.n-1) * (k.m-1) * ((Math.random() * 25 + 61) / 100)));

        outerloop:
        while (true) {

            while (true) {
                initialize_grid();
                generate_black_cells(max_white_cells);
                if((correct_grid(true) && correct_grid(false))) break;
            }

            int act = 0;
            boolean sum_cells_row_generated = false;
            HashMap<Integer, int[]> sum_cell_info = new HashMap<>();

            while (act <= max_kakuros) {
                while (act <= max_kakuros && !sum_cells_row_generated) {
                    sum_cells_row_generated = get_sum_cells_row(sum_cell_info);
                    ++act;
                }

                if (sum_cells_row_generated) {
                    get_sum_cells_column(sum_cell_info);

                    try {
                        s.sum_cell_info = sum_cell_info;
                        if (s.validate_kakuro(k)) break outerloop;
                    } catch (ExceptionKakuro e) {
                    }
                }
                sum_cells_row_generated = false;
            }
        }

        Kakuro aux = k;
        k = null;
        candidate_cells = null;
        cell_validity = null;
        cell_index = null;
        return aux;
    }

    private void initialize_grid() {
        k.grid = new Cell[k.n][k.m];
        k.white_cells = (k.get_rows() - 1) * (k.get_columns() - 1);

        for (int i = 0; i < k.n; ++i) {
            for (int j = 0; j < k.m; ++j) {
                if (i == 0 || j == 0) k.grid[i][j] = new BlackCell();
                else k.grid[i][j] = new WhiteCell();
            }
        }
    }

    private void get_candidate_cells() {
        int array_pos, cell;
        boolean[] constrains;
        array_pos = 0;

        for (int i = 1; i < k.n; ++i) {
            for (int j = 1; j < k.m; ++j) {
                constrains = new boolean[2];
                constrains[0] = constrains[1] = true;

                cell = i * k.m + j;
                if (i != 2 && i != k.n - 2 && j != 2 && j != k.m - 2) {
                    candidate_cells.add(cell);
                    cell_index.put(cell, array_pos);
                    ++array_pos;
                }
                else {
                    cell_index.put(cell, -1);
                    constrains[1] = false;
                }
                cell_validity.put(cell, constrains);
            }
        }
    }

    private void generate_black_cells(int max_white_cells) {
        candidate_cells = new ArrayList<> ((k.n - 1) * (k.m - 1));
        cell_validity = new HashMap<> ((k.n - 1) * (k.m - 1));
        cell_index = new HashMap<> ((k.n - 1) * (k.m - 1));

        get_candidate_cells();
        Random rand = new Random(System.currentTimeMillis());
        int painted_cells, cell, cell_sim = 0;

        while (k.white_cells > max_white_cells && !candidate_cells.isEmpty()) {
            cell = candidate_cells.get(rand.nextInt(candidate_cells.size()));
            painted_cells = paint_cells(cell, true);

            remove_candidate_cell(cell, 0);
            if (abs(painted_cells) == 2) {
                cell_sim = (k.n - (cell / k.m)) * k.m + (k.m - (cell % k.m));
                remove_candidate_cell(cell_sim, 0);
            }

            if (is_connected_grid()) {
                update_candidate_cells(cell);
                if (abs(painted_cells) == 2) update_candidate_cells(cell_sim);
            }
            else paint_cells(cell, false);
        }
    }

    private void update_candidate_cells(int cell) {
        int r = cell / k.m;
        int c = cell % k.m;
        if (c + 2 < k.m && valid_cell(cell + 2)) remove_candidate_cell(cell + 2, 1);
        if (c - 2 >= 1 && valid_cell(cell - 2)) remove_candidate_cell(cell - 2, 1);
        if (r + 2 < k.n && valid_cell(cell + 2 * k.m)) remove_candidate_cell(cell + 2 * k.m, 1);
        if (r - 2 >= 1 && valid_cell(cell - 2 * k.m)) remove_candidate_cell(cell - 2 * k.m, 1);
        if (c + 1 < k.m && invalid_pattern_cell(cell + 1) && check_pattern(cell + 1)) add_candidate_cell(cell + 1);
        if (c - 1 >= 1 && invalid_pattern_cell(cell - 1) && check_pattern(cell - 1)) add_candidate_cell(cell - 1);
        if (r + 1 < k.n && invalid_pattern_cell(cell + k.m) && check_pattern(cell + k.m)) add_candidate_cell(cell + k.m);
        if (r - 1 >= 1 && invalid_pattern_cell(cell - k.m) && check_pattern(cell - k.m)) add_candidate_cell(cell - k.m);
    }

    private boolean invalid_pattern_cell(int cell) {
        return cell_validity.get(cell)[0] && !cell_validity.get(cell)[1];
    }

    private boolean valid_cell(int cell) {
        return cell_validity.get(cell)[0] && cell_validity.get(cell)[1];
    }

    private void add_candidate_cell(int cell) {
        candidate_cells.add(cell);
        cell_validity.get(cell)[1] = true;
        cell_index.replace(cell, candidate_cells.size() - 1);
    }

    private void remove_candidate_cell(int cell, int constraint) {
        int index = cell_index.get(cell);
        if (index != -1) {
            Collections.swap(candidate_cells, index, candidate_cells.size() - 1);
            cell_index.replace(candidate_cells.get(index), index);
            cell_index.replace(cell, -1);
            cell_validity.get(cell)[constraint] = false;
            candidate_cells.remove(candidate_cells.size() - 1);
        }
    }

    private int paint_cells(int cell, boolean black) {
        int r, c, r_sim, c_sim, painted_cells;
        r = cell / k.m;
        c = cell % k.m;
        r_sim = k.n - r;
        c_sim = k.m - c;

        if (black) {
            painted_cells = -1;
            k.grid[r][c] = new BlackCell();
        }
        else {
            painted_cells = 1;
            k.grid[r][c] = new WhiteCell();
        }

        if (r != r_sim || c != c_sim) {
            if (black) {
                k.grid[r_sim][c_sim] = new BlackCell();
                --painted_cells;
            }
            else {
                k.grid[r_sim][c_sim] = new WhiteCell();
                ++painted_cells;
            }
        }

        k.white_cells += painted_cells;
        return  painted_cells;
    }

    private boolean is_connected_grid() {
        int pos = next_white_cell_pos(k, k.m + 1);
        if (pos >= k.n * k.m) return false;

        boolean[][] visited = new boolean[k.n][k.m];
        for (int i = 0; i < k.n; ++i) {
            for (int j = 0; j < k.m; ++j) visited[i][j] = !(k.grid[i][j] instanceof WhiteCell);
        }

        return count_white_cells(visited, pos) == k.white_cells;
    }

    private int count_white_cells(boolean[][] visited, int pos) {
        int count = 0;
        int r = pos / k.m;
        int c = pos % k.m;

        if (r >= 1 && c >= 1 && r < k.n && c < k.m && (k.grid[r][c] instanceof WhiteCell) && !visited[r][c]) {
            visited[r][c] = true;
            ++count;
            count += count_white_cells(visited, pos + 1);
            count += count_white_cells(visited, pos - 1);
            count += count_white_cells(visited, pos + k.m);
            count += count_white_cells(visited, pos - k.m);
        }
        return count;
    }

    private int next_white_cell_pos(Kakuro k, int pos) {
        int r, c;

        r = pos / k.m;
        c = pos % k.m;
        while (r < k.n && c < k.m && !(k.grid[r][c] instanceof WhiteCell)) {
            ++pos;
            r = pos / k.m;
            c = pos % k.m;
        }
        return pos;
    }

    /**
     * Comprovar que si es posa una casella negre a la fila r i columna c no es viola cap regla del Kakuro
     *
     * @return Retorna si la posicio es valida o no
     */
    private boolean check_pattern(int cell) {
        int r = cell / k.m;
        int c = cell % k.m;
        if (r - 2 >= 0 && k.grid[r - 1][c] instanceof WhiteCell && k.grid[r - 2][c] instanceof BlackCell) return false;
        if (c - 2 >= 0 && k.grid[r][c - 1] instanceof WhiteCell && k.grid[r][c - 2] instanceof BlackCell) return false;
        if (r + 2 < k.n && k.grid[r + 1][c] instanceof WhiteCell && k.grid[r + 2][c] instanceof BlackCell) return false;
        if (c + 2 < k.m && k.grid[r][c + 1] instanceof WhiteCell && k.grid[r][c + 2] instanceof BlackCell) return false;
        if (r + 2 == k.n && k.grid[r + 1][c] instanceof WhiteCell) return false;
        if (c + 2 == k.m && k.grid[r][c + 1] instanceof WhiteCell) return false;
        return true;
    }

    /**
     * Corregir Kakuro horitzontalment (no pot haver mes de 9 caselles blanques seguides)
     *
     * @return Retorna si s'ha pogut corregir o no
     */
    private boolean correct_grid(boolean row) {
        int count, r, c, r_ini, c_ini, max_i, max_j, bound;
        r_ini = c_ini = count = 0;

        bound = get_strip_bound();
        if (row) {
            max_i = k.n;
            max_j = k.m;
        }
        else {
            max_i = k.m;
            max_j = k.n;
        }

        for (int i = 1; i < max_i; ++i) {
            for (int j = 1; j < max_j; ++j) {
                if (row) {
                    r = i;
                    c = j;
                }
                else {
                    r = j;
                    c = i;
                }
                if (k.grid[r][c] instanceof WhiteCell) {
                    ++count;
                    if (count == 1) {
                        r_ini = r;
                        c_ini = c;
                    }
                    else if (count == bound) {
                        boolean painted = false;
                        int cell, next_black_cell = 0;

                        List<Integer> l;
                        if (row) l = get_candidate_positions_list(bound, max_j);
                        else l = get_candidate_positions_list(bound, max_i);
                        Iterator it = l.iterator();

                        while (it.hasNext() && !painted) {
                            next_black_cell = (int) it.next();
                            if (row) cell = r_ini * k.m + c_ini + next_black_cell;
                            else cell = (r_ini + next_black_cell) * k.m + c_ini;
                            paint_cells(cell, true);
                            painted = check_pattern(cell) && is_connected_grid();
                            if (!painted) paint_cells(cell, false);
                        }
                        if (!painted) return false;
                        else {
                            if (row) j = c_ini + next_black_cell;
                            else j = r_ini + next_black_cell;
                        }
                        bound = get_strip_bound();
                        count = 0;
                    }
                }
                else count = 0;
            }
            count = 0;
        }
        return true;
    }

    private int get_strip_bound() {
        int bound, n = new Random(System.currentTimeMillis()).nextInt(10);

        if (k.difficulty == 1) {
            if (n < 7) bound = 5;
            else bound = 8;
        }
        else if (k.difficulty == 2) {
            if (n < 2) bound = 5;
            else if (n < 8) bound = 8;
            else bound = 10;
        }
        else {
            if (n < 7) bound = 10;
            else bound = 8;
        }
        return bound;
    }

    private List<Integer> get_candidate_positions_list(int bound, int max) {
        List<Integer> l1, l2, l3, l4;

        l1 = new ArrayList<>(4);
        l2 = new ArrayList<>(8);
        l3 = new ArrayList<>(2);
        l4 = new ArrayList<>(bound);

        l1.add(0);
        for (int i = 2; i < 5 && i < max && i < bound; ++i) l1.add(i);
        for (int i = 5; i < 8 && i < max && i < bound; ++i) l2.add(i);
        for (int i = 8; i < 10 && i < max && i < bound; ++i) l3.add(i);

        Collections.shuffle(l1);
        Collections.shuffle(l2);
        Collections.shuffle(l3);

        if (bound == 5) l4.addAll(l1);
        else if (bound == 8) {
            l4.addAll(l2);
            l4.addAll(l1);
        }
        else {
            l4.addAll(l3);
            l4.addAll(l2);
            l4.addAll(l1);
        }
        return l4;
    }


    /**
     * Inicialitza SumCell si fa falta i calcula la seva suma vertical
     */
    private void get_sum_cells_column(HashMap<Integer, int[]> sum_cell_info) {
        for (int j = 1; j < k.m; ++j) {
            for (int i = 0; i < k.n; ++i) {
                if (k.grid[i][j] instanceof WhiteCell) k.grid[i][j].set_value(0);
                else if (is_next_cell_white(i, j, false)) {
                    if (!(k.grid[i][j] instanceof SumCell)) {
                        k.grid[i][j] = new SumCell();
                        sum_cell_info.put(i * k.m + j, new int[] {0, 0, 0, 0});
                    }
                    int r = i;
                    int c = j;
                    int sum_column = 0;

                    while (++i < k.n && k.grid[i][j] instanceof WhiteCell) {
                        sum_column += k.grid[i][j].get_value();
                        k.grid[i][j].set_value(0);
                    }
                    k.grid[r][c].set_sum_column(sum_column);
                    --i;

                    sum_cell_info.get(r * k.m + c)[1] = i - r;
                }
            }
        }
    }

    /**
     * Inicialitza SumCell si fa falta, emplena la fila de valors aleatoris i calcula la suma horitzontal
     *
     * @return Retorna false si fill_row es queda sense valors disponibles, altrament retorna true
     */
    private boolean get_sum_cells_row(HashMap<Integer, int[]> sum_cell_info) {
        k.solution = new HashMap<>(k.white_cells);
        for (int i = 1; i < k.n; ++i) {
            for (int j = 0; j < k.m; ++j) {
                if (is_next_cell_white(i, j, true)) {
                    k.grid[i][j] = new SumCell();
                    int next_j = fill_row(i , j);
                    if (j == -1) return false;
                    else {
                        sum_cell_info.put(i * k.m + j, new int[] {next_j - j, 0, 0, 0});
                        j = next_j;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Comprovar que la casella en la següent posició és blanca
     *
     * @return Retorna true si la casella en la següent posició és blanca, altrament retorna false
     */
    private boolean is_next_cell_white(int r, int c, boolean is_row) {
        if (is_row) return (++c < k.m && k.grid[r][c] instanceof WhiteCell);
        return (++r < k.n && k.grid[r][c] instanceof WhiteCell);
    }

    /**
     * Emplena les caselles a la dreta de SumCell amb valors aleatoris i calcula la suma horitzontal
     *
     * @return Retorna la columna de la ultima casella blanca que ha tractat
     */
    private int fill_row(int r, int c) {
        HashSet<Integer> repeated_row = new HashSet<>();
        int j = c;
        int sum_row = 0;

        while (++j < k.m && k.grid[r][j] instanceof WhiteCell) {
            k.solution.put(Integer.toString(r) + j, null);
            HashSet<Integer> valid = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

            valid.removeAll(repeated_row);
            if (valid.isEmpty()) return -1;

            int aux_i = r;
            while (--aux_i >= 0 && k.grid[aux_i][j] instanceof WhiteCell) valid.remove(k.grid[aux_i][j].get_value());
            if (valid.isEmpty()) return -1;

            int value = get_random_set_value(valid);
            k.grid[r][j].set_value(value);
            repeated_row.add(value);
            sum_row += value;
        }

        --j;
        k.grid[r][c].set_sum_row(sum_row);
        return j;
    }

    /**
     * Obtenir un valor aleatori del HashSet
     *
     * @param s El HashSet de valors
     * @return Retorn un valor aleatori del HashSet
     */
    private int get_random_set_value(HashSet<Integer> s) {
        Iterator<Integer> it = s.iterator();
        if (s.size() == 1) return it.next();
        int value = new Random().nextInt(s.size());
        int i = 0;

        while (it.hasNext()) {
            if (i == value) {
                value = it.next();
                break;
            }
            ++i;
            it.next();
        }
        return value;
    }
}

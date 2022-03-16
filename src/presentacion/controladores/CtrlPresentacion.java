/**
 * @file ./src/presentacion/controladores/CtrlPresentacion.java
 * @author Sergi Berdor
 */

package src.presentacion.controladores;

import src.dominio.controladores.CtrlDominio;
import src.dominio.clases.ExceptionKakuro;
import src.dominio.controladores.CtrlRankingDomini;
import src.presentacion.vistas.*;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @class CtrlPresentacion
 * @brief Classe del Controlador de Presentacio. S'encarrega de la comunicacio amb el CtrlDominio, del control de les
 * peticions de l'usuari i tambe de la interaccio amb l'usuari.
 */
public class CtrlPresentacion {

    //ATRIBUTS


    private CtrlDominio cDom = new CtrlDominio();
    private CtrlRankingDomini cRankDom = new CtrlRankingDomini();

    private VistaKakuro vistaKakuro;
    private VistaNewGame vistaNewGame;
    private VistaGestioPerfils vistaGestioPerfils;

    private VistaStatistics vistaStatistics;


    private VistaRanking vistaRanking;


    private VistaRepo vistaRepo;

    private String userIn = null;

    //CONSTRUCTORS


    public CtrlPresentacion() {
        vistaKakuro = new VistaKakuro(this);
        vistaNewGame = new VistaNewGame(this);
        vistaGestioPerfils = new VistaGestioPerfils(this);
        vistaStatistics = new VistaStatistics(this);
        vistaRanking = new VistaRanking(this);
        vistaRepo = new VistaRepo(this);
    }

    //METODES


    //GUI

    public void inicializarPresentacionGUI() throws FileNotFoundException{
        this.cDom.inicializarCtrlDominio();
        vistaKakuro.inicializarComponentes();
        vistaNewGame.inicializarComponentes();
        vistaStatistics.inicialitzar_vista();
        vistaRanking.inicialitzar_vista();
        vistaRepo.inicializarComponentes();

        gestio_perfils();
    }

    private void gestio_perfils() {
        vistaGestioPerfils.start_menu();
    }

    public void main_menu() {
        vistaGestioPerfils.main_menu();
    }

    public void new_game() {
        vistaNewGame.hacerVisible();
    }
    public void startGameEntry(int i,int diff) throws ExceptionKakuro {
        try{
            if(i == 99) {
                cDom.readRepoGame(userIn);
            }else {
                cDom.readRepoKakuro(i, diff);
            }
        } catch (FileNotFoundException ignore) { }
        vistaNewGame.hacerInvisible();
        vistaKakuro.hacerVisible(true,userIn);
    }
    private void startGameGenerate(String name) {
        vistaNewGame.hacerInvisible();
        vistaKakuro.hacerVisible(false,userIn);
    }
    public void endGame() {
        vistaKakuro.hacerInvisible();
        new_game();
    }
    public void saveKakuro(String name) throws Exception {
        cDom.saveKakuro(name, true);
    }
    public void goBack(int i) {
        switch (i){
            case 0:
                vistaNewGame.hacerInvisible();
                vistaGestioPerfils.main_menu();
                break;
            case 1:
                vistaKakuro.hacerInvisible();
                vistaNewGame.hacerVisible();
                break;
        }
    }
    public int sendHelp(int r, int c) throws ExceptionKakuro {
        return cDom.sendHelp(r,c);
    }
    public void startGameGenerate() {
        vistaNewGame.hacerInvisible();
        vistaKakuro.hacerVisible(false,userIn);
    }
    public String[] getListFiles(int diff) {
        return this.cDom.getListOfFiles(diff);
    }
    public void setUser(String user){ userIn = user;}
    public void savePartida(boolean ajuda) throws Exception { cDom.savePartida(userIn, ajuda);}
    public boolean hasPartida() {return cDom.hasPartida(userIn);}
    public boolean getHelp() {return cDom.getHelp();}




//terminal



    public void inicializarPresentacionTerminal() throws FileNotFoundException {
        this.cDom.inicializarCtrlDominio();
        start();
    }

    public void start() throws FileNotFoundException{
        Scanner reader = new Scanner(System.in);
        int order;

        System.out.println("Press (1) to Start");
        while (reader.nextInt() == 1) {
            loginMenu();

            System.out.println("\n(1) Return LOG IN MENU");
            System.out.println("(>=2) Exit game");
        }
    }


    public void loginMenu() throws FileNotFoundException{
        System.out.println("LOG IN MENU");
        System.out.println("(1) Log in");
        System.out.println("(2) New User");
        System.out.println("(3) Delete User");
        System.out.println("(>=4) Shut down");

        Scanner reader = new Scanner(System.in);
        int order;
        order = reader.nextInt();
        if (order == 1) {
            System.out.print("Insert username: ");
            reader = new Scanner(System.in);
            String user = reader.nextLine();
            if (!existsuser(user)) System.out.println("User does not exist");
            else {
                System.out.print("Insert password: ");
                reader = new Scanner(System.in);
                String password = reader.nextLine();
                if (correctpassword(user,password)) {
                    System.out.println("Logged in with user: " + user);
                    userIn = user;
                    System.out.println();

                    System.out.println("Press (1) to go to MAIN MENU");
                    while (reader.nextInt() == 1) {
                        mainMenu(user);

                        System.out.println("\n(1) Return MAIN MENU");
                        System.out.println("(>=2) Log out");
                    }
                }
                else {
                    System.out.println("Incorrect password");
                }
            }
        }
        else if (order == 2) {
            System.out.print("Insert username: ");
            reader = new Scanner(System.in);
            String user = reader.nextLine();
            System.out.print("Insert password: ");
            String password = reader.nextLine();
            try {
                saveprofile(user,password);
                System.out.println("New user: " + user + " was created");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        else if (order == 3) {
            System.out.print("Insert username: ");
            reader = new Scanner(System.in);
            String user = reader.nextLine();
            System.out.print("Insert password: ");
            String password = reader.nextLine();
            try {
                cDom.deleteUser(user, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }



    private void mainMenu(String user) throws FileNotFoundException{
        System.out.println("MAIN MENU");
        System.out.println("(1) New game");
        System.out.println("(2) Access to repository");
        System.out.println("(3) View user statistics");
        System.out.println("(4) View rankings");
        System.out.println("(>=5) Return");

        Scanner reader = new Scanner(System.in);
        int order;
        order = reader.nextInt();
        if (order == 1) mainMenuGame(user);
        else if (order == 2) menuRepo();
        else if (order == 3) printUserStatistics(user);
        else if (order == 4) printRanking();

    }


    public void mainMenuGame(String user) throws FileNotFoundException{

        Scanner reader = new Scanner(System.in);

        System.out.println("GAME MENU");
        System.out.println("(1) Enter Kakuro");
        System.out.println("(2) Download Kakuro");
        System.out.println("(3) Generate Kakuro");
        System.out.println("(>=4) Return");

        int order = Integer.valueOf(reader.next());

        if (order == 1) {
            System.out.println("Please Introduce a Kakuro:");

            try {
                readMatrix(true);
                System.out.println("Save Kakuro? (y/n)");
                String save = reader.next();
                if(save.equals("y")) {
                    System.out.println("Please Introduce a name:");
                    String name = reader.next();
                    cDom.savePartida(name,false);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
                mainMenuGame(user);
            }
            gameMenu(user, false);
        } else if (order == 2) {
            System.out.println("Enter a number to select a diff:");
            int diff = reader.nextInt();
            String[] list = listFiles(diff, true);
            System.out.println("Enter a number to select a file:");
            int file = reader.nextInt();

            while ((file < 0 || file >= list.length) && file != 99 ) {
                System.out.println("Invalid number");
                System.out.println("Enter a number to select a file:");
                file = reader.nextInt();
            }
            try {
                if(file == 99 && diff == 0) cDom.readRepoGame(userIn);
                else cDom.readRepoKakuro(file, diff);
                gameMenu(user, true);
            }catch (ExceptionKakuro e) {
                System.out.println(e.getMessage());
            }
        }
        else if(order == 3) {
            System.out.println("Introduce kakuro size and difficulty: (n,m,diff)");
            System.out.println("Easy(1): n,m >= 3");
            System.out.println("Mid(2): n >= 6 or m >= 6");
            System.out.println("Hard(3): n >= 9 or m >= 9");
            String line = reader.next();
            Scanner sc = new Scanner(line);
            sc.useDelimiter(",| +");

            int n = Integer.parseInt(sc.next());
            int m = Integer.parseInt(sc.next());
            int difficulty = Integer.parseInt(sc.next());

            while((n < 3 || m < 3) ||
                    (difficulty == 2 && n < 6 && m < 6) ||
                    (difficulty == 3 && n < 9 && m < 9)) {
                System.out.println("Introduce a correct diff and size");
                line = reader.next();
                sc = new Scanner(line);
                sc.useDelimiter(",| +");
                n = Integer.parseInt(sc.next());
                m = Integer.parseInt(sc.next());
                difficulty = Integer.parseInt(sc.next());
            }
            cDom.generatePartida(n, m, difficulty);
            printMatrix(false);
            System.out.println("Save Kakuro? (y/n)");
            String save = reader.next();
            if(save.equals("y")) {
                System.out.println("Please Introduce a name:");
                String name = reader.next();
                try {
                    cDom.savePartida(name,false);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            gameMenu(user, false);
        }
    }


    private void gameMenu(String user, boolean repo) {
        Scanner reader = new Scanner(System.in);
        printMatrix(false);

        System.out.println("\nGAME MENU");
        System.out.println("(1) Manual Solver");
        System.out.println("(2) Automatic Solver");
        System.out.println("(>=3) Return");

        int order = reader.nextInt();
        if (order == 1) {
            play(user, repo);
            //validar solució final
        } else if (order == 2) {
            printMatrix(true);
        }
    }


    private void menuRepo() {
        Scanner reader = new Scanner(System.in);
        System.out.println("REPOSITORY MENU");
        System.out.println("(1) Save Kakuro");
        System.out.println("(2) Delete Kakuro");
        System.out.println("(>=3) Return");

        int order = reader.nextInt();

        if (order == 1) {
            System.out.println("Please Introduce a Kakuro:");
            try {
                readMatrix(false);
            }catch (ExceptionKakuro e) {
                System.out.println(e.getMessage());
                menuRepo();
            }
            System.out.println("Please Introduce a name:");
            String name = reader.next();
            try {
                cDom.saveKakuro(name,false);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if(order == 2) {
            try {
                listFiles(0, false);
                System.out.println("Enter a number to select a file:");
                int file = reader.nextInt();
                cDom.deleteKakuro(file);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

    private void play(String user, boolean repo) {
        printMatrix(false);
        Scanner sc = new Scanner(System.in);
        boolean ajuda = cDom.getHelp();

        while (true) {
            if(!repo) System.out.println( "USAGE: (row) (col) (value) /// (row) (col) (-2) 'ask for a hint' /// (-1) 'Finish' /// (-2) 'Save Kakuro'" +
                    "/// (-3) 'Save Game'\n");
            else System.out.println( "USAGE: (row) (col) (value) /// (row) (col) (-2) 'ask for a hint' /// (-1) 'Finish' /// (-3) 'Save Partida' \n");
            int r = sc.nextInt();
            if(r == -1) {
                break;
            }
            else if(r == -3) {
                try {
                    cDom.savePartida(user,ajuda);
                    System.out.println("saved");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                continue;
            }
            else if(!repo && r == -2) {
                System.out.println("Enter name:");
                String name = sc.next();
                try {
                    cDom.saveKakuro(name, true);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("saved");
                continue;
            }
            int c = sc.nextInt();
            int value = sc.nextInt();
            try {
                if(value == -2) {
                    ajuda = true;
                    int help = 0;
                    try {
                        help = cDom.sendHelp(r,c);
                        System.out.println(help);
                    } catch (ExceptionKakuro exceptionKakuro) {
                        System.out.println(exceptionKakuro.getMessage());
                    }
                }
                else if(value == 0) cDom.setValue(r,c,-1);
                else {
                    if (cDom.setValue(r, c, value)) {
                        Duration d = Duration.ofMillis(cDom.getDuration());
                        System.out.println("\n Solved:) in " + d.toMinutesPart() + ':' + d.toSecondsPart());
                        cDom.updateStatistics(user, ajuda, d, cDom.get_difficulty());
                        if (!ajuda) cRankDom.updateRanking(user, d, cDom.get_difficulty());
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
            printMatrix(false);
            System.out.println();
        }
        printMatrix(false);
    }




    //metodes auxiliars
    public boolean existsuser(String username) {
        return cDom.existsUser(username);
    }
    public boolean correctpassword (String user, String password) {
        try {
            return password.equals(cDom.getPassword(user));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void saveprofile(String username, String password) throws Exception {
        cDom.saveUser(username, password);
    }
    public void deleteprofile(String username, String password) throws Exception {
        cDom.deleteUser(username,password);
    }
    public String[] getListOfFiles() {
        return cDom.getListOfFiles(0);
    }
    public boolean setValue(int r, int c, int value) throws ExceptionKakuro { return cDom.setValue(r,c,value);}

    public String[][] getMatrix(boolean solucio) {
        if (!solucio) return cDom.writeKakuro();
        return cDom.writeKakuroSol();
    }
    public long getDuration() {
        return cDom.getDuration();
    }
    public void enterKakuro(String matrixText) throws ExceptionKakuro {

        Scanner reader = new Scanner(matrixText);
        Scanner sc = new Scanner(reader.nextLine());
        sc.useDelimiter(",| +");

        int n = Integer.parseInt(sc.next());
        int m = Integer.parseInt(sc.next());
        String[][] matrix = new String[n][m];

        for (int i = 0; i < n; ++i) {
            sc = new Scanner(reader.nextLine());
            sc.useDelimiter(",| +");
            for (int j = 0; j < m; ++j) matrix[i][j] = sc.next();
        }
        cDom.readPartida(n, m, matrix);
        vistaNewGame.hacerInvisible();
        vistaKakuro.hacerVisible(false,userIn);
    }
    public String[][] generateKakuro( String size, String diff) throws ExceptionKakuro {
        Scanner reader = new Scanner(size);
        Scanner sc = new Scanner(reader.nextLine());
        sc.useDelimiter(",| +");

        int n = Integer.parseInt(sc.next());
        int m = Integer.parseInt(sc.next());
        int difficulty = Integer.parseInt(diff);

        if ((n < 3 || m < 3) ||
                (difficulty == 2 && n < 6 && m < 6) ||
                (difficulty == 3 && n < 9 && m < 9)) {
            throw new ExceptionKakuro("Introduce a correct diff and size");
        }

        cDom.generatePartida(n,m,difficulty); //1,2,3 de fàcil a difícil
        return getMatrix(false);
    }

    public void readMatrix(boolean partida) throws ExceptionKakuro{
        System.out.println("Introduce kakuro size: (n,m)");
        Scanner reader = new Scanner(System.in);
        Scanner sc = new Scanner(reader.nextLine());
        sc.useDelimiter(",| +");

        int n = Integer.parseInt(sc.next());
        int m = Integer.parseInt(sc.next());
        String[][] matrix = new String[n][m];

        System.out.println("Introduce kakuro:");
        for (int i = 0; i < n; ++i) {
            sc = new Scanner(reader.nextLine());
            sc.useDelimiter(",| +");
            for (int j = 0; j < m; ++j) matrix[i][j] = sc.next();
        }
        if(partida) cDom.readPartida(n, m, matrix);
        else cDom.readKakuro(n,m,matrix);
    }


    public String[] listFiles(int diff, boolean enPartida) {
        String[] list = cDom.getListOfFiles(diff);
        if(enPartida && diff == 0 && cDom.hasPartida(userIn)) {
            System.out.println(99 + "-->" + "continue last game");
        }
        for (int i = 0; i < list.length; i++) {
            System.out.println(i + "-->" + list[i]);
        }
        return list;
    }

    public void printMatrix(boolean solucio) {
        Integer [] size = cDom.sizeKakuro();
        String [][] matrix;
        if (!solucio) matrix = cDom.writeKakuro();
        else matrix = cDom.writeKakuroSol();

        for (int i = 0; i < size[0]; ++i) {
            for (int j = 0; j < size[1]; ++j) {
                System.out.print(matrix[i][j] + ' ');
            }
            System.out.println();
        }
    }

    public void printUserStatistics(String user) {
        String[] statistics = getUserStatistics(user);
        System.out.println("Easy Kakuros solved: " + statistics[0]);
        System.out.println("Medium Kakuros solved: " + statistics[1]);
        System.out.println("Hard Kakuros solved: " + statistics[2]);
        System.out.println("EasyKakuros solved without help: " + statistics[3]);
        System.out.println("Medium Kakuros solved without help: " + statistics[4]);
        System.out.println("Hard Kakuros solved without help: " + statistics[5]);
        System.out.print("Average time to solve a kakuro without help: ");
        int hours, minutes, seconds, totalGames;
        totalGames = Integer.parseInt(statistics[3]) + Integer.parseInt(statistics[4]) + Integer.parseInt(statistics[5]);
        if (totalGames != 0) {
            hours = Integer.parseInt(statistics[6]);
            minutes = Integer.parseInt(statistics[7]);
            seconds = Integer.parseInt(statistics[8]);
            seconds = (hours * 3600 + minutes * 60 + seconds) / totalGames;
            if (seconds / 3600 < 10) {
                System.out.print("0" + seconds / 3600 + ":");
            } else {
                System.out.print(seconds / 3600 + ":");
            }

            if ((seconds % 3600) / 60 < 10) {
                System.out.print("0" + (seconds % 3600) / 60 + ":");
            } else {
                System.out.print((seconds % 3600) / 60 + ":");
            }

            if ((seconds % 3600) % 60 < 10) {
                System.out.println("0" + (seconds % 3600) % 60);
            } else {
                System.out.println((seconds % 3600) % 60);
            }
        } else System.out.println("00:00:00");
    }


    public void printRanking() {
        ArrayList<ArrayList<String>> RankingFacil = null;
        ArrayList<ArrayList<String>> RankingMitja = null;
        ArrayList<ArrayList<String>> RankingDificil = null;
        try {
            RankingFacil = cRankDom.getRankingPerDiff(1);
            RankingMitja = cRankDom.getRankingPerDiff(2);
            RankingDificil = cRankDom.getRankingPerDiff(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Top 10 kakuros de nivell fàcil: ");
        printRank(RankingFacil);

        System.out.println();
        System.out.println("Top 10 kakuros de nivell mitjà: ");
        printRank(RankingMitja);

        System.out.println();
        System.out.println("Top 10 kakuros de nivell difícil: ");
        printRank(RankingDificil);
    }

    private void printRank(ArrayList<ArrayList<String>> ranking) {
        for (int i = 0; i < ranking.size(); ++i) {
            for (int j = 0; j < ranking.get(i).size(); ++j) {
                if (j == 0) System.out.print(i + 1 + ". " + ranking.get(i).get(j) + ": ");
                else if (j == 1 || j == 2) {
                    if (Integer.parseInt(ranking.get(i).get(j)) < 10) {
                        System.out.print("0" + ranking.get(i).get(j) + ":");
                    } else {
                        System.out.print(ranking.get(i).get(j) + ":");
                    }
                } else {
                    if (Integer.parseInt(ranking.get(i).get(j)) < 10) {
                        System.out.println("0" + ranking.get(i).get(j));
                    } else {
                        System.out.println(ranking.get(i).get(j));
                    }
                }
            }
        }
    }

    public void viewUserStatistics() {
        vistaStatistics.hacerVisible(userIn);
    }

    public void viewRanking() {
        vistaRanking.hacerVisible();
    }

    public void viewRepo() {
        vistaRepo.hacerVisible(cDom.getListOfFiles(0));
    }

    public boolean deleteKakuro(int i) {
        try {
            return cDom.deleteKakuro(i);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean saveKakuro(String name, String matrixText) throws Exception {
        Scanner reader = new Scanner(matrixText);
        Scanner sc = new Scanner(reader.nextLine());
        sc.useDelimiter(",| +");

        int n = Integer.parseInt(sc.next());
        int m = Integer.parseInt(sc.next());
        String[][] matrix = new String[n][m];

        for (int i = 0; i < n; ++i) {
            sc = new Scanner(reader.nextLine());
            sc.useDelimiter(",| +");
            for (int j = 0; j < m; ++j) matrix[i][j] = sc.next();
        }
        cDom.readKakuro(n,m,matrix);
        return cDom.saveKakuro(name, false);
    }
    public ArrayList<ArrayList<String>> getRankingPerDiff (int diff) throws Exception {
        return cRankDom.getRankingPerDiff(diff);
    }

    public String[] getUserStatistics(String user) {
        return cDom.getUserStatistics(user);
    }

    public void updateStatistics(String user, boolean ajuda, Duration d) {
        cDom.updateStatistics(user, ajuda, d, cDom.get_difficulty());
    }

    public void updateRanking(String user, Duration d)  {
        try {
            cRankDom.updateRanking(user, d, cDom.get_difficulty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * @file ./src/presentacion/vistas/VistaKakuro.java
 * @author Sergi Berdor
 */
package src.presentacion.vistas;

import src.dominio.clases.ExceptionKakuro;
import src.presentacion.controladores.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
/**
 * @class VistaKakuro
 * @brief Classe que representa la vista per exportar kakuros a la GUI.
 */
public class VistaKakuro {

    private CtrlPresentacion controladorPresentacion;
    private boolean repo = false;

    private JFrame frameVista = new JFrame("Kakuro");

    private JPanel panelContenidos = new JPanel();
    private JPanel panelKakuro = new JPanel();
    private JLabel labelError = new JLabel(" ");
    JMenuBar menuBar = new JMenuBar();


    private JTextField[][] tfCells = new JTextField[0][0];

    private String[][] matrix = new String[0][0];

    public static int GRID_SIZE_X;
    public static int GRID_SIZE_Y;
    public static final int CELL_SIZE = 40;

    public static int CANVAS_WIDTH;
    public static int CANVAS_HEIGHT;

    public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    public static final Color CLOSED_CELL_TEXT = Color.BLACK;

    private boolean finished = false;
    private boolean ajuda = false;
    private String userIn = null;
    private boolean autosolv = false;

    //private String userActual = null;

    public VistaKakuro(CtrlPresentacion pCtrlPresentacion) {
        controladorPresentacion = pCtrlPresentacion;
    }

    public void inicializarComponentes() {
        inicializarFrameVista();
        inicializarPanelContenidos();
    }

    private void inicializarFrameVista() {
        frameVista.setResizable(false);
        // Posicion y operaciones por defecto
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);
    }

    public void hacerInvisible() {
        frameVista.setVisible(false);
    }

    private void inicializarPanelContenidos() {
        // Layout
        panelContenidos.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new BorderLayout());

        inicializarMenuBar();

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Duration d = Duration.ofMillis(controladorPresentacion.getDuration());
                    showDialog(d);
                }
            }
        );

        panelBotones.add(submit, BorderLayout.CENTER);

        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        panelKakuro.setBorder(padding);
        panelBotones.setBorder(padding);

        JPanel panelKakuroErr = new JPanel(new BorderLayout());
        labelError.setForeground(Color.RED);
        labelError.setHorizontalAlignment(JLabel.CENTER);
        labelError.setVerticalAlignment(JLabel.CENTER);


        panelKakuroErr.add(panelKakuro, BorderLayout.NORTH);
        panelKakuroErr.add(labelError, BorderLayout.SOUTH);




        // Paneles
        panelContenidos.add(menuBar, BorderLayout.NORTH);
        panelContenidos.add(panelKakuroErr, BorderLayout.CENTER);
        panelContenidos.add(panelBotones, BorderLayout.PAGE_END);
    }

    private void inicializarMenuBar() {
        menuBar.removeAll();
        JMenu options = new JMenu("Options");
        JMenuItem savek = new JMenuItem("Save kakuro");
        JMenuItem saveg = new JMenuItem("Save game");
        JMenuItem saveExitk = new JMenuItem("Save kakuro & exit");
        JMenuItem saveExitg = new JMenuItem("Save game & exit");
        JMenuItem exit = new JMenuItem("Exit");
        JMenu help = new JMenu("Help");
        JMenuItem autosolver = new JMenuItem("Autosolver");
        JMenuItem instructions = new JMenuItem("Instructions");

        autosolver.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                             matrix = controladorPresentacion.getMatrix(true);
                                             printMatrix(true);
                                             finished = true;
                                             autosolv = true;
                                         }
                                     }
        );

        instructions.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               showDialogInfo();
                                           }
                                       }
        );

        savek.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            controladorPresentacion.saveKakuro(showDialogName());
                                        } catch (Exception exception) {
                                            showDialogError(exception.getMessage());
                                        }
                                    }
                                }
        );
        saveg.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            controladorPresentacion.savePartida(ajuda);
                                        } catch (Exception exception) {
                                            showDialogError(exception.getMessage());
                                        }
                                    }
                                }
        );
        saveExitk.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            try {
                                                controladorPresentacion.saveKakuro(showDialogName());
                                                controladorPresentacion.goBack(1);
                                            } catch (Exception exception) {
                                                showDialogError(exception.getMessage());
                                            }
                                        }
                                    }
        );
        saveExitg.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            try {
                                                controladorPresentacion.savePartida(ajuda);
                                                controladorPresentacion.goBack(1);
                                            } catch (Exception exception) {
                                                showDialogError(exception.getMessage());
                                            }
                                        }
                                    }
        );
        exit.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       controladorPresentacion.goBack(1);
                                   }
                               }
        );


        if(!repo) {
            options.add(savek);
            options.add(saveExitk);
        }
        options.add(saveg);
        options.add(saveExitg);
        options.add(exit);
        menuBar.add(options);
        help.add(autosolver);
        help.add(instructions);
        menuBar.add(help);
    }
    private void showDialog(Duration d) {
        if (finished) {
            JOptionPane.showMessageDialog(null,
                    "Solved:) in " + d.toMinutesPart() + ':' + d.toSecondsPart(),
                    "Congratulations !!!",
                    JOptionPane.PLAIN_MESSAGE);
            if (!autosolv) controladorPresentacion.updateStatistics(userIn, ajuda, d);
            if (!autosolv && !ajuda) controladorPresentacion.updateRanking(userIn, d);
            ajuda = false;
            autosolv = false;
            controladorPresentacion.endGame();

        }
        else {
            JOptionPane.showMessageDialog(null,
                    "Kakuro not finished",
                    "Not yet !",
                    JOptionPane.PLAIN_MESSAGE);

        }
    }

    private void showDialogInfo() {
        JLabel text = new JLabel("<html>\n" +
                "<ul>\n" +
                "<li>Kakuro puzzles are similar with crosswords, but instead of letters the board is filled with digits (from 1 to 9).\n" +
                "<li>The board's squares need to be filled in with these digits in order to sum up to the specified numbers.\n" +
                "<li>You are not allowed to use the same digit more than once to obtain a given sum.\n" +
                "<li>Each Kakuro puzzle has an unique solution. Good luck!\n" +
                "<li>Write '0' to show the correct value.\n"+
                "</ul>\n");

        JLabel hyperlink = new JLabel("<html>\n"+
                "<ul>\n"+
                "<li>Visit Kakuro Combinations – jalu.ch\n"+
                "<ul>\n");
        hyperlink.setForeground(Color.BLUE.darker());
        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                   try {

                       Desktop.getDesktop().browse(new URI("https://jalu.ch/coding/kakuro_combinations.php"));

                   } catch (IOException | URISyntaxException e1) {
                       showDialogError(e1.getMessage());
                   }
               }
           });

        JPanel info = new JPanel(new BorderLayout());
        info.add(text, BorderLayout.NORTH);
        info.add(hyperlink, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(null,
                info,
                "Instrucctions",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void hacerVisible(boolean repo,String user) {
        this.repo = repo;
        userIn = user;
        ajuda = controladorPresentacion.getHelp();
        matrix = controladorPresentacion.getMatrix(showOptionDialog());
        inicializarMenuBar();
        inicializarFrameVista();
        inicializarPanelKakuro();
        frameVista.pack();
        frameVista.setLocationRelativeTo(null);
        frameVista.setVisible(true);
    }

    private String showDialogName() {
        Object name = JOptionPane.showInputDialog(null, "Enter name:");
        return name.toString();
    }

    private void showDialogError(String error) {
        JOptionPane.showMessageDialog(null,
                error,
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private boolean showOptionDialog() {
        Object[] options = { "Manual Solver", "Automatic Solver"};
        int x = JOptionPane.showOptionDialog(null, "Choose solver method",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        finished = (x == 1);
        autosolv = (x == 1);
        return x == 1;
    }

    private void inicializarPanelKakuro() {

        GRID_SIZE_X = matrix.length;
        GRID_SIZE_Y = matrix[0].length;
        CANVAS_WIDTH = CELL_SIZE * GRID_SIZE_X;
        CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE_Y;

        panelKakuro.removeAll();
        panelKakuro.setLayout(new GridLayout(GRID_SIZE_X, GRID_SIZE_Y));
        panelKakuro.setPreferredSize(new Dimension(CANVAS_HEIGHT + 100, CANVAS_WIDTH));

        tfCells = new JTextField[GRID_SIZE_X][GRID_SIZE_Y];
        printMatrix(false);

    }

    private void printMatrix(boolean solution) {
        // Construct 9x9 JTextFields and add to the content-pane
        for (int row = 0; row < GRID_SIZE_X; ++row) {
            for (int col = 0; col < GRID_SIZE_Y; ++col) {
                if( !solution) {
                    tfCells[row][col] = new JTextField(1); // Allocate element of array
                    panelKakuro.add(tfCells[row][col]);            // ContentPane adds JTextField
                }
                tfCells[row][col].setFont(new Font("Sans Serif", Font.PLAIN, 10));
                if (matrix[row][col].equals("*")) {
                    tfCells[row][col].setText("");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(CLOSED_CELL_TEXT);
                } else if (matrix[row][col].equals("?")) {
                    tfCells[row][col].setText("");     // set to empty string
                    tfCells[row][col].setEditable(true);
                    tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                    addListenersCells(tfCells[row][col]);
                } else if (matrix[row][col].charAt(0) != 'C' &&
                        matrix[row][col].charAt(0) != 'F') {
                    tfCells[row][col].setText(matrix[row][col]);
                    tfCells[row][col].setEditable(true);
                    tfCells[row][col].setBackground(Color.WHITE);
                    addListenersCells(tfCells[row][col]);
                } else {
                    tfCells[row][col].setText(matrix[row][col] + "");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(Color.LIGHT_GRAY);
                    tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
                }
                // Beautify all the cells
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }

    private void addListenersCells(JTextField jTextField) {
        jTextField.addKeyListener(new KeyAdapter() {
              @Override
              public void keyReleased(KeyEvent e) {
                  int keyCode = e.getExtendedKeyCode();

                  String value = jTextField.getText();
                  int l = value.length();

                  if(l < 1) {
                      jTextField.setBackground(CLOSED_CELL_BGCOLOR);
                  }
                  else if(l > 1 || value.charAt(0) < '0' || value.charAt(0) > '9') {
                      jTextField.setBackground(Color.red);
                      return;
                  }

                  if (keyCode == KeyEvent.VK_DELETE && jTextField.getText().isEmpty()
                        && !Character.isSpaceChar(jTextField.getText().charAt(0))) {
                      setValue(jTextField, -1);
                      jTextField.setBackground(CLOSED_CELL_BGCOLOR);
                  }
                  else if(keyCode == KeyEvent.VK_ENTER && !jTextField.getText().isEmpty()
                          && !Character.isSpaceChar(jTextField.getText().charAt(0))) {
                      jTextField.setBackground(Color.WHITE);
                      setValue(jTextField, Integer.parseInt(value));

                  }
              }
          }
        );
    }

    private void setValue(JTextField jTextField, int value) {
        int rowSelected = -1;
        int colSelected = -1;

        for (int row = 0; row < GRID_SIZE_X; ++row) {
            for (int col = 0; col < GRID_SIZE_Y; ++col) {
                if (tfCells[row][col] == jTextField) {
                    rowSelected = row;
                    colSelected = col;
                    break;
                }
            }
        }
        if (value == 0) {
            try {
                ajuda = true;
                value = controladorPresentacion.sendHelp(rowSelected, colSelected);
            } catch (ExceptionKakuro exceptionKakuro) {
                // No ha de pasar mai sempre es demanarà ajuda sobre una cela correcta
                showDialogError(exceptionKakuro.getMessage());
            }
            tfCells[rowSelected][colSelected].setText(String.valueOf(value));
            tfCells[rowSelected][colSelected].setBackground(CLOSED_CELL_BGCOLOR);
            tfCells[rowSelected][colSelected].setEditable(false);
        }
        else if (value == -1) {
            try {
                controladorPresentacion.setValue(rowSelected, colSelected, value);
            } catch (ExceptionKakuro ignored) {
            }
        }
        try {
            finished = controladorPresentacion.setValue(rowSelected, colSelected, value);
            labelError.setText(" ");
        } catch (ExceptionKakuro ek) {
            labelError.setText("Row[" + rowSelected +"],Col[" + colSelected + "], value["+ value +"]: " +ek.getMessage());
            tfCells[rowSelected][colSelected].setText("");
        }

    }
}

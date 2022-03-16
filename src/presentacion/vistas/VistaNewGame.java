/**
 * @file ./src/presentacion/vistas/VistaNewGame.java
 * @author Sergi Berdor
 */
package src.presentacion.vistas;

import src.dominio.clases.ExceptionKakuro;
import src.presentacion.controladores.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class VistaNewGame
 * @brief Classe que representa la vista per iniciar  una partida en GUI.
 */
public class VistaNewGame {

    private CtrlPresentacion controladorPresentacion;

    private JFrame frameVista = new JFrame("New Game");
    private JPanel panelContenidos = new JPanel();
    private JPanel downloadPanel = new JPanel();

    public VistaNewGame(CtrlPresentacion pCtrlPresentacion) {
        controladorPresentacion = pCtrlPresentacion;
    }

    public void inicializarComponentes() {
        inicializarFrameVista();
        inicializarPanelContenidos();
    }

    public void hacerVisible() {
        createDownloadKakuro(0);
        frameVista.pack();
        frameVista.setLocationRelativeTo(null);
        frameVista.setVisible(true);
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

    private void inicializarPanelContenidos() {
        panelContenidos.setLayout(new BorderLayout());

        JPanel createPanel = createEnterKakuro();
        downloadPanel = new JPanel();
        JPanel generatePanel = createGenerateKakuro();


        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        createPanel.setBorder(padding);
        downloadPanel.setBorder(padding);
        generatePanel.setBorder(padding);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Enter Kakuro", null, createPanel);
        tabbedPane.addTab("Download Kakuro", null, downloadPanel);
        tabbedPane.addTab("Generate Kakuro", null, generatePanel);
        panelContenidos.add(tabbedPane, BorderLayout.CENTER);
    }

    private void createDownloadKakuro(int diff) {

        downloadPanel.removeAll();

        String[] diffString = { "Todos", "Facil", "Medio", "Dificil" };

        JComboBox diffList = new JComboBox(diffString);
        diffList.setSelectedIndex(diff);
        diffList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                int diffNext = cb.getSelectedIndex();
                createDownloadKakuro(diffNext);
            }
        });

        String[] listOfFiles = controladorPresentacion.getListFiles(diff);

        int numButtons = listOfFiles.length;
        if(diff == 0 && controladorPresentacion.hasPartida()) numButtons += 1;

        JRadioButton[] radioButtons = new JRadioButton[numButtons];
        final ButtonGroup group = new ButtonGroup();

        JPanel panelBotones = new JPanel(new BorderLayout());

        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = group.getSelection().getActionCommand();
                        try {
                            controladorPresentacion.startGameEntry(Integer.parseInt(command),diff);
                        } catch (ExceptionKakuro exceptionKakuro) {
                            showDialogError(exceptionKakuro.getMessage());
                        }
                    }
                }

        );

        JButton goBack = new JButton("Return");
        goBack.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         controladorPresentacion.goBack(0);
                                     }
                                 }

        );


        panelBotones.add(goBack, BorderLayout.WEST);
        panelBotones.add(startGame, BorderLayout.EAST);

        int l = 0;
        if(diff == 0 && controladorPresentacion.hasPartida()) {
            radioButtons[0] = new JRadioButton("Continue last game");
            radioButtons[0].setActionCommand(String.valueOf(99));
            group.add(radioButtons[0]);
            radioButtons[0].setSelected(true);
            numButtons--;
            ++l;
        }
        for(int i = 0; i < numButtons; ++i) {
            radioButtons[l] = new JRadioButton(listOfFiles[i]);
            radioButtons[l].setActionCommand(String.valueOf(i));
            group.add(radioButtons[l]);
            if ( l == 0) {
                radioButtons[l].setSelected(true);
            }
            ++l;
        }
        createPaneDownload("Some kakuro examples:", diff, radioButtons, panelBotones, diffList);
    }

    public void hacerInvisible() {
        frameVista.setVisible(false);
    }

    private JPanel createEnterKakuro() {

        JLabel jLabel2 = new JLabel("Enter kakuro:");

        JTextArea textAreaKakuro = new JTextArea(20,20);
        textAreaKakuro.setLineWrap(true);

        JPanel borderKakuro = new JPanel(new BorderLayout());
        borderKakuro.add(jLabel2, BorderLayout.PAGE_START);
        borderKakuro.add(textAreaKakuro, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new BorderLayout());

        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(textAreaKakuro.getText().isBlank()) showErrorDialog("Name empty");
                        else if(textAreaKakuro.getText().isBlank()) showErrorDialog("Kakuro empty");
                        else controladorPresentacion.enterKakuro(textAreaKakuro.getText());
                    } catch (ExceptionKakuro exceptionKakuro) {
                        showErrorDialog(exceptionKakuro.getMessage());
                    }
                }
            }
        );

        JButton goBack = new JButton("Return");
        goBack.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         controladorPresentacion.goBack(0);
                                     }
                                 }
        );

        panelBotones.add(goBack, BorderLayout.WEST);
        panelBotones.add(startGame, BorderLayout.EAST);

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(borderKakuro, BorderLayout.CENTER);
        pane.add(panelBotones, BorderLayout.PAGE_END);

        return pane;
    }

    private void createPaneDownload(String description, int dif, JRadioButton[] radioButtons, JPanel panelBotones, JComboBox diffList) {

        int numChoices = radioButtons.length;
        JPanel box = new JPanel();
        JLabel label = new JLabel(description);

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(label);

        for (int i = 0; i < numChoices; i++) {
            box.add(radioButtons[i]);
        }

        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setPreferredSize(new Dimension(200,300));

        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        downloadPanel.setBorder(padding);
        downloadPanel.setLayout(new BorderLayout());
        downloadPanel.add(diffList, BorderLayout.NORTH);
        downloadPanel.add(scrollPane, BorderLayout.CENTER);
        downloadPanel.add(panelBotones, BorderLayout.SOUTH);
    }

    private void showDialogError(String error) {
        JOptionPane.showMessageDialog(null,
                error,
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel createGenerateKakuro() {
        JLabel jLabel1 = new JLabel("Enter size:");
        JPanel borderSize = new JPanel();

        borderSize.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        JTextArea textAreaSize = new JTextArea(1, 20);

        JPanel borderDiff = new JPanel(new BorderLayout());
        borderDiff.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        JLabel jLabelDiff = new JLabel("<html>Easy(1): n,m >= 3<br/>" +
                "Mid(2): n >= 6 or m >= 6<br/>" +
                "Hard(3): n >= 9 or m >= 9</html>");
        JTextArea textDiff = new JTextArea(1, 1);

        borderDiff.add(jLabelDiff, BorderLayout.NORTH);
        JLabel jLabelSetDiff = new JLabel("Enter diff:");
        JPanel aux = new JPanel();
        aux.add(jLabelSetDiff);
        aux.add(textDiff);
        borderDiff.add(aux, BorderLayout.SOUTH);

        JButton generateKakuro = new JButton("Try");

        borderSize.add(jLabel1);
        borderSize.add(textAreaSize);
        borderSize.add(generateKakuro);

        JPanel borderAux = new JPanel(new BorderLayout());
        borderAux.add(borderSize, BorderLayout.NORTH);
        borderAux.add(borderDiff, BorderLayout.SOUTH);


        JPanel border = new JPanel(new BorderLayout());
        border.add(borderAux, BorderLayout.PAGE_START);

        JTextArea textKakuro = new JTextArea(20, 20);
        textKakuro.setEditable(false);
        textKakuro.setLineWrap(true);

        JLabel jLabel2 = new JLabel("Kakuro:");

        JPanel borderKakuro = new JPanel(new BorderLayout());
        borderKakuro.add(jLabel2, BorderLayout.PAGE_START);
        borderKakuro.add(textKakuro, BorderLayout.CENTER);

        generateKakuro.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String kakuroText = null;
                        if(textAreaSize.getText().isBlank()) showErrorDialog("Size empty");
                        else if(textDiff.getText().isBlank()) showErrorDialog("Diff empty");
                        else {
                            try {
                                kakuroText = convertToString(controladorPresentacion.generateKakuro(textAreaSize.getText(), textDiff.getText()));
                            } catch (ExceptionKakuro er) {
                                showDialogError(er.getMessage());
                            }
                        }
                        textKakuro.setText(kakuroText);
                        textKakuro.setVisible(true);
                    }
                }
        );

        JPanel panelBotones = new JPanel(new BorderLayout());

        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controladorPresentacion.startGameGenerate();
                        }
                    }
        );

        JButton goBack = new JButton("Return");
        goBack.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         controladorPresentacion.goBack(0);
                                     }
                                 }

        );

        panelBotones.add(goBack, BorderLayout.WEST);
        panelBotones.add(startGame, BorderLayout.EAST);

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(border, BorderLayout.PAGE_START);
        pane.add(borderKakuro, BorderLayout.CENTER);
        pane.add(panelBotones, BorderLayout.PAGE_END);

        return pane;
    }

    private String convertToString(String[][] matrix) {
        StringBuilder text = new StringBuilder();
        for (String[] strings : matrix) {
            for (int j = 0; j < matrix[0].length; ++j) {
                if (j == 0) text.append(strings[j]);
                else text.append(",").append(strings[j]);
            }
            text.append("\n");
        }
        return text.toString();
    }

    private void showErrorDialog(String error) {
        JOptionPane.showMessageDialog(null,
                error,
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }
}

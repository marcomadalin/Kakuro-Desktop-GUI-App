/**
 * @file ./src/presentacion/vistas/VistaRepo.java
 * @author
 */
package src.presentacion.vistas;


import src.presentacion.controladores.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class VistaRepo
 * @brief Classe que representa la vista per visualitzar a la GUI el repositori.
 */
public class VistaRepo {
    private CtrlPresentacion controladorPresentacion;

    private JFrame frameVista = new JFrame("Repository");
    private JPanel panelContenidos = new JPanel();

    public VistaRepo(CtrlPresentacion pCtrlPresentacion) {
        controladorPresentacion = pCtrlPresentacion;
    }

    public void inicializarComponentes() {
        inicializarFrameVista();
    }

    public void hacerVisible(String[] list) {
        inicializarPanelContenidos(list);
        frameVista.pack();
        frameVista.setLocationRelativeTo(null);
        frameVista.setVisible(true);
    }

    private void inicializarFrameVista() {
        frameVista.setResizable(false);
        // Posicion y operaciones por defecto
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVista.setLocationRelativeTo(null);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);

    }

    public void hacerInvisible() {
        frameVista.setVisible(false);
    }

    private void inicializarPanelContenidos(String[] list) {
        panelContenidos.setLayout(new BorderLayout());

        JPanel savePanel = saveKakuro();
        JPanel deletePanel = deleteKakuro(list);

        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        savePanel.setBorder(padding);
        deletePanel.setBorder(padding);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Save Kakuro", null, savePanel);
        tabbedPane.addTab("Delete Kakuro", null, deletePanel);
        panelContenidos.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel saveKakuro() {
        JLabel jLabel1 = new JLabel("Enter name:");
        JPanel borderName = new JPanel();
        borderName.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        JTextArea textAreaName = new JTextArea(1,20);

        borderName.add(jLabel1);
        borderName.add(textAreaName);

        JLabel jLabel2 = new JLabel("Enter kakuro:");

        JTextArea textAreaKakuro = new JTextArea(20,20);
        textAreaKakuro.setLineWrap(true);

        JPanel borderKakuro = new JPanel(new BorderLayout());
        borderKakuro.add(jLabel2, BorderLayout.PAGE_START);
        borderKakuro.add(textAreaKakuro, BorderLayout.CENTER);

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorPresentacion.saveKakuro(textAreaName.getText(), textAreaKakuro.getText());
                    JOptionPane.showMessageDialog(null, "Kakuro has been saved successfully",
                                "Message", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Kakuro hasn't been saved successfully (file with name "
                            + textAreaName.getText() + " already exists)", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                hacerInvisible();
                panelContenidos.removeAll();
                inicializarFrameVista();
                controladorPresentacion.viewRepo();
            }
        });

        JButton returnbutton = new JButton("Return");
        returnbutton.setActionCommand("return");
        returnbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("return".equals(e.getActionCommand())) {
                    frameVista.setVisible(false);
                    frameVista.dispose();
                    controladorPresentacion.main_menu();
                }
            }
        });

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(borderName, BorderLayout.PAGE_START);
        pane.add(borderKakuro, BorderLayout.CENTER);

        JPanel paneAux = new JPanel(new BorderLayout());
        paneAux.add(save, BorderLayout.LINE_END);
        paneAux.add(returnbutton, BorderLayout.LINE_START);
        pane.add(paneAux, BorderLayout.PAGE_END);

        return pane;
    }

    private JPanel deleteKakuro(String[] listOfFiles) {
        int numButtons = listOfFiles.length;
        JRadioButton[] radioButtons = new JRadioButton[numButtons];
        final ButtonGroup group = new ButtonGroup();
        JButton delete = new JButton("Delete");
        delete.setBackground(Color.decode("0xFF5733"));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = group.getSelection().getActionCommand();
                if(controladorPresentacion.deleteKakuro(Integer.parseInt(command)))
                    JOptionPane.showMessageDialog(null, "Kakuro has been deleted successfully",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(null, "Kakuro hasn't been deleted successfully",
                        "Error", JOptionPane.INFORMATION_MESSAGE);
                hacerInvisible();
                panelContenidos.removeAll();
                inicializarFrameVista();
                controladorPresentacion.viewRepo();
            }
        });

        JButton returnbutton = new JButton("Return");
        returnbutton.setActionCommand("return");
        returnbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("return".equals(e.getActionCommand())) {
                    frameVista.setVisible(false);
                    frameVista.dispose();
                    controladorPresentacion.main_menu();
                }
            }
        });

        for(int i = 0; i < numButtons; ++i) {
            radioButtons[i] = new JRadioButton(listOfFiles[i]);
            radioButtons[i].setActionCommand(String.valueOf(i));
            group.add(radioButtons[i]);
            if ( i == 0) {
                radioButtons[i].setSelected(true);
            }
        }
        return createPaneDelete("Select a Kakuro:", radioButtons, delete, returnbutton);
    }

    private JPanel createPaneDelete(String description, JRadioButton[] radioButtons, JButton delete, JButton returnbutton) {

        int numChoices = radioButtons.length;
        JPanel box = new JPanel();
        JLabel label = new JLabel(description);

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(label);

        for (int i = 0; i < numChoices; i++) {
            box.add(radioButtons[i]);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);

        JPanel paneAux = new JPanel(new BorderLayout());
        paneAux.add(delete, BorderLayout.LINE_END);
        paneAux.add(returnbutton, BorderLayout.LINE_START);
        pane.add(paneAux, BorderLayout.PAGE_END);
        return pane;
    }
}


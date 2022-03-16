/**
 * @file ./src/presentacion/vistas/VistaStatistics.java
 * @author
 */
package src.presentacion.vistas;

import src.presentacion.controladores.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * @class VistaStatistics
 * @brief Classe que representa la vista per visualitzar a la GUI les estadistiques de un usuari.
 */
public class VistaStatistics {
    private CtrlPresentacion controladorPresentacion;

    private  static JFrame frame ;
    private  static JPanel panel ;

    private  static JLabel statiscticslabel1;
    private  static JLabel statiscticslabel2;
    private  static JLabel statiscticslabel3;
    private  static JLabel statiscticslabel4;
    private  static JLabel statiscticslabel5;
    private  static JLabel statiscticslabel6;
    private  static JLabel statiscticslabel7;

    public VistaStatistics(CtrlPresentacion pCtrlPresentacion) {
        controladorPresentacion = pCtrlPresentacion;
    }

    public void hacerVisible(String user) {
        statistics_menu(user);
    }

    public void hacerInvisible() {
        frame.setVisible(false);
    }

    public void inicialitzar_vista(){
        frame = new JFrame("frame VistaUserStatistics");
        frame.setSize(600, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setLayout(null);
    }

    public void statistics_menu(String user) {
        inicialitzar_vista();

        String[] statistics = controladorPresentacion.getUserStatistics(user);

        statiscticslabel1 = new JLabel ("Nº of easy kakuros solved: " + statistics[0]);
        statiscticslabel1.setBounds(10,20,350,25);
        panel.add(statiscticslabel1);

        statiscticslabel2 = new JLabel("Nº of medium kakuros solved: " + statistics[1]);
        statiscticslabel2.setBounds(10,50,350,25);
        panel.add(statiscticslabel2);

        statiscticslabel3 = new JLabel("Nº of hard kakuros solved " + statistics[2]);
        statiscticslabel3.setBounds(10,80,350,25);
        panel.add(statiscticslabel3);

        statiscticslabel4 = new JLabel("Nº of easy kakuros solved without help: " + statistics[3]);
        statiscticslabel4.setBounds(10,110,350,25);
        panel.add(statiscticslabel4);

        statiscticslabel5 = new JLabel("Nº of medium kakuros solved without help: " + statistics[4]);
        statiscticslabel5.setBounds(10,140,350,25);
        panel.add(statiscticslabel5);

        statiscticslabel6 = new JLabel("Nº of hard kakuros solved without help: " + statistics[5]);
        statiscticslabel6.setBounds(10,170,350,25);
        panel.add(statiscticslabel6);

        String string7 = "Average time to solve a kakuro without help: ";
        int hours, minutes, seconds, totalGames;
        totalGames = Integer.parseInt(statistics[3]) + Integer.parseInt(statistics[4]) + Integer.parseInt(statistics[5]);
        if (totalGames != 0) {
            hours = Integer.parseInt(statistics[6]);
            minutes = Integer.parseInt(statistics[7]);
            seconds = Integer.parseInt(statistics[8]);
            seconds = (hours * 3600 + minutes * 60 + seconds) / totalGames;
            if (seconds / 3600 < 10) {
                string7 += ("0" + seconds / 3600 + ":");
            } else {
                string7 += (seconds / 3600 + ":");
            }

            if ((seconds % 3600) / 60 < 10) {
                string7 += ("0" + (seconds % 3600) / 60 + ":");
            } else {
                string7 += ((seconds % 3600) / 60 + ":");
            }

            if ((seconds % 3600) % 60 < 10) {
                string7 += ("0" + (seconds % 3600) % 60);
            } else {
                string7 += ((seconds % 3600) % 60);
            }
        } else string7 += "00:00:00";
        statiscticslabel7 = new JLabel(string7);
        statiscticslabel7.setBounds(10,200,450,25);
        panel.add(statiscticslabel7);

        JButton returnbutton = new JButton("Return");
        returnbutton.setBounds(470,200,100,25);
        returnbutton.setActionCommand("return");
        returnbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("return".equals(e.getActionCommand())) {
                    frame.setVisible(false);
                    frame.dispose();
                    controladorPresentacion.main_menu();
                }
            }
        });
        panel.add(returnbutton);

        frame.add(panel);
        frame.setTitle(user + "'s statistics");
        frame.setVisible(true);
    }
}

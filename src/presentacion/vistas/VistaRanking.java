/**
 * @file ./src/presentacion/vistas/VistaRanking.java
 * @author
 */
package src.presentacion.vistas;

import src.presentacion.controladores.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * @class VistaRanking
 * @brief Classe que representa la vista per visualitzar a la GUI els rankings.
 */
public class VistaRanking {
    private CtrlPresentacion controladorPresentacion;

    private  static JFrame frame ;
    private  static JPanel panel ;

    private  static JLabel rankinglabel1;
    private  static JLabel rankinglabel2;
    private  static JLabel rankinglabel3;
    private  static JLabel rankinglabel4;
    private  static JLabel rankinglabel5;
    private  static JLabel rankinglabel6;
    private  static JLabel rankinglabel7;
    private  static JLabel rankinglabel8;
    private  static JLabel rankinglabel9;
    private  static JLabel rankinglabel10;
    private  static JLabel rankinglabel11;

    private  static JLabel rankinglabel21;
    private  static JLabel rankinglabel22;
    private  static JLabel rankinglabel23;
    private  static JLabel rankinglabel24;
    private  static JLabel rankinglabel25;
    private  static JLabel rankinglabel26;
    private  static JLabel rankinglabel27;
    private  static JLabel rankinglabel28;
    private  static JLabel rankinglabel29;
    private  static JLabel rankinglabel30;
    private  static JLabel rankinglabel31;

    private  static JLabel rankinglabel41;
    private  static JLabel rankinglabel42;
    private  static JLabel rankinglabel43;
    private  static JLabel rankinglabel44;
    private  static JLabel rankinglabel45;
    private  static JLabel rankinglabel46;
    private  static JLabel rankinglabel47;
    private  static JLabel rankinglabel48;
    private  static JLabel rankinglabel49;
    private  static JLabel rankinglabel50;
    private  static JLabel rankinglabel51;

    public VistaRanking(CtrlPresentacion pCtrlPresentacion) {
        controladorPresentacion = pCtrlPresentacion;
    }

    public void hacerVisible() {
        ranking_menu();
    }

    public void hacerInvisible() {
        frame.setVisible(false);
    }

    public void inicialitzar_vista(){
        frame = new JFrame("frame VistaRanking");
        frame.setSize(770, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setLayout(null);
    }

    private void showDialogError(String error) {
        JOptionPane.showMessageDialog(null,
                error,
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void ranking_menu() {
        inicialitzar_vista();
        ArrayList<ArrayList<String>> Ranking = null;
        try {
            Ranking = controladorPresentacion.getRankingPerDiff(1);
        } catch (Exception e) {
            showDialogError(e.getMessage());
        }

        rankinglabel1 = new JLabel ("Top 10 easy kakuros: ");
        rankinglabel1.setBounds(10,20,250,25);
        rankinglabel1.setFont(new Font("Sans Serif", Font.BOLD, 15));
        panel.add(rankinglabel1);

        String[] s = new String[10];
        s = printRank(Ranking, s);
        rankinglabel2 = new JLabel (s[0]);
        rankinglabel2.setBounds(10,50,250,25);
        rankinglabel2.setForeground(Color.decode("0xB30000"));
        panel.add(rankinglabel2);

        rankinglabel3 = new JLabel (s[1]);
        rankinglabel3.setBounds(10,80,250,25);
        rankinglabel3.setForeground(Color.decode("0xA99000"));
        panel.add(rankinglabel3);

        rankinglabel4 = new JLabel (s[2]);
        rankinglabel4.setBounds(10,110,250,25);
        rankinglabel4.setForeground(Color.decode("0x7200D0"));
        panel.add(rankinglabel4);

        rankinglabel5 = new JLabel (s[3]);
        rankinglabel5.setBounds(10,140,250,25);
        panel.add(rankinglabel5);

        rankinglabel6 = new JLabel (s[4]);
        rankinglabel6.setBounds(10,170,250,25);
        panel.add(rankinglabel6);

        rankinglabel7 = new JLabel (s[5]);
        rankinglabel7.setBounds(10,200,250,25);
        panel.add(rankinglabel7);

        rankinglabel8 = new JLabel (s[6]);
        rankinglabel8.setBounds(10,230,250,25);
        panel.add(rankinglabel8);

        rankinglabel9 = new JLabel (s[7]);
        rankinglabel9.setBounds(10,260,250,25);
        panel.add(rankinglabel9);

        rankinglabel10 = new JLabel (s[8]);
        rankinglabel10.setBounds(10,290,250,25);
        panel.add(rankinglabel10);

        rankinglabel11 = new JLabel (s[9]);
        rankinglabel11.setBounds(10,320,250,25);
        panel.add(rankinglabel11);

        try {
            Ranking = controladorPresentacion.getRankingPerDiff(2);
        } catch (Exception e) {
            showDialogError(e.getMessage());
        }
        rankinglabel21 = new JLabel ("Top 10 medium kakuros: ");
        rankinglabel21.setBounds(260,20,250,25);
        rankinglabel21.setFont(new Font("Sans Serif", Font.BOLD, 15));
        panel.add(rankinglabel21);

        s = new String[10];
        s = printRank(Ranking, s);
        rankinglabel22 = new JLabel (s[0]);
        rankinglabel22.setBounds(260,50,250,25);
        rankinglabel22.setForeground(Color.decode("0xB30000"));
        panel.add(rankinglabel22);

        rankinglabel23 = new JLabel (s[1]);
        rankinglabel23.setBounds(260,80,250,25);
        rankinglabel23.setForeground(Color.decode("0xA99000"));
        panel.add(rankinglabel23);

        rankinglabel24 = new JLabel (s[2]);
        rankinglabel24.setBounds(260,110,250,25);
        rankinglabel24.setForeground(Color.decode("0x7200D0"));
        panel.add(rankinglabel24);

        rankinglabel25 = new JLabel (s[3]);
        rankinglabel25.setBounds(260,140,250,25);
        panel.add(rankinglabel25);

        rankinglabel26 = new JLabel (s[4]);
        rankinglabel26.setBounds(260,170,250,25);
        panel.add(rankinglabel26);

        rankinglabel27 = new JLabel (s[5]);
        rankinglabel27.setBounds(260,200,250,25);
        panel.add(rankinglabel27);

        rankinglabel28 = new JLabel (s[6]);
        rankinglabel28.setBounds(260,230,250,25);
        panel.add(rankinglabel28);

        rankinglabel29 = new JLabel (s[7]);
        rankinglabel29.setBounds(260,260,250,25);
        panel.add(rankinglabel29);

        rankinglabel30 = new JLabel (s[8]);
        rankinglabel30.setBounds(260,290,250,25);
        panel.add(rankinglabel30);

        rankinglabel31 = new JLabel (s[9]);
        rankinglabel31.setBounds(260,320,250,25);
        panel.add(rankinglabel31);

        try {
            Ranking = controladorPresentacion.getRankingPerDiff(3);
        } catch (Exception e) {
            showDialogError(e.getMessage());
        }
        rankinglabel41 = new JLabel ("Top 10 hard kakuros: ");
        rankinglabel41.setBounds(510,20,250,25);
        rankinglabel41.setFont(new Font("Sans Serif", Font.BOLD, 15));
        panel.add(rankinglabel41);

        s = new String[10];
        s = printRank(Ranking, s);
        rankinglabel42 = new JLabel (s[0]);
        rankinglabel42.setBounds(510,50,250,25);
        rankinglabel42.setForeground(Color.decode("0xB30000"));
        panel.add(rankinglabel42);

        rankinglabel43 = new JLabel (s[1]);
        rankinglabel43.setBounds(510,80,250,25);
        rankinglabel43.setForeground(Color.decode("0xA99000"));
        panel.add(rankinglabel43);

        rankinglabel44 = new JLabel (s[2]);
        rankinglabel44.setBounds(510,110,250,25);
        rankinglabel44.setForeground(Color.decode("0x7200D0"));
        panel.add(rankinglabel44);

        rankinglabel45 = new JLabel (s[3]);
        rankinglabel45.setBounds(510,140,250,25);
        panel.add(rankinglabel45);

        rankinglabel46 = new JLabel (s[4]);
        rankinglabel46.setBounds(510,170,250,25);
        panel.add(rankinglabel46);

        rankinglabel47 = new JLabel (s[5]);
        rankinglabel47.setBounds(510,200,250,25);
        panel.add(rankinglabel47);

        rankinglabel48 = new JLabel (s[6]);
        rankinglabel48.setBounds(510,230,250,25);
        panel.add(rankinglabel48);

        rankinglabel49 = new JLabel (s[7]);
        rankinglabel49.setBounds(510,260,250,25);
        panel.add(rankinglabel49);

        rankinglabel50 = new JLabel (s[8]);
        rankinglabel50.setBounds(510,290,250,25);
        panel.add(rankinglabel50);

        rankinglabel51 = new JLabel (s[9]);
        rankinglabel51.setBounds(510,320,250,25);
        panel.add(rankinglabel51);

        JButton returnbutton = new JButton("Return");
        returnbutton.setBounds(640,350,100,25);
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
        frame.setTitle("Ranking");
        frame.setVisible(true);
    }

    private String[] printRank(ArrayList<ArrayList<String>> ranking, String[] s) {
        for (int i = 0; i < ranking.size(); ++i) {
            for (int j = 0; j < ranking.get(i).size(); ++j) {
                if (j == 0) s[i] = (i+1 + ". " + ranking.get(i).get(j) + ": ");
                else if (j == 1 || j == 2) {
                    if (Integer.parseInt(ranking.get(i).get(j)) < 10) {
                        s[i] += ("0" + ranking.get(i).get(j) + ":");
                    } else {
                        s[i] += (ranking.get(i).get(j) + ":");
                    }
                } else {
                    if (Integer.parseInt(ranking.get(i).get(j)) < 10) {
                        s[i] += ("0" + ranking.get(i).get(j));
                    } else {
                        s[i] += (ranking.get(i).get(j));
                    }
                }
            }
        }
        return s;
    }
}

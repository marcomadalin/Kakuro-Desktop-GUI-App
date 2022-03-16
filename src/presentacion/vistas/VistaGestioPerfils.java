/**
 * @file ./src/presentacion/vistas/VistaGestioPerfils.java
 * @author Gerard Caravaca
 */
package src.presentacion.vistas;

import src.presentacion.controladores.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class VistaGestioPerfils
 * @brief Classe que representa la vista per gestionar perfils d'usuari.
 */
public class VistaGestioPerfils implements ActionListener {

    private  CtrlPresentacion cp;
    private  static JFrame frame ;
    private static JFrame Reafirm;
    private  static JPanel panel ;
    private  static JLabel userlabel;
    private  static JTextField userText;
    private  static JLabel passwordlabel;
    private  static JLabel passwordconf;
    private  static JPasswordField passwordText;
    private  static JPasswordField passwordTextconf;
    private  static JLabel success;

    public VistaGestioPerfils(CtrlPresentacion pCtrlPresentacion) {
        cp = pCtrlPresentacion;
    }

    public void inicilitzar_vista(){
        frame = new JFrame("frame VistaGestioPerfils");
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setLayout(null);
    }
    public void start_menu() {

        inicilitzar_vista();
        JButton delete = new JButton("Delete Profile");
        delete.setBounds(150,40,200,30);
        delete.addActionListener(new VistaGestioPerfils(cp));
        delete.setActionCommand("delete_profile");
        panel.add(delete);

        JButton register = new JButton("New Profile");
        register.setBounds(150, 70, 200,30);
        register.addActionListener(new VistaGestioPerfils(cp));
        register.setActionCommand("register");
        panel.add(register);

        JButton start = new JButton("Log in");
        start.addActionListener(new VistaGestioPerfils(cp));
        start.setActionCommand("log in");
        start.setBounds(150, 100, 200,30);

        JButton exit = new JButton("Exit");
        exit.addActionListener(new VistaGestioPerfils(cp));
        exit.setActionCommand("end");
        exit.setBounds(400, 130, 80,25);
        panel.add(exit);


        panel.add(start);
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setTitle("Starting window");

        frame.setVisible(true);
    }

    public void login_menu() {
        inicilitzar_vista();
        frame.setSize(520,200);

        userlabel = new JLabel ("User:");
        userlabel.setBounds(10,20,80,25);
        panel.add(userlabel);

        passwordlabel = new JLabel("Password:");
        passwordlabel.setBounds(10,50,80,25);
        panel.add(passwordlabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passwordText = new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);
        JButton button;
        button = new JButton("Login");
        button.setBounds(10,80,80,25);
        button.setActionCommand("login");
        button.addActionListener(new VistaGestioPerfils(cp));
        panel.add(button);

        JButton returnstart = new JButton("Return");
        returnstart.setActionCommand("return_start");
        returnstart.addActionListener(new VistaGestioPerfils(cp));
        returnstart.setBounds(400,130, 100,25);
        panel.add(returnstart);

        success = new JLabel("");
        success.setBounds(10,110,300,25);
        panel.add(success);

        frame.add(panel);
        frame.getRootPane().setDefaultButton(button);
        frame.setTitle("Login Menu");
        frame.setVisible(true);
    }

    public void register_menu() {
        inicilitzar_vista();

        userlabel = new JLabel ("Username:");
        userlabel.setBounds(10,20,80,25);
        panel.add(userlabel);

        passwordlabel = new JLabel("Password:");
        passwordlabel.setBounds(10,50,80,25);
        panel.add(passwordlabel);
        passwordconf = new JLabel("Confirmation:");
        passwordconf.setBounds(10,80,80,25);
        panel.add(passwordconf);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passwordText = new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        passwordTextconf = new JPasswordField();
        passwordTextconf.setBounds(100,80,165,25);
        panel.add(passwordTextconf);

        JButton button;
        button = new JButton("Sign in");
        button.setBounds(10,110,80,25);
        button.setActionCommand("signin");
        button.addActionListener(new VistaGestioPerfils(cp));
        panel.add(button);

        JButton returnstart = new JButton("Return");
        returnstart.setActionCommand("return_start");
        returnstart.addActionListener(new VistaGestioPerfils(cp));
        returnstart.setBounds(400,130, 80,25);
        panel.add(returnstart);

        success = new JLabel("");
        success.setBounds(10,140,300,25);
        panel.add(success);
        frame.getRootPane().setDefaultButton(button);
        frame.add(panel);
        frame.setTitle("Sign in Menu");
        frame.setTitle("Sign in Menu");
        frame.setVisible(true);
    }

    public void delete_menu() {
        inicilitzar_vista();

        userlabel = new JLabel ("User:");
        userlabel.setBounds(10,20,80,25);
        panel.add(userlabel);

        passwordlabel = new JLabel("Password:");
        passwordlabel.setBounds(10,50,80,25);
        panel.add(passwordlabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passwordText = new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);
        JButton button;
        button = new JButton("Delete");
        button.setBounds(10,80,80,25);
        button.setBackground(Color.decode("0xFF5733"));
        button.setActionCommand("delete");
        button.addActionListener(new VistaGestioPerfils(cp));
        panel.add(button);


        success = new JLabel("");
        success.setBounds(10,110,300,25);
        panel.add(success);

        JButton returnstart = new JButton("Return");
        returnstart.setActionCommand("return_start");
        returnstart.addActionListener(new VistaGestioPerfils(cp));
        returnstart.setBounds(400,130, 80,25);
        panel.add(returnstart);
        frame.getRootPane().setDefaultButton(button);
        frame.add(panel);
        frame.setTitle("Delete Menu");
        frame.setVisible(true);

    }

    public void main_menu() {
        inicilitzar_vista();
        frame.setSize(520,220);
        JButton gamebutton = new JButton("New Game");
        JButton repobutton = new JButton("Repository");
        JButton statsbutton = new JButton("User stats");
        JButton rankbutton = new JButton("Ranking");
        JButton exitbutton = new JButton("Logout");

        gamebutton.setBounds(175,20,150,25);
        repobutton.setBounds(175,50,150,25);
        statsbutton.setBounds(175,80,150,25);
        rankbutton.setBounds(175,110,150,25);
        exitbutton.setBounds(400,150,100,25);

        exitbutton.setActionCommand("exit");
        exitbutton.addActionListener(new VistaGestioPerfils(cp));

        gamebutton.setActionCommand("newgame");
        gamebutton.addActionListener(new VistaGestioPerfils(cp));

        repobutton.setActionCommand("repo");
        repobutton.addActionListener(new VistaGestioPerfils(cp));

        statsbutton.setActionCommand("stats");
        statsbutton.addActionListener(new VistaGestioPerfils(cp));

        rankbutton.setActionCommand("rank");
        rankbutton.addActionListener(new VistaGestioPerfils(cp));

        panel.add(gamebutton);
        panel.add(repobutton);
        panel.add(exitbutton);
        panel.add(rankbutton);
        panel.add(statsbutton);

        frame.add(panel);
        frame.setTitle("Main Menu");
        frame.setVisible(true);
    }

    private void showMessageReaffirmation(int i) {
        Reafirm = new JFrame("Confirm Action");
        Reafirm.setSize(300, 150);
        Reafirm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Reafirm.setResizable(false);
        Reafirm.setLocationRelativeTo(null);
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        JLabel sure = new JLabel("Are you sure?");
        sure.setBounds(20,20,150,30);
        JButton yes = new JButton("Accept");
        JButton no = new JButton("Cancel");
        yes.setBounds(20,75,100,25);
        no.setBounds(180,75,100,25);
        if (i == 0) {
            yes.setActionCommand("yesExit");
            yes.addActionListener(new VistaGestioPerfils(cp));
            no.setActionCommand("noExit");
            no.addActionListener(new VistaGestioPerfils(cp));
        }else{
            yes.setActionCommand("yesDelete");
            yes.addActionListener(new VistaGestioPerfils(cp));
            no.setActionCommand("noDelete");
            no.addActionListener(new VistaGestioPerfils(cp));
        }
        panel2.add(yes);
        panel2.add(no);
        panel2.add(sure);
        Reafirm.add(panel2);
        Reafirm.setVisible(true);
    }

    private void showDialogError(String error) {
        JOptionPane.showMessageDialog(null,
                error,
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if("login".equals(e.getActionCommand())) {
            String user = userText.getText();
            String pass = passwordText.getText();
            if (cp.existsuser(user)) {
                if (cp.correctpassword(user, pass)) {
                    success.setText("Login successful");
                    success.setForeground(Color.GREEN);
                    cp.setUser(user);
                    frame.setVisible(false);
                    frame.dispose();
                    main_menu();
                }
                else {
                    success.setText("Incorrect password");
                    success.setForeground(Color.RED);
                }
            } else {
                success.setText("Incorrect username");
                success.setForeground(Color.RED);
            }
        }
        else if ("exit".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            start_menu();
        }
        else if ("log in".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            login_menu();

        }
        else if ("register".equals(e.getActionCommand())) {
            frame.setVisible(false);
            register_menu();
        }
        else if ("signin".equals(e.getActionCommand())) {
            String conf = passwordTextconf.getText();
            String user = userText.getText();
            String pass = passwordText.getText();
            if (!pass.equals(conf)){
                success.setText("Passwords do not match");
                success.setForeground(Color.RED);
            }
            else if (pass.length() < 8) {
                success.setText("Password must have a minimum of 8 digits");
                success.setForeground(Color.RED);
            }
            else {
                try {
                    cp.saveprofile(user,pass);
                } catch (Exception exception) {
                    showDialogError(exception.getMessage());
                }
                success.setText("Signin successful");
                success.setForeground(Color.GREEN);
                frame.setVisible(false);
                frame.dispose();
                start_menu();
            }
        }
        else if ("delete_profile".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            delete_menu();
        }
        else if ("delete".equals(e.getActionCommand())) {
            String user = userText.getText();
            String pass = passwordText.getText();
            if (cp.existsuser(user)) {
                if (cp.correctpassword(user,pass)) {
                    showMessageReaffirmation(1);
                }
                else {
                    success.setText("Incorrect password");
                    success.setForeground(Color.RED);
                }
            } else {
                success.setText("Username does not exists");
                success.setForeground(Color.RED);
            }

        }
        else if ("yesDelete".equals(e.getActionCommand())){
            String user = userText.getText();
            String pass = passwordText.getText();
            try {
                cp.deleteprofile(user,pass);
                frame.setVisible(false);
                frame.dispose();
                Reafirm.setVisible(false);
                Reafirm.dispose();
                start_menu();
            } catch (Exception exception) {
                showDialogError(exception.getMessage());
            }

        }
        else if ("noDelete".equals(e.getActionCommand())){
            Reafirm.setVisible(false);
            Reafirm.dispose();
        }
        else if ("newgame".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            cp.new_game();
        }
        else if ("gomainmenu".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            main_menu();
        }
        else if ("repo".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            cp.viewRepo();
        }
        else if ("stats".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            cp.viewUserStatistics();
        }
        else if ("rank".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            cp.viewRanking();
        }
        else if ("end".equals(e.getActionCommand())) {
           showMessageReaffirmation(0);
        }
        else if("yesExit".equals(e.getActionCommand())){
            frame.setVisible(false);
            frame.dispose();
            Reafirm.setVisible(false);
            Reafirm.dispose();
            System.exit(0);
        }
        else if("noExit".equals(e.getActionCommand())){
            Reafirm.setVisible(false);
            Reafirm.dispose();
        }
        else if ("return_start".equals(e.getActionCommand())) {
            frame.setVisible(false);
            frame.dispose();
            start_menu();
        }


    }
}

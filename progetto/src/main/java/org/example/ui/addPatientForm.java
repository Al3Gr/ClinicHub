package org.example.ui;

import com.github.lgooddatepicker.components.DatePicker;
import org.example.domain.ClinicHub;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class addPatientForm extends JFrame{

    ClinicHub clinicHub;
    private JPanel addPatientPanel;
    private JTextField nameField;
    private JTextField lastnameField;
    private JPanel datePanel;
    private JButton cnlCancel;
    private JTextField residenceField;
    private JTextField cfField;
    private JTextField telField;
    private JTextField emailField;
    private JButton btnConfirm;

    private DatePicker datePicker = new DatePicker();


    public addPatientForm(JFrame parent){
        super();
        clinicHub = ClinicHub.getInstance();
        setTitle("ClinicHub");
        setContentPane(addPatientPanel);
        setMinimumSize(new Dimension(510, 720));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        datePanel.setLayout(new FlowLayout());
        datePanel.add(datePicker);
        cnlCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(UtilityUI.validateData(nameField.getText(), lastnameField.getText(),residenceField.getText(), cfField.getText(), telField.getText(), emailField.getText())) {
                    try {
                        clinicHub.addPatient(nameField.getText(), lastnameField.getText(), datePicker.getDate(), residenceField.getText(), cfField.getText(), telField.getText(), emailField.getText());
                        confirmFrame();
                    } catch (Exception ex) {
                        alertFrame("Paziente già presente");
                    }
                } else {
                    alertFrame("Dati non validi");
                }

            }
        });
    }

    private void alertFrame(String message) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(400, 200));
        frame.setTitle("Alert");

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        JPanel panel = new JPanel();
        panel.setBorder(padding);
        panel.setLayout(new GridLayout(2, 1));

        frame.setContentPane(panel);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));

        JButton btn = new JButton("OK");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.getContentPane().add(label);
        frame.getContentPane().add(btn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void confirmFrame(){
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(400, 200));
        frame.setTitle("Conferma");

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(padding);

        JLabel label = new JLabel("Confermi registrazione?", SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 18));

        panel.add(label);

        JPanel buttonPanel = new JPanel(new GridLayout());

        JButton confirmBtn = new JButton("Si");
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clinicHub.confirmPatient();
                    frame.dispose();
                    dispose();

                } catch (Exception ex) {
                    alertFrame("Errore precedura");
                }
            }
        });

        JButton cancelBtn = new JButton("No");
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(confirmBtn);

        panel.add(buttonPanel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}

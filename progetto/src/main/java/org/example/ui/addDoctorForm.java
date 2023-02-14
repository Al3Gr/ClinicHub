package org.example.ui;

import com.github.lgooddatepicker.components.DatePicker;
import org.example.domain.ClinicHub;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addDoctorForm extends JFrame{

    ClinicHub clinicHub;

    private DatePicker datePicker = new DatePicker();
    private JPanel addDoctorPanel;
    private JTextField nameField;
    private JTextField lastnameField;
    private JPanel datePanel;
    private JTextField cfField;
    private JTextField telField;
    private JTextField emailField;
    private JButton confirmButton;
    private JButton backButton;

    public addDoctorForm(JFrame parent){
        super();
        clinicHub = ClinicHub.getInstance();
        setTitle("ClinicHub");
        setContentPane(addDoctorPanel);
        setMinimumSize(new Dimension(510, 720));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        datePanel.setLayout(new FlowLayout());
        datePanel.add(datePicker);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(UtilityUI.validateData(nameField.getText(), lastnameField.getText(), cfField.getText(), telField.getText(), emailField.getText())) {
                    try {
                        clinicHub.addDoctor(nameField.getText(), lastnameField.getText(), datePicker.getDate(),  cfField.getText(), telField.getText(), emailField.getText());
                        confirmFrame();
                    } catch (Exception ex) {
                        UtilityUI.alertFrame("Paziente già presente");
                    }
                } else {
                    UtilityUI.alertFrame("Dati non validi");
                }
            }
        });

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
                    clinicHub.confirmDoctor();
                    frame.dispose();
                    dispose();

                } catch (Exception ex) {
                    UtilityUI.alertFrame("Errore precedura");
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

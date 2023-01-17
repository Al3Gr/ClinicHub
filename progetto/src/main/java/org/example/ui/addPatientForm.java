package org.example.ui;

import com.github.lgooddatepicker.components.DatePicker;
import org.example.domain.ClinicHub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class addPatientForm extends JFrame{

    ClinicHub clinicHub;
    private JPanel addPatientPanel;
    private JTextField nameField;
    private JTextField lastnameField;
    private JPanel datePanel;
    private JButton clrCancel;
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
        clrCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



}

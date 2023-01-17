package org.example.ui;

import org.example.domain.ClinicHub;

import javax.swing.*;
import java.awt.*;
import javafx.scene.control.DatePicker;

public class addPatientForm extends JFrame{

    ClinicHub clinicHub;
    private JPanel addPatientPanel;
    private JTextField nameField;
    private JTextField lastnameField;
    private JTextField textField1;



    public addPatientForm(JFrame parent){
        super();
        clinicHub = ClinicHub.getInstance();
        setTitle("ClinicHub");
        setContentPane(addPatientPanel);
        setMinimumSize(new Dimension(510, 720));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        new DatePicker();
    }


}

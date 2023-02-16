package org.example.ui;

import org.example.domain.ClinicHub;
import org.example.domain.Doctor;

import javax.swing.*;
import java.awt.*;

public class showDoctorForm extends JFrame{
    private JPanel mainPanel;
    private ClinicHub clinicHub;

    public showDoctorForm(JFrame parent) {
        super();
        clinicHub = ClinicHub.getInstance();
        setTitle("ClinicHub");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(800, 720));

        mainPanel.setLayout(new FlowLayout());
        JList list = new JList(clinicHub.printMedicalStaff().toArray());
        mainPanel.add(list);

        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }
}

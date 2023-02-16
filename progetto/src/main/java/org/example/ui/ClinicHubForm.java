package org.example.ui;

import org.example.domain.ClinicHub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClinicHubForm extends JFrame {

    ClinicHub clinicHub;
    private JPanel welcomePanel;
    private JButton btnAddPatient;
    private JButton btnLoginPatient;
    private JButton addDocButton;
    private JButton resultBtn;
    private JButton showPatBtn;
    private JButton showDocBtn;
    private JButton showHospBtn;
    private JButton showExamBtn;

    public ClinicHubForm(JFrame parent){
        super();
        clinicHub = ClinicHub.getInstance();
        setTitle("ClinicHub");
        setContentPane(welcomePanel);
        setMinimumSize(new Dimension(510, 720));

        btnAddPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addPatientForm(parent);
            }
        });
        btnLoginPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PatientOperationForm(parent);
            }
        });

        addDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addDoctorForm(parent);
            }
        });

        resultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorOperationForm(parent);
            }
        });

        showPatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new showPatientForm(parent);
            }
        });

        showDocBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new showDoctorForm(parent);
            }
        });

        showHospBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new showHospForm(parent);
            }
        });

        showExamBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new showExamForm(parent);
            }
        });


        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        ClinicHubForm c = new ClinicHubForm(null);
    }
}

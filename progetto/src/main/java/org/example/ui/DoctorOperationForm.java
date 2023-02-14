package org.example.ui;

import org.example.domain.ClinicHub;
import org.example.domain.Exam;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DoctorOperationForm extends JFrame{

    ClinicHub clinicHub;

    private JPanel mainPanel;
    private JTextField cfField;
    private JButton loginButton;
    private JPanel listPanel;
    private JButton backBtn;
    private JButton resultBtn;
    private JTextField infoField;
    private JLabel infoLabel;
    private List<Exam> examList = new ArrayList<>();

    private JList lista;

    public DoctorOperationForm(JFrame parent) {
        super();
        clinicHub = ClinicHub.getInstance();
        setTitle("ClinicHub");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(510, 720));

        listPanel.setLayout(new FlowLayout());
        lista = new JList();
        infoLabel.setVisible(false);
        infoField.setVisible(false);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(UtilityUI.validateData(cfField.getText())){
                    try {
                        examList = clinicHub.loginMed(cfField.getText());
                        //System.out.println(examList);
                        mainPanel.revalidate();
                        lista.setListData(examList.toArray());
                        //JList lista = new JList(examList.toArray());
                        listPanel.add(lista);
                        infoLabel.setVisible(true);
                        infoField.setVisible(true);
                    } catch (Exception ex) {
                        UtilityUI.alertFrame("Codice fiscale non valido");
                    }
                } else {
                    UtilityUI.alertFrame("Inserire codice fiscale");
                }
            }
        });



        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        resultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lista.getSelectedValue() != null){
                    Exam ex = (Exam) lista.getSelectedValue();
                    try {
                        clinicHub.selectExamReady(ex.getCode(), infoField.getText());
                        clinicHub.sendResultForEmail();
                        examList.remove(ex);
                        infoField.setText("");
                        lista.setListData(examList.toArray());
                        mainPanel.revalidate();
                    } catch (Exception exc) {
                        throw new RuntimeException(exc);
                    }

                } else {
                    UtilityUI.alertFrame("Seleziona qualcosa");
                }
            }
        });

        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }



}

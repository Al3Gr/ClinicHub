package org.example.ui;

import org.example.domain.ClinicHub;
import org.example.domain.Operation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class loginPatientForm extends JFrame{
    private JComboBox operationCombo;
    private JPanel mainPanel;
    private JTextField cfField;
    private JButton loginBtn;
    private JLabel nameLabel;
    private JLabel lastnameLabel;
    private JLabel birthdateLabel;
    private JLabel residenceLabel;
    private JLabel telLabel;
    private JLabel emailLabel;

    private JComboBox OperationCombo;
    private JRadioButton dailyButton;
    private JRadioButton standardButton;
    private JButton hospButton;

    private ButtonGroup buttonGroup = new ButtonGroup();

    private ClinicHub clinicHub;

    public loginPatientForm(JFrame parent) {
        super();
        clinicHub = ClinicHub.getInstance();
        setTitle("ClinicHub");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(550, 500));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buttonGroup.add(dailyButton);
        buttonGroup.add(standardButton);
        dailyButton.setSelected(true);

        operationCombo.addItem(Operation.VASECTOMY);
        operationCombo.addItem(Operation.MASTECTOMY);
        operationCombo.addItem(Operation.APPENDECTOMY);
        operationCombo.addItem(Operation.CHOLECYSTECTOMY);
        operationCombo.addItem(Operation.HEMORRHOIDECTOMY);
        operationCombo.addItem(Operation.HYSTERECTOMY);
        operationCombo.addItem(Operation.HYSTEROSCOPY);
        operationCombo.addItem(Operation.PERICARDIECTOMY);
        operationCombo.addItem(Operation.PNEUMONECTOMY);
        operationCombo.addItem(Operation.TONSILLECTOMY);

        setValueVisible(false);


        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean check = clinicHub.loginPatient(cfField.getText());
                    if(check) {
                        nameLabel.setText(clinicHub.getCurrentPatient().getName());
                        lastnameLabel.setText(clinicHub.getCurrentPatient().getLastname());
                        birthdateLabel.setText(clinicHub.getCurrentPatient().getBirthday().toString());
                        residenceLabel.setText(clinicHub.getCurrentPatient().getResidence());
                        telLabel.setText(clinicHub.getCurrentPatient().getTelephone());
                        emailLabel.setText(clinicHub.getCurrentPatient().getE_mail());
                        setValueVisible(true);
                    }
                } catch (Exception ex) {
                    Utility.alertFrame("Paziente non trovato");
                }
            }
        });


        hospButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Date> dates = null;
                    if(dailyButton.isSelected()) {
                        dates = (ArrayList<Date>) clinicHub.newHospitalization("DAILY", (Operation) operationCombo.getSelectedItem());
                    } else if(standardButton.isSelected()) {
                        dates = (ArrayList<Date>) clinicHub.newHospitalization("STANDARD", (Operation) operationCombo.getSelectedItem());
                    }
                    confirmHospFrame(dates);
                } catch (Exception ex) {
                    Utility.alertFrame("Inserire dati!!!");
                }
            }
        });
        setVisible(true);
    }

    private void setValueVisible(boolean value) {
        nameLabel.setVisible(value);
        lastnameLabel.setVisible(value);
        birthdateLabel.setVisible(value);
        residenceLabel.setVisible(value);
        telLabel.setVisible(value);
        emailLabel.setVisible(value);
    }

    private void confirmHospFrame(List<Date> dates){
        DateFormat formatDate = new SimpleDateFormat("dd MMMM YYYY");

        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(500, 200));
        frame.setTitle("Conferma");

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        JPanel m_panel = new JPanel(new GridLayout(2, 1));
        m_panel.setBorder(padding);

        JPanel dataPanel = new JPanel(new GridLayout(3, 2));
        JComboBox datesCombo = new JComboBox<>(dates.toArray());

        JLabel startlabel = new JLabel("Scegli data inizio ricovero: ", SwingConstants.LEFT);
        startlabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        JLabel endlabel = new JLabel("Data fine ricovero: ", SwingConstants.LEFT);
        endlabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        JLabel endlabel_info = new JLabel("", SwingConstants.LEFT);
        endlabel_info.setVisible(false);

        JLabel pricelabel = new JLabel("Prezzo ricovero: ", SwingConstants.LEFT);
        pricelabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        JLabel pricelabel_info = new JLabel("", SwingConstants.LEFT);
        pricelabel_info.setVisible(false);


        datesCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime((Date) datesCombo.getSelectedItem());
                    clinicHub.chooseHospitalization(cal);
                    endlabel_info.setText(formatDate.format(clinicHub.getCurrentHosp().getEnd_date().getTime()));
                    pricelabel_info.setText(String.valueOf(clinicHub.showPrice()));
                    pricelabel_info.setVisible(true);
                    endlabel_info.setVisible(true);
                } catch (Exception ex) {
                    Utility.alertFrame("Errore sconosciuto");
                }
            }
        });

        dataPanel.add(startlabel);
        dataPanel.add(datesCombo);
        dataPanel.add(endlabel);
        dataPanel.add(endlabel_info);
        dataPanel.add(pricelabel);
        dataPanel.add(pricelabel_info);

        m_panel.add(dataPanel);

        JButton confirmButton = new JButton("Conferma");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clinicHub.confirmHospitalization();
                    frame.dispose();
                } catch (Exception ex) {
                    Utility.alertFrame("Errore sconosciuto");
                }
            }
        });

        JButton annullaButton = new JButton("Annulla");
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        buttonPanel.add(annullaButton);
        buttonPanel.add(confirmButton);

        m_panel.add(buttonPanel);

        frame.add(m_panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

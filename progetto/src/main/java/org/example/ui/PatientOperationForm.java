package org.example.ui;

import org.example.domain.ClinicHub;
import org.example.domain.ExamType;
import org.example.domain.Operation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatientOperationForm extends JFrame{
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
    private JComboBox examTypeCombo;
    private JButton prenotaEsameButton;

    private ButtonGroup buttonGroup = new ButtonGroup();

    private ClinicHub clinicHub;

    public PatientOperationForm(JFrame parent) {
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

        examTypeCombo.addItem(ExamType.BLOOD_ANALYSIS);
        examTypeCombo.addItem(ExamType.ECHOCARDIOGRAPHY);
        examTypeCombo.addItem(ExamType.MAMMOGRAPHY);
        examTypeCombo.addItem(ExamType.ELECTROENCEPHALOGRAPHY);
        examTypeCombo.addItem(ExamType.ELECTROMYOGRAPHY);

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
                    UtilityUI.alertFrame("Paziente non trovato");
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
                    UtilityUI.alertFrame("Inserire dati!!!");
                }
            }
        });

        prenotaEsameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Date> dates = (ArrayList<Date>) clinicHub.newExamBooking((ExamType) examTypeCombo.getSelectedItem());
                    confirmExamFrame(dates);
                } catch (Exception ex) {
                    System.out.println(ex);
                    UtilityUI.alertFrame("Inserire dati!!!");
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

        JButton confirmButton = new JButton("Conferma");
        confirmButton.setVisible(false);


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
                    confirmButton.setVisible(true);
                } catch (Exception ex) {
                    UtilityUI.alertFrame("Errore sconosciuto");
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


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clinicHub.confirmHospitalization();
                    frame.dispose();
                } catch (Exception ex) {
                    UtilityUI.alertFrame("Errore sconosciuto");
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

    private void confirmExamFrame(List<Date> dates){
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(730, 400));
        frame.setTitle("Conferma");

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        JPanel m_panel = new JPanel(new GridLayout(2, 1));
        m_panel.setBorder(padding);

        JPanel dataPanel = new JPanel(new GridLayout(6, 2));
        JComboBox datesCombo = new JComboBox<>(dates.toArray());

        JLabel datelabel = new JLabel("Scegli data esame: ", SwingConstants.LEFT);
        datelabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        JLabel timelabel = new JLabel("Scegli orario esame: ", SwingConstants.LEFT);
        timelabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        JComboBox timesCombo = new JComboBox();
        timesCombo.setVisible(false);

        JLabel readylabel = new JLabel("Data in cui l'esame sarà probabilmente pronto: ", SwingConstants.LEFT);
        readylabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        JLabel readylabel_info = new JLabel("", SwingConstants.LEFT);
        readylabel_info.setVisible(false);


        JLabel doctorAskLabel = new JLabel("Vuoi scegliere medico per l'esame? ", SwingConstants.LEFT);
        doctorAskLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        doctorAskLabel.setVisible(false);

        JRadioButton yesRadioButton = new JRadioButton();
        yesRadioButton.setText("Si");
        JRadioButton noRadioButton = new JRadioButton();
        noRadioButton.setText("No");

        ButtonGroup g = new ButtonGroup();
        g.add(yesRadioButton);
        g.add(noRadioButton);

        JPanel radioPanel = new JPanel(new GridLayout(1, 2));
        radioPanel.add(yesRadioButton);
        radioPanel.add(noRadioButton);
        radioPanel.setVisible(false);

        JLabel doctorLabel = new JLabel("Inserisci cognome medico: ", SwingConstants.LEFT);
        doctorLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        doctorLabel.setVisible(false);

        JPanel doctorDataPanel =new JPanel(new GridLayout(1, 2));

        JTextField doctorfield =new JTextField();

        JButton button = new JButton("Seleziona");

        doctorDataPanel.add(doctorfield);
        doctorDataPanel.add(button);
        doctorDataPanel.setVisible(false);


        JLabel priceLabel = new JLabel("Prezzo esame: ", SwingConstants.LEFT);
        priceLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        priceLabel.setVisible(false);

        JLabel priceLabel_info = new JLabel("", SwingConstants.LEFT);
        priceLabel_info.setVisible(false);

        JButton confirmButton = new JButton("Conferma");
        confirmButton.setVisible(false);

        JButton annullaButton = new JButton("Annulla");

        datesCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime((Date) datesCombo.getSelectedItem());
                    List<LocalTime> times = clinicHub.chooseExamDate(cal);
                    for (LocalTime t: times) {
                        timesCombo.addItem(t);
                    }
                    timesCombo.setVisible(true);
                    readylabel_info.setVisible(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        timesCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Calendar cal = clinicHub.chooseExamTime((LocalTime) timesCombo.getSelectedItem());
                    readylabel_info.setText(String.valueOf(cal.getTime()));
                    readylabel_info.setVisible(true);
                    doctorAskLabel.setVisible(true);
                    radioPanel.setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        yesRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorLabel.setVisible(true);
                doctorDataPanel.setVisible(true);

            }
        });

        noRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorLabel.setVisible(false);
                doctorDataPanel.setVisible(false);
                priceLabel_info.setText(String.valueOf(clinicHub.showExamPrice()));
                priceLabel_info.setVisible(true);
                priceLabel.setVisible(true);
                confirmButton.setVisible(true);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clinicHub.chooseDoctor(doctorfield.getText());
                    priceLabel_info.setText(String.valueOf(clinicHub.showExamPrice()));
                    priceLabel.setVisible(true);
                    priceLabel_info.setVisible(true);
                    confirmButton.setVisible(true);
                } catch (Exception ex) {
                    UtilityUI.alertFrame("Medico non presente");
                }
            }
        });

        dataPanel.add(datelabel);
        dataPanel.add(datesCombo);
        dataPanel.add(timelabel);
        dataPanel.add(timesCombo);
        dataPanel.add(readylabel);
        dataPanel.add(readylabel_info);
        dataPanel.add(doctorAskLabel);
        dataPanel.add(radioPanel);
        dataPanel.add(doctorLabel);
        dataPanel.add(doctorDataPanel);
        dataPanel.add(priceLabel);
        dataPanel.add(priceLabel_info);


        m_panel.add(dataPanel);



        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clinicHub.confirmBooking();
                    frame.dispose();
                } catch (Exception ex) {
                    UtilityUI.alertFrame("Errore sconosciuto");
                }
            }
        });

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

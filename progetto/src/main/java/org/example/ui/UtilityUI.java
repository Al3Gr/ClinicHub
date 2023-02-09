package org.example.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtilityUI {
    public static boolean validateData(String ...inputString) {
        for(String stringa : inputString) {
            if(stringa.equals(""))
                return false;
        }
        return true;
    }

    public static void alertFrame(String message) {
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


}

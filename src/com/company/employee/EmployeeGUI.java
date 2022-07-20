package com.company.employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeGUI implements ActionListener {

        public JButton knopf1;
        public JButton knopf2;
        public JButton knopf3;
        public JLabel vorname;
        public JTextField text1;
        public JLabel nachname;
        public JTextField text2;
        public JLabel berufsbezeichnung;
        public JTextField text3;
        public JLabel einstellungsdatum;
        public JTextField text4;
        public JLabel personalnummer;
        public JTextField text5;
        public JLabel jahresgehalt;
        public JTextField text6;
        public JTable table1;
        public JTableHeader header1;
        public EmployeeList mitarbeiterliste;

    public EmployeeGUI() {
            mitarbeiterliste = new EmployeeList();
            JFrame f2 = new JFrame();
            f2.setTitle("Mitarbeiter");
            f2.setSize(2000, 1000);
            f2.setLocationRelativeTo(null);

            f2.setLayout(null);

            knopf1 = new JButton("Hinzufügen");
            knopf1.setBounds(200, 520, 350, 75);
            knopf1.addActionListener(this);
            text1 = new JTextField();
            f2.add(knopf1);
            //m1.firstName = text1.getText();
            //String test = m1.firstName;

            /*Folgendes Suchen: Actionslistener der regestriert den JButton - rausgelesen in Strings und wieder übertragbar in JTable
            *                   Das auf alle KAtegorien kopieren
            *                   Hinzufügen und Löschen erstmal*/

            knopf2 = new JButton("Löschen");
            knopf2.setBounds(200, 595, 350, 75);
            knopf2.addActionListener(this);
            f2.add(knopf2);

            knopf3 = new JButton("Ändern");
            knopf3.setBounds(200, 670, 350, 75);
            knopf3.addActionListener(this);
            f2.add(knopf3);

            vorname = new JLabel("Vorname:");
            vorname.setBounds(50, 30, 150, 20);
            f2.add(vorname);

            text1.setBounds(200, 30, 350, 20);
            f2.add(text1);

            nachname = new JLabel("Nachname:");
            nachname.setBounds(50, 65, 150, 20);
            f2.add(nachname);
            text2 = new JTextField();
            text2.setBounds(200, 65, 350, 20);
            f2.add(text2);

            berufsbezeichnung = new JLabel("Berufsfbezeichnung:");
            berufsbezeichnung.setBounds(50, 100, 150, 20);
            f2.add(berufsbezeichnung);
            text3 = new JTextField();
            text3.setBounds(200, 100, 350, 20);
            f2.add(text3);

            einstellungsdatum = new JLabel("Einstellungsdatum:");
            einstellungsdatum.setBounds(50, 135, 150, 20);
            f2.add(einstellungsdatum);
            text4 = new JTextField();
            text4.setBounds(200, 135, 350, 20);
            f2.add(text4);

            personalnummer = new JLabel("Personalnummer:");
            personalnummer.setBounds(50, 170, 150, 20);
            f2.add(personalnummer);
            text5 = new JTextField();
            text5.setBounds(200, 170, 350, 20);
            f2.add(text5);

            jahresgehalt = new JLabel("Jahresgehalt:");
            jahresgehalt.setBounds(50, 205, 150, 20);
            f2.add(jahresgehalt);
            text6 = new JTextField();
            text6.setBounds(200, 205, 350, 20);
            f2.add(text6);

            //           public static void main( String args[] ) {
            //                    String rowData[][] = {
            //                  };
            //                   String  columnNames[] = {
            //                           "Vorname", "Nachname", "Berufsbezeichnung", "Einstellungsdatum", "Personalnummer", "Jahresgehalt"
            //                  };
            //                table1 = new JTable( rowData, columnNames );

            /*table1 = new JTable(30, 6);
            table1.setBounds(580, 30, 815, 710);
            table1.setShowGrid(true);
            //table1.insertRow(0, new Object[] {test});
            f2.add(table1);*/

            JPanel p1 = new JPanel();
            String ro[][] = {{"test", "100", "99","98", "97", "96"}};
            String col[] = {"vn", "nn", "be", "ed", "pn", "jg"};
            DefaultTableModel model = new DefaultTableModel(ro, col);
            JTable table1 = new JTable(model);
            //model.insertRow(0, new Object[] {test, "100", "99","98", "97", "96"});
            p1.add(table1);

            f2.setVisible(true);

    }


    public static void main(String[] args){new EmployeeGUI();}

        @Override
        public void actionPerformed(ActionEvent e) {

                if (e.getSource() == knopf1)
                {
                  mitarbeiterliste.hinzufuegen(text1.getText(),  text2.getText(), text3.getText(), text4.getText(), Integer.parseInt(text5.getText()), Integer.parseInt(text6.getText()));

                }

        }


}

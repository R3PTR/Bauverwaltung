import javax.swing.*;
import java.awt.event.*;

public class AuftragGUI implements ActionListener {

    private JButton knopf1;
    private JButton knopf2;
    private JButton knopf3;
    private JLabel auftraggeber;
    private JTextField text1;
    private JLabel postleitzahl;
    private JTextField text2;
    private JLabel adresse;
    private JTextField text3;
    private JLabel aufgaben;
    private JTextArea area1;
    private JLabel startdatum;
    private JTextField text4;
    private JLabel enddatum;
    private JTextField text5;
    private JTable table1;

    public AuftragGUI() {
        JFrame f3 = new JFrame();
        f3.setTitle("Auftrag");
        f3.setSize(2000, 1000);
        f3.setLocationRelativeTo(null);

        f3.setLayout(null);

        knopf1 = new JButton("Hinzufügen");
        knopf1.setBounds(150,520,400,75);
        knopf1.addActionListener(this);
        f3.add(knopf1);

        knopf2 = new JButton("Löschen");
        knopf2.setBounds(150,595,400,75);
        knopf2.addActionListener(this);
        f3.add(knopf2);

        knopf3 = new JButton("Ändern");
        knopf3.setBounds(150,670,400,75);
        knopf3.addActionListener(this);
        f3.add(knopf3);

        auftraggeber = new JLabel("Auftraggeber:");
        auftraggeber.setBounds(50,30,150,20);
        f3.add(auftraggeber);
        text1 = new JTextField();
        text1.setBounds(150,30,400,20);
        f3.add(text1);

        postleitzahl = new JLabel("Postleitzahl:");
        postleitzahl.setBounds(50,65,150,20);
        f3.add(postleitzahl);
        text2 = new JTextField();
        text2.setBounds(150,65,400,20);
        f3.add(text2);

        adresse = new JLabel("Adresse:");
        adresse.setBounds(50,100,150,20);
        f3.add(adresse);
        text3 = new JTextField();
        text3.setBounds(150,100,400,20);
        f3.add(text3);

        aufgaben = new JLabel("Aufgaben:");
        aufgaben.setBounds(50,205,150,20);
        f3.add(aufgaben);
        area1 = new JTextArea();
        area1.setBounds(150,205,400,300);
        f3.add(area1);

        startdatum = new JLabel("Startdatum:");
        startdatum.setBounds(50,135,150,20);
        f3.add(startdatum);
        text4 = new JTextField();
        text4.setBounds(150,135,400,20);
        f3.add(text4);

        enddatum = new JLabel("Enddatum:");
        enddatum.setBounds(50,170,150,20);
        f3.add(enddatum);
        text5 = new JTextField();
        text5.setBounds(150,170,400,20);
        f3.add(text5);

        table1 = new JTable(30,6);
        table1.setBounds(580, 30,815, 710);
        table1.setShowGrid(true);
        f3.add(table1);

        f3.setVisible(true);
    }

    public static void main(String[] args){new AuftragGUI();}

        @Override
        public void actionPerformed(ActionEvent e) {
        }
}

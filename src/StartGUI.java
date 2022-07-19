import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  StartGUI implements ActionListener {

    private JButton k1;
    private MitarbeiterGUI gui1;
    private JButton k2;
    private AuftragGUI gui2;

    public StartGUI() {
        JFrame f1 = new JFrame("Verwaltungsprogramm");
        f1.setSize(2000, 1000);
        f1.setLocationRelativeTo(null);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setLayout(null);

        k1 = new JButton("Mitarbeiter");
        k1.setBounds(375,300,350,100);
        k1.addActionListener(this);
        f1.add(k1);

        k2 = new JButton("Aufträge");
        k2.setBounds(725,300,350,100);
        k2.addActionListener(this);
        f1.add(k2);

        f1.setVisible(true);

    }

    public static void main(String[] args) {
        new StartGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k1) {
            gui1 = new MitarbeiterGUI();
        }

        if(e.getSource() == k2) {
            gui2 = new AuftragGUI();
        }
     }
}
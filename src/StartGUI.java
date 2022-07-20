import com.company.employee.EmployeeGUI;
import com.company.job.JobGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  StartGUI implements ActionListener {

    private JButton employees;
    private EmployeeGUI gui1;
    private JButton contract;
    private JobGUI gui2;

    public StartGUI() {
        JFrame mainFrame = new JFrame("Verwaltungsprogramm");
        mainFrame.setSize(1280, 720);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        employees = new JButton("Mitarbeiter");
        employees.setBounds(375,300,350,100);
        employees.addActionListener(this);
        mainFrame.add(employees);

        contract = new JButton("Aufträge");
        contract.setBounds(725,300,350,100);
        contract.addActionListener(this);
        mainFrame.add(contract);

        mainFrame.setVisible(true);

    }

    public static void main(String[] args) {
        new StartGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == employees) {
            gui1 = new EmployeeGUI();
        }

        if(e.getSource() == contract) {
            gui2 = new JobGUI();
        }
     }
}
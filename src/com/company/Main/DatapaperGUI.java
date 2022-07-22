package com.company.Main;

import com.company.employee.Employee;
import com.company.job.Job;
import com.company.job.JobGUI;

import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class DatapaperGUI {

    private JTextArea datapaper;
    private JFrame datapaperFrame;

    public DatapaperGUI(Job job) {
        datapaperFrame = new JFrame();
        datapaperFrame.setTitle("Aufträge");
        //16:9 Ratio
        datapaperFrame.setSize(800, 800);
        datapaperFrame.setResizable(false);
        datapaperFrame.setLocationRelativeTo(null);

        datapaperFrame.setLayout(null);

        datapaper = new JTextArea();
        datapaper.setBounds(50,50,700,700);
        datapaper.setEditable(false);

        datapaperFrame.add(datapaper);
        datapaper.setText("");
        datapaper.append("Auftraggeber: " + job.getClient() + "\n");
        datapaper.append("Postleitzahl und Addresse: " + job.getPostCode() + " " + job.getAddress() + "\n");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        datapaper.append("Startdatum: " + job.getStartDate().format(dateTimeFormatter) + " Enddatum: " + job.getEndDate().format(dateTimeFormatter) + "\n");
        datapaper.append("Aufgaben: " + job.getDescription() + "\n");
        datapaper.append("Mitarbeiter: \n");
        for(Employee employee : JobGUI.employeeToJob.get(job)) {
            datapaper.append("Name: " + employee.getName() + " | Berufsbezeichnung: " + employee.getJobTitle() + " | Einstellungsdatum: " + employee.getHireDate().format(dateTimeFormatter) + " | Jahresgehalt: " + String.valueOf(employee.getAnnualSalary()) + "\n");
        }

        datapaperFrame.setVisible(true);
    }
}

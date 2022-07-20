package com.company.job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JobGUI implements ActionListener {

    private JButton addJobButton;
    private JButton deleteJobButton;
    private JButton modifyJob;
    private JLabel clientLabel;
    private JTextField client;
    private JLabel postCodeLabel;
    private JTextField postCode;
    private JLabel addressLabel;
    private JTextField address;
    private JLabel descriptionLabel;
    private JTextArea description;
    private JLabel startDateLabel;
    private JTextField startDate;
    private JLabel endDateLabel;
    private JTextField endDate;
    private JTable jobTable;

    private JFrame jobFrame;

    private DefaultTableModel defaultTableModel;

    public static List<Job> jobList = new ArrayList<>();

    public JobGUI() {
        jobFrame = new JFrame();
        jobFrame.setTitle("Aufträge");
        //16:9 Ratio
        jobFrame.setSize(1424, 801);
        jobFrame.setResizable(false);
        jobFrame.setLocationRelativeTo(null);

        jobFrame.setLayout(null);

        addJobButtonToFrame();

        deleteJobButton = new JButton("Löschen");
        deleteJobButton.setBounds(150,595,400,75);
        deleteJobButton.addActionListener(this);
        jobFrame.add(deleteJobButton);

        modifyJob = new JButton("Ändern");
        modifyJob.setBounds(150,670,400,75);
        modifyJob.addActionListener(this);
        jobFrame.add(modifyJob);

        clientLabel = new JLabel("Auftraggeber:");
        clientLabel.setBounds(50,30,150,20);
        jobFrame.add(clientLabel);
        client = new JTextField();
        client.setBounds(150,30,400,20);
        jobFrame.add(client);

        postCodeLabel = new JLabel("Postleitzahl:");
        postCodeLabel.setBounds(50,65,150,20);
        jobFrame.add(postCodeLabel);
        postCode = new JTextField();
        postCode.setBounds(150,65,400,20);
        jobFrame.add(postCode);

        addressLabel = new JLabel("Adresse:");
        addressLabel.setBounds(50,100,150,20);
        jobFrame.add(addressLabel);
        address = new JTextField();
        address.setBounds(150,100,400,20);
        jobFrame.add(address);

        descriptionLabel = new JLabel("Aufgaben:");
        descriptionLabel.setBounds(50,205,150,20);
        jobFrame.add(descriptionLabel);
        description = new JTextArea();
        description.setBounds(150,205,400,300);
        jobFrame.add(description);

        startDateLabel = new JLabel("Startdatum:");
        startDateLabel.setBounds(50,135,150,20);
        jobFrame.add(startDateLabel);
        startDate = new JTextField();
        startDate.setBounds(150,135,400,20);
        jobFrame.add(startDate);


        endDateLabel = new JLabel("Enddatum:");
        endDateLabel.setBounds(50,170,150,20);
        jobFrame.add(endDateLabel);
        endDate = new JTextField();
        endDate.setBounds(150,170,400,20);
        jobFrame.add(endDate);

        String[] jobTableHeader={"Auftragsnummer", "Auftraggeber","Postleitzahl","Adresse","Startdatum","Enddatum","Aufgaben"};
        defaultTableModel = new DefaultTableModel(jobTableHeader, 0);
        defaultTableModel.addRow(jobTableHeader);
        jobTable = new JTable(defaultTableModel);
        jobTable.setBounds(580, 30,815, 710);
        jobTable.setShowGrid(true);


        jobFrame.add(jobTable);

        jobFrame.setVisible(true);
    }

    private void addJobButtonToFrame() {
        addJobButton = new JButton("Hinzufügen");
        addJobButton.setBounds(150,520,400,75);
        addJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobGUI.jobList.add(new Job(client.getText(), postCode.getText(), address.getText(), description.getText(), getStartDate(), getEndDate()));
                String[] jobArray = {client.getText(), postCode.getText(), address.getText(), description.getText(), startDate.getText(), endDate.getText()};
                defaultTableModel.addRow(jobArray);
                log(JobGUI.jobList.toString());
            }
        });
        jobFrame.add(addJobButton);
    }
    private LocalDate getStartDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(this.startDate.getText(), dateTimeFormatter);
    }
    private LocalDate getEndDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(this.endDate.getText(), dateTimeFormatter);
    }

    public static void main(String[] args){new JobGUI();}

    private void log(Object output) {
        System.out.println(output);
    }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
}

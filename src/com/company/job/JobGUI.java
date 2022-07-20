package com.company.job;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JobGUI implements ActionListener {

    private JButton addJobButton;
    private JButton deleteJobButton;
    private JButton modifyJobButton;
    private JButton exportJobsButton;
    private JButton importJobsButton;
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

    private int activeRow = -1;

    private JLabel outputLabel;

    private JTextArea output;

    private JLabel employeeLabel;

    private JList employees;

    public static List<Job> jobList = new ArrayList<>();

    public JobGUI() {
        jobFrame = new JFrame();
        jobFrame.setTitle("Aufträge");
        //16:9 Ratio
        jobFrame.setSize(1424, 801);
        jobFrame.setResizable(false);
        jobFrame.setLocationRelativeTo(null);

        jobFrame.setLayout(null);

        addAddJobButtonToFrame();

        addDeleteJobButtonToFrame();

        addModifyJobButtonToFrame();

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
        description.setBounds(150,205,400,140);
        jobFrame.add(description);

        employeeLabel = new JLabel("Mitarbeiter:");
        employeeLabel.setBounds(50,355,150,20);
        jobFrame.add(employeeLabel);


        startDateLabel = new JLabel("Startdatum:");
        startDateLabel.setBounds(50,135,150,20);
        jobFrame.add(startDateLabel);
        startDate = new JTextField();
        startDate.setBounds(150,135,400,20);
        jobFrame.add(startDate);

        addJobTableToFrame();

        outputLabel = new JLabel("Output:");
        outputLabel.setBounds(580, 550,150,20);
        jobFrame.add(outputLabel);
        output = new JTextArea();
        output.setBounds(580,580,400,100);
        output.setEditable(false);
        jobFrame.add(output);


        endDateLabel = new JLabel("Enddatum:");
        endDateLabel.setBounds(50,170,150,20);
        jobFrame.add(endDateLabel);
        endDate = new JTextField();
        endDate.setBounds(150,170,400,20);
        jobFrame.add(endDate);

        addExportButtonToFrame();

        addImportButtonToFrame();

        jobFrame.setVisible(true);
    }

    private void addImportButtonToFrame() {
        importJobsButton = new JButton("Importieren");
        importJobsButton.setBounds(1000,550,400,75);
        importJobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        jobFrame.add(importJobsButton);
    }

    private void addExportButtonToFrame() {
        exportJobsButton = new JButton("Exportieren");
        exportJobsButton.setBounds(1000,650,400,75);
        exportJobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("csv", "csv");
                jFileChooser.setFileFilter(extensionFilter);
                int status = jFileChooser.showSaveDialog(null);
                if(status == JFileChooser.APPROVE_OPTION) {
                    File f = jFileChooser.getSelectedFile();

                    log(f.getName());
                }
            }
        });
        jobFrame.add(exportJobsButton);
    }

    private void addModifyJobButtonToFrame() {
        modifyJobButton = new JButton("Ändern");
        modifyJobButton.setBounds(150, 670, 400, 75);
        modifyJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeRow != -1) {
                    JobGUI.jobList.add(activeRow-1, new Job(client.getText(), postCode.getText(), address.getText(), description.getText(), getStartDate(), getEndDate()));
                    String[] jobArray = {client.getText(), postCode.getText(), address.getText(), startDate.getText(), endDate.getText(), "Zum Anzeigen klicken"};
                    defaultTableModel.removeRow(activeRow);
                    defaultTableModel.insertRow(activeRow, jobArray);
                    resetTextFields();
                    output.setText("Job geändert");
                }
            }
        });
        jobFrame.add(modifyJobButton);
    }

    private void addDeleteJobButtonToFrame() {
        deleteJobButton = new JButton("Löschen");
        deleteJobButton.setBounds(150,595,400,75);
        deleteJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeRow != -1) {
                    JobGUI.jobList.remove(activeRow-1);
                    defaultTableModel.removeRow(activeRow);
                    resetTextFields();
                    output.setText("Job gelöscht");
                }


            }
        });
        jobFrame.add(deleteJobButton);
    }

    private void addJobTableToFrame() {
        String[] jobTableHeader={"Auftraggeber","Postleitzahl","Adresse","Startdatum","Enddatum", "Aufgaben"};
        defaultTableModel = new DefaultTableModel(jobTableHeader, 0);
        defaultTableModel.addRow(jobTableHeader);
        jobTable = new JTable(defaultTableModel);
        jobTable.setBounds(580, 30,815, 500);
        jobTable.setShowGrid(true);
        jobTable.setDefaultEditor(Object.class, null);
        jobTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                activeRow = jobTable.getSelectedRow();
                if(activeRow == 0)
                    return;
                client.setText((String) jobTable.getValueAt(activeRow, 0));
                postCode.setText((String) jobTable.getValueAt(activeRow, 1));
                address.setText((String) jobTable.getValueAt(activeRow, 2));
                startDate.setText((String) jobTable.getValueAt(activeRow, 3));
                endDate.setText((String) jobTable.getValueAt(activeRow, 4));
                description.setText(JobGUI.jobList.get(activeRow-1).getDescription());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        jobFrame.add(jobTable);
    }

    private void addAddJobButtonToFrame() {
        addJobButton = new JButton("Hinzufügen");
        addJobButton.setBounds(150,520,400,75);
        addJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobGUI.jobList.add(new Job(client.getText(), postCode.getText(), address.getText(), description.getText(), getStartDate(), getEndDate()));
                String[] jobArray = {client.getText(), postCode.getText(), address.getText(), startDate.getText(), endDate.getText(), "Zum Anzeigen klicken"};
                defaultTableModel.addRow(jobArray);
                resetTextFields();
                output.setText("Neuer Job angelegt");
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

    private void resetTextFields() {
        client.setText("");
        postCode.setText("");
        address.setText("");
        description.setText("");
        startDate.setText("");
        endDate.setText("");
        activeRow = -1;
    }

    public static void main(String[] args){new JobGUI();}

    private void log(Object output) {
        System.out.println(output);
    }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
}

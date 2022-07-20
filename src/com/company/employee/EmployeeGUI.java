package com.company.employee;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeGUI implements ActionListener {

        private JButton addEmployeeButton;
        private JButton deleteEmployeeButton;
        private JButton modifyemployeeButton;
        private JButton exportemployeesButton;
        private JButton importemployeesButton;
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
        private JTable employeeTable;

        private JFrame employeeFrame;

        private DefaultTableModel defaultTableModel;

        private int activeRow = -1;

        private JLabel outputLabel;

        private JTextArea output;

        private JLabel employeeLabel;

        private JList employees;

        public static List<Employee> employeeList = new ArrayList<>();

        public EmployeeGUI() {
                employeeFrame = new JFrame();
                employeeFrame.setTitle("Aufträge");
                //16:9 Ratio
                employeeFrame.setSize(1424, 801);
                employeeFrame.setResizable(false);
                employeeFrame.setLocationRelativeTo(null);

                employeeFrame.setLayout(null);

                addAddemployeeButtonToFrame();

                addDeleteemployeeButtonToFrame();

                addModifyemployeeButtonToFrame();

                clientLabel = new JLabel("Auftraggeber:");
                clientLabel.setBounds(50,30,150,20);
                employeeFrame.add(clientLabel);
                client = new JTextField();
                client.setBounds(150,30,400,20);
                employeeFrame.add(client);

                postCodeLabel = new JLabel("Postleitzahl:");
                postCodeLabel.setBounds(50,65,150,20);
                employeeFrame.add(postCodeLabel);
                postCode = new JTextField();
                postCode.setBounds(150,65,400,20);
                employeeFrame.add(postCode);

                addressLabel = new JLabel("Adresse:");
                addressLabel.setBounds(50,100,150,20);
                employeeFrame.add(addressLabel);
                address = new JTextField();
                address.setBounds(150,100,400,20);
                employeeFrame.add(address);

                descriptionLabel = new JLabel("Aufgaben:");
                descriptionLabel.setBounds(50,205,150,20);
                employeeFrame.add(descriptionLabel);
                description = new JTextArea();
                description.setBounds(150,205,400,140);
                employeeFrame.add(description);

                employeeLabel = new JLabel("Mitarbeiter:");
                employeeLabel.setBounds(50,355,150,20);
                employeeFrame.add(employeeLabel);

                DefaultListModel<String> defaultListModel = new DefaultListModel<>();

                employees = new JList(defaultListModel);
                employees.setBounds(150,355,400,140);
                employeeFrame.add(employees);

                startDateLabel = new JLabel("Startdatum:");
                startDateLabel.setBounds(50,135,150,20);
                employeeFrame.add(startDateLabel);
                startDate = new JTextField();
                startDate.setBounds(150,135,400,20);
                employeeFrame.add(startDate);

                addemployeeTableToFrame();

                outputLabel = new JLabel("Output:");
                outputLabel.setBounds(580, 550,150,20);
                employeeFrame.add(outputLabel);
                output = new JTextArea();
                output.setBounds(580,580,400,100);
                output.setEditable(false);
                employeeFrame.add(output);


                endDateLabel = new JLabel("Enddatum:");
                endDateLabel.setBounds(50,170,150,20);
                employeeFrame.add(endDateLabel);
                endDate = new JTextField();
                endDate.setBounds(150,170,400,20);
                employeeFrame.add(endDate);

                addExportButtonToFrame();

                addImportButtonToFrame();

                employeeFrame.setVisible(true);
        }

        private void addImportButtonToFrame() {
                importemployeesButton = new JButton("Importieren");
                importemployeesButton.setBounds(1000,550,400,75);
                importemployeesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                JFileChooser jFileChooser = new JFileChooser();
                                FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("csv", "csv");
                                jFileChooser.setFileFilter(extensionFilter);
                                int status = jFileChooser.showOpenDialog(null);
                                if(status == JFileChooser.APPROVE_OPTION) {
                                        try {
                                                BufferedReader bufferedReader = new BufferedReader(new FileReader(jFileChooser.getSelectedFile()));
                                                String line = bufferedReader.readLine();
                                                while (line != null) {
                                                        if(!line.equals("Auftraggeber,Postleitzahl,Adresse,Startdatum,Enddatum,Aufgaben")) {
                                                                String[] entries = line.split(",");
                                                                // employee = new employee(entries[0], entries[1], entries[2], entries[5].replace("|", "\n"), getDateFromString(entries[3]), getDateFromString(entries[4]));
                                                                // employeeGUI.employeeList.add(employee);
                                                                entries[5] = "Zum Anzeigen klicken";
                                                                defaultTableModel.addRow(entries);
                                                        }
                                                        line = bufferedReader.readLine();
                                                }
                                                output.setText("employees importiert");
                                        } catch (FileNotFoundException ex) {
                                                throw new RuntimeException(ex);
                                        } catch (IOException ex) {
                                                throw new RuntimeException(ex);
                                        }
                                }
                        }
                });
                employeeFrame.add(importemployeesButton);
        }

        private void addExportButtonToFrame() {
                exportemployeesButton = new JButton("Exportieren");
                exportemployeesButton.setBounds(1000,650,400,75);
                exportemployeesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(EmployeeGUI.employeeList.size() <1) {
                                        output.setText("Keine employees zum Exportieren vorhanden!");
                                        return;
                                }
                                JFileChooser jFileChooser = new JFileChooser();
                                FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("csv", "csv");
                                jFileChooser.setFileFilter(extensionFilter);
                                int status = jFileChooser.showSaveDialog(null);
                                if(status == JFileChooser.APPROVE_OPTION) {
                                        File f = jFileChooser.getSelectedFile();
                                        int extensionIndex = f.getName().lastIndexOf('.');
                                        if(extensionIndex == -1) {
                                                f = new File(f.getParent(), f.getName() + ".csv");
                                        } else {
                                                String name = f.getName().substring(0, extensionIndex);
                                                f = new File(f.getParent(), name + ".csv");
                                        }
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Auftraggeber,Postleitzahl,Adresse,Startdatum,Enddatum,Aufgaben\n");
                                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                                        for (int i = 0; i<EmployeeGUI.employeeList.size(); i++) {
                                                Employee employee = EmployeeGUI.employeeList.get(i);
                                                //sb.append(employee.getClient()).append(",").append(employee.getPostCode()).append(",").append(employee.getAddress()).append(",").append(employee.getStartDate().format(dateTimeFormatter)).append(",").append(employee.getEndDate().format(dateTimeFormatter)).append(",").append(employee.getDescription().replace("\n", "|"));
                                                if(i < EmployeeGUI.employeeList.size()-1) {
                                                        sb.append("\n");
                                                }
                                        }
                                        try {
                                                PrintWriter printWriter = new PrintWriter(f);
                                                printWriter.print(sb.toString());
                                                printWriter.close();
                                                output.setText("employees exportiert");
                                        } catch (FileNotFoundException ex) {
                                                output.setText("Fehler beim Export");
                                                throw new RuntimeException(ex);
                                        }
                                }
                        }
                });
                employeeFrame.add(exportemployeesButton);
        }

        private void addModifyemployeeButtonToFrame() {
                modifyemployeeButton = new JButton("Ändern");
                modifyemployeeButton.setBounds(150, 670, 400, 75);
                modifyemployeeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(activeRow != -1) {
                                        //EmployeeGUI.employeeList.add(activeRow-1, new Employee(client.getText(), postCode.getText(), address.getText(), description.getText(), getStartDate(), getEndDate()));
                                        String[] employeeArray = {client.getText(), postCode.getText(), address.getText(), startDate.getText(), endDate.getText(), "Zum Anzeigen klicken"};
                                        defaultTableModel.removeRow(activeRow);
                                        defaultTableModel.insertRow(activeRow, employeeArray);
                                        resetTextFields();
                                        output.setText("employee geändert");
                                }
                        }
                });
                employeeFrame.add(modifyemployeeButton);
        }

        private void addDeleteemployeeButtonToFrame() {
                deleteEmployeeButton = new JButton("Löschen");
                deleteEmployeeButton.setBounds(150,595,400,75);
                deleteEmployeeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(activeRow != -1) {
                                        EmployeeGUI.employeeList.remove(activeRow-1);
                                        defaultTableModel.removeRow(activeRow);
                                        resetTextFields();
                                        output.setText("employee gelöscht");
                                }


                        }
                });
                employeeFrame.add(deleteEmployeeButton);
        }

        private void addemployeeTableToFrame() {
                String[] employeeTableHeader={"Auftraggeber","Postleitzahl","Adresse","Startdatum","Enddatum", "Aufgaben"};
                defaultTableModel = new DefaultTableModel(employeeTableHeader, 0);
                defaultTableModel.addRow(employeeTableHeader);
                employeeTable = new JTable(defaultTableModel);
                employeeTable.setBounds(580, 30,815, 500);
                employeeTable.setShowGrid(true);
                employeeTable.setDefaultEditor(Object.class, null);
                employeeTable.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                activeRow = employeeTable.getSelectedRow();
                                if(activeRow == 0)
                                        return;
                                client.setText((String) employeeTable.getValueAt(activeRow, 0));
                                postCode.setText((String) employeeTable.getValueAt(activeRow, 1));
                                address.setText((String) employeeTable.getValueAt(activeRow, 2));
                                startDate.setText((String) employeeTable.getValueAt(activeRow, 3));
                                endDate.setText((String) employeeTable.getValueAt(activeRow, 4));
                                //description.setText(EmployeeGUI.employeeList.get(activeRow-1).getDescription());
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
                employeeFrame.add(employeeTable);
        }

        private void addAddemployeeButtonToFrame() {
                addEmployeeButton = new JButton("Hinzufügen");
                addEmployeeButton.setBounds(150,520,400,75);
                addEmployeeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                //EmployeeGUI.employeeList.add(new Employee(client.getText(), postCode.getText(), address.getText(), description.getText(), getStartDate(), getEndDate()));
                                String[] employeeArray = {client.getText(), postCode.getText(), address.getText(), startDate.getText(), endDate.getText(), "Zum Anzeigen klicken"};
                                defaultTableModel.addRow(employeeArray);
                                resetTextFields();
                                output.setText("Neuer employee angelegt");
                        }
                });
                employeeFrame.add(addEmployeeButton);
        }
        private LocalDate getStartDate() {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return LocalDate.parse(this.startDate.getText(), dateTimeFormatter);
        }
        private LocalDate getEndDate() {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return LocalDate.parse(this.endDate.getText(), dateTimeFormatter);
        }

        private LocalDate getDateFromString(String date) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return LocalDate.parse(date, dateTimeFormatter);
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

        public static void main(String[] args){new EmployeeGUI();}

        private void log(Object output) {
                System.out.println(output);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
}

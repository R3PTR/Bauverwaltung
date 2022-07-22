package com.company.employee;

import com.company.job.Job;
import com.company.job.JobGUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.security.spec.ECField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeGUI {

        private JTextField name;
        private JTextField jobTitle;
        private JTextField startDate;
        private JTextField annualSalary;
        private JTable employeeTable;

        private JFrame employeeFrame;

        private DefaultTableModel defaultTableModel;

        private int activeRow = -1;

        private JTextArea output;

        private JLabel employeeLabel;

        private JList employees;

        public static List<Employee> employeeList = new ArrayList<>();

        public EmployeeGUI() {
                employeeFrame = new JFrame();
                employeeFrame.setTitle("Mitarbeiter");
                //16:9 Ratio
                employeeFrame.setSize(1424, 801);
                employeeFrame.setResizable(false);
                employeeFrame.setLocationRelativeTo(null);

                employeeFrame.setLayout(null);

                addAddemployeeButtonToFrame();

                addDeleteemployeeButtonToFrame();

                addModifyemployeeButtonToFrame();

                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setBounds(50,30,150,20);
                employeeFrame.add(nameLabel);
                name = new JTextField();
                name.setBounds(200,30,350,20);
                employeeFrame.add(name);

                JLabel jobTitleLabel = new JLabel("Berufsbezeichnung:");
                jobTitleLabel.setBounds(50,65,150,20);
                employeeFrame.add(jobTitleLabel);
                jobTitle = new JTextField();
                jobTitle.setBounds(200,65,350,20);
                employeeFrame.add(jobTitle);

                JLabel startDateLabel = new JLabel("Einstellungsdatum:");
                startDateLabel.setBounds(50,135,150,20);
                employeeFrame.add(startDateLabel);
                startDate = new JTextField();
                startDate.setBounds(200,135,350,20);
                employeeFrame.add(startDate);

                JLabel annualSalaryLabel = new JLabel("Jahresgehalt:");
                annualSalaryLabel.setBounds(50,100,150,20);
                employeeFrame.add(annualSalaryLabel);
                annualSalary = new JTextField();
                annualSalary.setBounds(200,100,350,20);
                employeeFrame.add(annualSalary);

                addemployeeTableToFrame();

                JLabel outputLabel = new JLabel("Output:");
                outputLabel.setBounds(580, 550,150,20);
                employeeFrame.add(outputLabel);
                output = new JTextArea();
                output.setBounds(580,580,400,100);
                output.setEditable(false);
                employeeFrame.add(output);

                addExportButtonToFrame();

                addImportButtonToFrame();

                employeeFrame.setVisible(true);

                loadPossibleEmployees();
        }

        private boolean checkInput() {
                output.setText("");
                boolean correct = true;
                if(name.getText().isBlank()) {
                        output.append("Name muss angegeben werden!\n");
                        correct = false;
                }
                if(name.getText().matches(".*[0-9].*")) {
                        output.append("Name darf keine Zahlen enthalten!\n");
                        correct = false;
                }
                for (Employee employee : EmployeeGUI.employeeList) {
                        if(employee.getName().equals(name.getText())) {
                                output.append("Ein Mitarbeiter mit diesem Namen existiert bereits!\n");
                                correct = false;
                        }
                }
                if(jobTitle.getText().isBlank()) {
                        output.append("Berufsbezeichnung muss angegeben werden!\n");
                        correct = false;
                }
                try {
                        String.valueOf(annualSalary.getText());
                } catch (Exception e) {
                        output.append("Jahresgehalt muss eine ganze Zahl sein!\n");
                        correct = false;
                }

                return correct;
        }

        private void loadPossibleEmployees() {
                if(EmployeeGUI.employeeList.size() > 0) {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        for (int i = 0; i < EmployeeGUI.employeeList.size(); i++) {
                                Employee employee = EmployeeGUI.employeeList.get(i);
                                defaultTableModel.addRow(new String[]{employee.getName(), employee.getJobTitle(), employee.getHireDate().format(dateTimeFormatter), String.valueOf(employee.getAnnualSalary())});
                        }
                }
        }

        private void addImportButtonToFrame() {
                JButton importemployeesButton = new JButton("Importieren");
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
                                                        if(!line.equals("Name,Berufsbezeichnung,Einstellungsdatum,Jahresgehalt")) {
                                                                String[] entries = line.split(",");
                                                                Employee employee = new Employee(entries[0], entries[1], getDateFromString(entries[2]), Integer.parseInt(entries[3]));
                                                                EmployeeGUI.employeeList.add(employee);
                                                                defaultTableModel.addRow(entries);
                                                        }
                                                        line = bufferedReader.readLine();
                                                }
                                                output.setText("Mitarbeiter importiert");
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
                JButton exportemployeesButton = new JButton("Exportieren");
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
                                        sb.append("Name,Berufsbezeichnung,Einstellungsdatum,Jahresgehalt\n");
                                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                                        for (int i = 0; i<EmployeeGUI.employeeList.size(); i++) {
                                                Employee employee = EmployeeGUI.employeeList.get(i);
                                                sb.append(employee.getName()).append(",").append(employee.getJobTitle()).append(",").append(employee.getHireDate().format(dateTimeFormatter)).append(",").append(employee.getAnnualSalary());
                                                if(i < EmployeeGUI.employeeList.size()-1) {
                                                        sb.append("\n");
                                                }
                                        }
                                        try {
                                                PrintWriter printWriter = new PrintWriter(f);
                                                printWriter.print(sb.toString());
                                                printWriter.close();
                                                output.setText("Mitarbeiter exportiert");
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
                JButton modifyemployeeButton = new JButton("Ändern");
                modifyemployeeButton.setBounds(150, 670, 400, 75);
                modifyemployeeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(activeRow != -1) {
                                        EmployeeGUI.employeeList.add(activeRow-1, new Employee(name.getText(), jobTitle.getText(), getStartDate(), Integer.parseInt(annualSalary.getText())));
                                        String[] employeeArray = {name.getText(), jobTitle.getText(), startDate.getText(), annualSalary.getText()};
                                        defaultTableModel.removeRow(activeRow);
                                        defaultTableModel.insertRow(activeRow, employeeArray);
                                        resetTextFields();
                                        output.setText("Mitarbeiter geändert");
                                }
                        }
                });
                employeeFrame.add(modifyemployeeButton);
        }

        private void addDeleteemployeeButtonToFrame() {
                JButton deleteEmployeeButton = new JButton("Löschen");
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
                String[] employeeTableHeader={"Name","Berufsbezeichnung","Einstellungsdatum","Jahresgehalt"};
                defaultTableModel = new DefaultTableModel(employeeTableHeader, 0);
                defaultTableModel.addRow(employeeTableHeader);
                employeeTable = new JTable(defaultTableModel);
                employeeTable.setBounds(580, 30,815, 500);
                employeeTable.setShowGrid(true);
                employeeTable.setDefaultEditor(Object.class, null);
                employeeTable.setRowSelectionAllowed(true);
                employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                employeeTable.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                activeRow = employeeTable.getSelectedRow();
                                if(activeRow == 0)
                                        return;
                                name.setText((String) employeeTable.getValueAt(activeRow, 0));
                                jobTitle.setText((String) employeeTable.getValueAt(activeRow, 1));
                                startDate.setText((String) employeeTable.getValueAt(activeRow, 2));
                                annualSalary.setText((String) employeeTable.getValueAt(activeRow, 3));
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
                JButton addEmployeeButton = new JButton("Hinzufügen");
                addEmployeeButton.setBounds(150,520,400,75);
                addEmployeeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(!checkInput())
                                        return;
                                EmployeeGUI.employeeList.add(new Employee(name.getText(), jobTitle.getText(), getStartDate(), Integer.parseInt(annualSalary.getText())));
                                String[] employeeArray = {name.getText(), jobTitle.getText(), startDate.getText(), annualSalary.getText()};
                                defaultTableModel.addRow(employeeArray);
                                resetTextFields();
                                output.setText("Neuer Mitarbeiter angelegt");
                        }
                });
                employeeFrame.add(addEmployeeButton);
        }
        private LocalDate getStartDate() {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return LocalDate.parse(this.startDate.getText(), dateTimeFormatter);
        }

        private LocalDate getDateFromString(String date) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return LocalDate.parse(date, dateTimeFormatter);
        }

        private void resetTextFields() {
                name.setText("");
                jobTitle.setText("");
                startDate.setText("");
                annualSalary.setText("");
                activeRow = -1;
        }

        private void log(Object output) {
                System.out.println(output);
        }
}

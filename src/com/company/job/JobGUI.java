package com.company.job;

import com.company.Main.DatapaperGUI;
import com.company.employee.Employee;
import com.company.employee.EmployeeGUI;

import javax.print.attribute.standard.JobName;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobGUI {

    private JTextField client;
    private JTextField postCode;
    private JTextField address;
    private JTextArea description;
    private JTextField startDate;
    private JTextField endDate;
    private JTable jobTable;
    private JFrame jobFrame;
    private DefaultTableModel defaultTableModel;
    private int activeRow = -1;
    private final JTextArea output;
    private JList employees;
    private DefaultListModel<String> defaultListModel;
    public static List<Job> jobList = new ArrayList<>();
    public static HashMap<Job, ArrayList<Employee>> employeeToJob = new HashMap<>();
    public static HashMap<Employee, ArrayList<Job>> jobToEmployee = new HashMap<>();

    private DatapaperGUI datapaperGUI;

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

        addRefreshEmployeesButton();

        JLabel clientLabel = new JLabel("Auftraggeber:");
        clientLabel.setBounds(50,30,150,20);
        jobFrame.add(clientLabel);
        client = new JTextField();
        client.setBounds(150,30,400,20);
        jobFrame.add(client);

        JLabel postCodeLabel = new JLabel("Postleitzahl:");
        postCodeLabel.setBounds(50,65,150,20);
        jobFrame.add(postCodeLabel);
        postCode = new JTextField();
        postCode.setBounds(150,65,400,20);
        jobFrame.add(postCode);

        JLabel addressLabel = new JLabel("Adresse:");
        addressLabel.setBounds(50,100,150,20);
        jobFrame.add(addressLabel);
        address = new JTextField();
        address.setBounds(150,100,400,20);
        jobFrame.add(address);

        JLabel descriptionLabel = new JLabel("Aufgaben:");
        descriptionLabel.setBounds(50,205,150,20);
        jobFrame.add(descriptionLabel);
        description = new JTextArea();
        description.setBounds(150,205,400,140);
        jobFrame.add(description);

        JLabel employeeLabel = new JLabel("Mitarbeiter:");
        employeeLabel.setBounds(50,355,150,20);
        jobFrame.add(employeeLabel);

        defaultListModel = new DefaultListModel<>();

        employees = new JList(defaultListModel);
        employees.setBounds(150,355,400,140);
        jobFrame.add(employees);

        JLabel startDateLabel = new JLabel("Startdatum:");
        startDateLabel.setBounds(50,135,150,20);
        jobFrame.add(startDateLabel);
        startDate = new JTextField();
        startDate.setBounds(150,135,400,20);
        jobFrame.add(startDate);

        addJobTableToFrame();

        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setBounds(580, 550,150,20);
        jobFrame.add(outputLabel);
        output = new JTextArea();
        output.setBounds(580,580,400,100);
        output.setEditable(false);
        jobFrame.add(output);

        addShowDatapaperButtonToFrame();


        JLabel endDateLabel = new JLabel("Enddatum:");
        endDateLabel.setBounds(50,170,150,20);
        jobFrame.add(endDateLabel);
        endDate = new JTextField();
        endDate.setBounds(150,170,400,20);
        jobFrame.add(endDate);

        addExportButtonToFrame();

        addImportButtonToFrame();

        jobFrame.setVisible(true);

        loadPossibleJobs();
    }

    private boolean checkInput() {
        output.setText("");
        boolean correct = true;
        if(client.getText().isBlank()) {
            output.append("Auftraggeber muss angegeben werden!\n");
            correct = false;
        }
        try{
            Integer.parseInt(postCode.getText());
        } catch (Exception e) {
            output.append("Postleitzahl muss eine Zahl sein.\n");
            correct = false;
        }
        if(postCode.getText().length() != 5) {
            output.append("Postleizahl muss 5 Zeichen lang sein.\n");
            correct = false;
        }
        if(postCode.getText().isBlank()) {
            output.append("Postleitzahl muss angegeben werden!\n");
        }
        if(address.getText().isBlank()) {
            output.append("Adresse muss angegeben werden!\n");
            correct = false;
        }
        if(description.getText().isBlank()) {
            output.append("Aufgaben müssen angegeben werden!\n");
            correct = false;
        }
        try {
            getStartDate();
        } catch (Exception e) {
            output.append("Startdatum nicht korrekt!\nIm Format: dd.MM.yyyy eingeben!\n");
            correct = false;
        }
        try {
            getEndDate();
        } catch (Exception e) {
            output.append("Enddatum nicht korrekt!\nIm Format: dd.MM.yyyy eingeben!\n");
            correct = false;
        }
        if(!getStartDate().isBefore(getEndDate())) {
            output.append("Startdatum muss vor Enddatum liegen!\n");
        }
        return correct;
    }

    private void addShowDatapaperButtonToFrame() {
        JButton showDatapaperButton = new JButton("Datenblatt anzeigen");
        showDatapaperButton.setBounds(580,700,400,50);
        showDatapaperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeRow == 0 || activeRow == -1)
                    return;

                Job job = JobGUI.jobList.get(activeRow-1);
                datapaperGUI = new DatapaperGUI(job);
            }
        });
        jobFrame.add(showDatapaperButton);
    }

    private boolean addEmployeesToJob(Job job) {
            boolean correct = true;
            ArrayList<Employee> employeeList =  new ArrayList<Employee>();
            for(Employee employee : EmployeeGUI.employeeList) {
                for(String name : (List<String>) employees.getSelectedValuesList()) {
                    if(name.equals(employee.getName())) {
                        if(employee.getHireDate().isBefore(job.getStartDate())) {
                            if(JobGUI.jobToEmployee.get(employee) != null) {
                                JobGUI.jobToEmployee.get(employee).remove(job);
                                for(Job jobs : JobGUI.jobToEmployee.get(employee)) {
                                    if(job != jobs) {
                                        if(isOverlapping(job.getStartDate(), job.getEndDate(),jobs.getStartDate(), jobs.getEndDate())) {
                                            correct = false;
                                            output.append("Startdaten von Jobs von: " + employee.getName() + " überschneiden sich. \n");
                                        }
                                    }
                                }
                                if(correct) {
                                    employeeList.add(employee);
                                    JobGUI.jobToEmployee.get(employee).add(job);
                                }
                            } else {
                                employeeList.add(employee);
                                if(JobGUI.jobToEmployee.get(employee) != null) {
                                    JobGUI.jobToEmployee.get(employee).add(job);
                                } else {
                                    ArrayList<Job> jobArrayList = new ArrayList<>();
                                    jobArrayList.add(job);
                                    JobGUI.jobToEmployee.put(employee, jobArrayList);
                                }
                            }
                        } else {
                            correct = false;
                            output.append("Startdatum vor Einstellungsdatum bei: " + employee.getName());
                        }
                    }
                }
            }
            JobGUI.employeeToJob.put(job, employeeList);
            return correct;
    }

    private boolean isOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    private void addEmployeesToJob(Job job, String[] employeeNames) {
        ArrayList<Employee> employeeList =  new ArrayList<Employee>();
        for(Employee employee : EmployeeGUI.employeeList) {
            for(String name : employeeNames) {
                if(name.equals(employee.getName())) {
                    employeeList.add(employee);
                }
            }
        }
        JobGUI.employeeToJob.put(job, employeeList);
    }

    private void loadPossibleJobs() {
        if(JobGUI.jobList.size() > 0) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            for (int i = 0; i < JobGUI.jobList.size(); i++) {
                Job job = JobGUI.jobList.get(i);
                defaultTableModel.addRow(new String[]{job.getClient(), job.getPostCode(), job.getAddress(), job.getStartDate().format(dateTimeFormatter), job.getEndDate().format(dateTimeFormatter), "Siehe Datenblatt"});
            }
        }
    }

    private void addRefreshEmployeesButton() {
        JButton refreshEmployeesButton = new JButton("Aktualisieren");
        refreshEmployeesButton.setBounds(10,385,130,20);
        refreshEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshEmployeesList();
            }
        });
        jobFrame.add(refreshEmployeesButton);
    }

    private void refreshEmployeesList() {
        if(EmployeeGUI.employeeList.size() < 1) {
            output.setText("Keine Mitarbeiter vorhanden!");
        } else {
            defaultListModel.clear();
            for (int i = 0; i < EmployeeGUI.employeeList.size(); i++) {
                defaultListModel.add(i, EmployeeGUI.employeeList.get(i).getName());
            }
        }
    }

    private void addImportButtonToFrame() {
        JButton importJobsButton = new JButton("Importieren");
        importJobsButton.setBounds(1000,550,400,75);
        importJobsButton.addActionListener(new ActionListener() {
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
                            if(!line.equals("Auftraggeber,Postleitzahl,Adresse,Startdatum,Enddatum,Aufgaben,Mitarbeiter")) {
                                String[] entries = line.split(",");
                                Job job = new Job(entries[0], entries[1], entries[2], entries[5].replace("|", "\n"), getDateFromString(entries[3]), getDateFromString(entries[4]));
                                JobGUI.jobList.add(job);
                                entries[5] = "Siehe Datenblatt";
                                if(!entries[6].equals("-")) {
                                    String[] employeeList = entries[6].split("\\|");
                                    addEmployeesToJob(job, employeeList);
                                }
                                entries[6] = null;
                                defaultTableModel.addRow(entries);
                            }
                            line = bufferedReader.readLine();
                        }
                        output.setText("Jobs importiert");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        jobFrame.add(importJobsButton);
    }

    private void addExportButtonToFrame() {
        JButton exportJobsButton = new JButton("Exportieren");
        exportJobsButton.setBounds(1000,650,400,75);
        exportJobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JobGUI.jobList.size() <1) {
                    output.setText("Keine Jobs zum Exportieren vorhanden!");
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
                    sb.append("Auftraggeber,Postleitzahl,Adresse,Startdatum,Enddatum,Aufgaben,Mitarbeiter\n");
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                    for (int i = 0; i<JobGUI.jobList.size(); i++) {
                        Job job = JobGUI.jobList.get(i);
                        sb.append(job.getClient()).append(",").append(job.getPostCode()).append(",").append(job.getAddress()).append(",").append(job.getStartDate().format(dateTimeFormatter)).append(",").append(job.getEndDate().format(dateTimeFormatter)).append(",").append(job.getDescription().replace("\n", "|")).append(",");
                        List<Employee> employees;
                        if((employees = JobGUI.employeeToJob.get(job)) != null) {
                            for (int j = 0; j <employees.size(); j++) {
                                sb.append(employees.get(j).getName());
                                if(j < employees.size()-1) {
                                    sb.append("|");
                                }
                            }
                        } else {
                            sb.append("-");
                        }
                        if(i < JobGUI.jobList.size()-1) {
                          sb.append("\n");
                        }
                    }
                    try {
                        PrintWriter printWriter = new PrintWriter(f);
                        printWriter.print(sb.toString());
                        printWriter.close();
                        output.setText("Jobs exportiert");
                    } catch (FileNotFoundException ex) {
                        output.setText("Fehler beim Export");
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        jobFrame.add(exportJobsButton);
    }

    private void addModifyJobButtonToFrame() {
        JButton modifyJobButton = new JButton("Ändern");
        modifyJobButton.setBounds(150, 670, 400, 75);
        modifyJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeRow != -1) {
                    if(!checkInput())
                        return;
                    Job job = JobGUI.jobList.get(activeRow-1);
                    job.setClient(client.getText());
                    job.setPostCode(postCode.getText());
                    job.setAddress(address.getText());
                    job.setStartDate(getStartDate());
                    job.setEndDate(getEndDate());
                    job.setDescription(description.getText());
                    String[] jobArray = {client.getText(), postCode.getText(), address.getText(), startDate.getText(), endDate.getText(), "Siehe Datenblatt"};
                    defaultTableModel.removeRow(activeRow);
                    defaultTableModel.insertRow(activeRow, jobArray);
                    addEmployeesToJob(job);
                    resetTextFields();
                    refreshEmployeesList();
                    output.setText("Job geändert");
                }
            }
        });
        jobFrame.add(modifyJobButton);
    }

    private void addDeleteJobButtonToFrame() {
        JButton deleteJobButton = new JButton("Löschen");
        deleteJobButton.setBounds(150,595,400,75);
        deleteJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeRow != -1) {
                    Job job = JobGUI.jobList.get(activeRow-1);
                    JobGUI.jobList.remove(job);
                    JobGUI.employeeToJob.remove(job);
                    defaultTableModel.removeRow(activeRow);
                    resetTextFields();
                    refreshEmployeesList();
                    output.setText("Job gelöscht");
                }


            }
        });
        jobFrame.add(deleteJobButton);
    }

    private void addJobTableToFrame() {
        String[] jobTableHeader={"Auftraggeber","Postleitzahl","Adresse","Startdatum","Enddatum", "Aufgaben/Mitarbeiter"};
        defaultTableModel = new DefaultTableModel(jobTableHeader, 0);
        defaultTableModel.addRow(jobTableHeader);
        jobTable = new JTable(defaultTableModel);
        jobTable.setBounds(580, 30,815, 500);
        jobTable.setShowGrid(true);
        jobTable.setDefaultEditor(Object.class, null);
        jobTable.setRowSelectionAllowed(true);
        jobTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jobTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                activeRow = jobTable.getSelectedRow();
                if(activeRow == 0 || activeRow == -1)
                    return;
                client.setText((String) jobTable.getValueAt(activeRow, 0));
                postCode.setText((String) jobTable.getValueAt(activeRow, 1));
                address.setText((String) jobTable.getValueAt(activeRow, 2));
                startDate.setText((String) jobTable.getValueAt(activeRow, 3));
                endDate.setText((String) jobTable.getValueAt(activeRow, 4));
                description.setText(JobGUI.jobList.get(activeRow-1).getDescription());
                refreshEmployeesList();
                if(JobGUI.employeeToJob.get(JobGUI.jobList.get(activeRow-1)) != null) {
                    ArrayList<Integer> indeces = new ArrayList<Integer>();
                    for (int i = 0; i < EmployeeGUI.employeeList.size(); i++) {
                        for (int j = 0; j < JobGUI.employeeToJob.get(JobGUI.jobList.get(activeRow-1)).size(); j++) {
                            if(JobGUI.employeeToJob.get(JobGUI.jobList.get(activeRow-1)).get(j).getName().equals(EmployeeGUI.employeeList.get(i).getName())) {
                                indeces.add(i);
                            }
                        }

                    }
                    if(indeces.size() != 0) {
                        employees.setSelectedIndices(indeces.stream().mapToInt(i-> i).toArray());
                    }
                }
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
        JButton addJobButton = new JButton("Hinzufügen");
        addJobButton.setBounds(150,520,400,75);
        addJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!checkInput())
                    return;
                Job job = new Job(client.getText(), postCode.getText(), address.getText(), description.getText(), getStartDate(), getEndDate());
                String[] jobArray = {client.getText(), postCode.getText(), address.getText(), startDate.getText(), endDate.getText(), "Siehe Datenblatt"};
                defaultTableModel.addRow(jobArray);
                addEmployeesToJob(job);
                JobGUI.jobList.add(job);
                resetTextFields();
                refreshEmployeesList();
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

    //public static void main(String[] args){new JobGUI();}

    private void log(Object output) {
        System.out.println(output);
    }
}

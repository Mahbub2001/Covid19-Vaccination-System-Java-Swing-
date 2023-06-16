package LoginPage;

import Covid.VaccinationManagementLandingPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AppointmentListPage extends JDialog {

    private JPanel appPanel;
    private JTable appointmentTable;
    private JButton completeButton;
    private JButton backBtn;
    private JLabel titleLabel;
    private DefaultTableModel tableModel;

    public AppointmentListPage(JFrame parent){
        super(parent);
        setTitle("Admin Login Panel");
        setContentPane(appPanel);
        setMinimumSize(new Dimension(1000,600));
        setModal(true);
        setLocationRelativeTo(parent);
        appPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("APPOINTMENT LIST");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(10,10,10,10));
        appPanel.add(titleLabel, BorderLayout.NORTH);


        String[] columnNames = {"Name", "Age", "NID Number or Birth Number", "Phone Number","password", "Center Name", "Appointment Date", "Vaccination","Gender"};
        String[][] appointmentData = getAppointmentData("registration.txt");
        tableModel = new DefaultTableModel(appointmentData, columnNames);
        appointmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        appPanel.setBorder(new EmptyBorder(10,10,10,10));
        appPanel.add(scrollPane, BorderLayout.CENTER);

        completeButton = new JButton("Complete");
        backBtn = new JButton("Back");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(backBtn);

        appPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(appPanel);
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String vaccinationStatus = tableModel.getValueAt(selectedRow, 7).toString();
                    if (vaccinationStatus.equals("No")) {
                        tableModel.setValueAt("Yes", selectedRow, 7);
                        updateVaccinationStatus(selectedRow, "Yes");
                    }
                }
            }
        });



        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                setVisible(false);
                VaccinationManagementLandingPage vc = new VaccinationManagementLandingPage(null);
            }
        });


        setVisible(true);
    }
    private String[][] getAppointmentData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder dataBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                dataBuilder.append(line).append("\n");
            }

            String[] appointments = dataBuilder.toString().split("\n");
            String[][] appointmentData = new String[appointments.length][];
            for (int i = 0; i < appointments.length; i++) {
                String[] appointmentFields = appointments[i].split(";");
                appointmentData[i] = new String[appointmentFields.length];
                for (int j = 0; j < appointmentFields.length; j++) {
                    appointmentData[i][j] = getValueByKey(appointmentFields[j].trim().split(": "), 1);
                }
            }
            return appointmentData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

    private String getValueByKey(String[] data, int index) {
        if (index < 0 || index >= data.length) {
            return "";
        }
        return data[index].trim();
    }

    private void updateVaccinationStatus(int rowIndex, String status) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registration.txt"))) {
            StringBuilder fileData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileData.append(line).append("\n");
            }
            String[] lines = fileData.toString().split("\n");

            if (rowIndex >= 0 && rowIndex < lines.length) {
                String[] fields = lines[rowIndex].split(";");
                if (fields.length > 7) {
                    fields[7] = "Vaccination: " + status;
                    lines[rowIndex] = String.join(";", fields);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("registration.txt"))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

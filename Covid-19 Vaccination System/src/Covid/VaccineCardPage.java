package Covid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class VaccineCardPage extends JDialog {

    private String searchUserInfo(String phoneNumber, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registration.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");

                String name = getValueByKey(userData, "Name");
                String age = getValueByKey(userData, "Age");
                String nidOrBirthNumber = getValueByKey(userData, "NID Number or Birth Number");
                String filePhoneNumber = getValueByKey(userData, "Phone Number");
                String filePassword = getValueByKey(userData, "Password");
                String centerName = getValueByKey(userData,"Center Name");
                String appointmentDate = getValueByKey(userData, "Appointment Date");
                String gender = getValueByKey(userData,"Gender");

                if (phoneNumber.equals(filePhoneNumber) && password.equals(filePassword)) {
                    StringBuilder userInfoBuilder = new StringBuilder();
                    userInfoBuilder.append("                                    Covid19 Vaccine Card :               \n");
                    userInfoBuilder.append("Name: ").append(name).append("\n");
                    userInfoBuilder.append("Age: ").append(age).append("\n");
                    userInfoBuilder.append("NID Number or Birth Number: ").append(nidOrBirthNumber).append("\n");
                    userInfoBuilder.append("Phone Number: ").append(filePhoneNumber).append("\n");
                    userInfoBuilder.append("Gender: ").append(gender).append("\n");
                    userInfoBuilder.append("Center Name: ").append(centerName).append("\n");
                    userInfoBuilder.append("Appointment Date: ").append(appointmentDate).append("\n");
                    userInfoBuilder.append("Please take to your center. Otherwise you will not able to take vaccine").append("\n");

                    return userInfoBuilder.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "User not found or incorrect credentials.";
    }

    private String getValueByKey(String[] userData, String key) {
        for (String data : userData) {
            String[] parts = data.split(": ");
            if (parts.length == 2 && parts[0].trim().equals(key)) {
                return parts[1].trim();
            }
        }
        return "";
    }

    private String generateCertificate(String userInfo) {
        String name = getValueByKey(userInfo.split("\n"), "Name");
        String certificateFileName = name + "_vaccine_card.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(certificateFileName))) {
            writer.write(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return certificateFileName;
    }

    private JPanel cdPanel;
    private JTextField phoneNumberField;
    private JPasswordField passwordField;
    private JButton generateButton;
    private JButton backbtn;
    private JButton searchButton;
    private JTextArea userInfoArea;

    public VaccineCardPage(JFrame parent){
        super(parent);
        setTitle("Vaccine Card Panel");
        setContentPane(cdPanel);
        setMinimumSize(new Dimension(800,600));
        setModal(true);
        setLocationRelativeTo(parent);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText();
                String password = new String(passwordField.getPassword());

                String userInfo = searchUserInfo(phoneNumber, password);
                userInfoArea.setText(userInfo);
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInfo = userInfoArea.getText();
                String certificateFileName = generateCertificate(userInfo);
                JOptionPane.showMessageDialog(VaccineCardPage.this, "Certificate generated successfully.\nFilename: " + certificateFileName);
            }
        });
        backbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                VaccinationManagementLandingPage v = new VaccinationManagementLandingPage(null);
            }
        });

        setVisible(true);

    }

}

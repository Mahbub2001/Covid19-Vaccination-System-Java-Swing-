package Covid;

import Util.UserDataRetriever;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class FileUserDataRetriever implements UserDataRetriever {
    @Override
    public String getUserInfo(String phoneNumber, String password) {
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
                String vcc = getValueByKey(userData,"Vaccination");


                if (phoneNumber.equals(filePhoneNumber) && password.equals(filePassword)) {
                    if(vcc.equals("No")){
                        StringBuilder userInfoBuilder = new StringBuilder();
                        userInfoBuilder.append("You don't give first dose yet");
                        return userInfoBuilder.toString();
                    }
                    else{
                        StringBuilder userInfoBuilder = new StringBuilder();
                        userInfoBuilder.append("                                    Covid19 Vaccination Certificate :               \n");
                        userInfoBuilder.append("Name: ").append(name).append("\n");
                        userInfoBuilder.append("Age: ").append(age).append("\n");
                        userInfoBuilder.append("NID Number or Birth Number: ").append(nidOrBirthNumber).append("\n");
                        userInfoBuilder.append("Phone Number: ").append(filePhoneNumber).append("\n");
                        userInfoBuilder.append("Gender: ").append(gender).append("\n");
                        userInfoBuilder.append("Center Name: ").append(centerName).append("\n");
                        userInfoBuilder.append("First Dose Date: ").append(appointmentDate).append("(" + vcc + ")").append("\n");
                        return userInfoBuilder.toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
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

}
public class VaccineCertificate extends JDialog  {

    private JPanel cerPanel;
    private JTextField phoneNumberField;
    private JPasswordField passwordField;
    private JTextArea certificateArea;
    private JButton generateButton;
    private  JButton searchButton;
    private JButton backbtn;

    public VaccineCertificate(UserDataRetriever userDataRetriever,JFrame parent){
        super(parent);
        setTitle("Vaccine Certificate  Panel");
        setContentPane(cerPanel);
        setMinimumSize(new Dimension(800,600));
        setModal(true);
        setLocationRelativeTo(parent);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText();
                String password = new String(passwordField.getPassword());

                String certificateInfo = generateCertificate(phoneNumber, password, userDataRetriever);
                certificateArea.setText(certificateInfo);
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String certificateInfo = certificateArea.getText();
                String certificateFileName = saveCertificateToFile(certificateInfo);
                JOptionPane.showMessageDialog(VaccineCertificate.this, "Certificate generated successfully.\nFilename: " + certificateFileName);
            }
        });
        backbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                setVisible(false);
                VaccinationManagementLandingPage vl = new VaccinationManagementLandingPage(null);
            }
        });

        setVisible(true);
    }

    private String generateCertificate(String phoneNumber, String password, UserDataRetriever userDataRetriever) {
        String userInfo = userDataRetriever.getUserInfo(phoneNumber, password);

        if (!userInfo.isEmpty()) {
            StringBuilder certificateBuilder = new StringBuilder();
//            certificateBuilder.append("                                    Covid19 Vaccination Certificate :               \n");
            certificateBuilder.append(userInfo);
            return certificateBuilder.toString();
        }

        return "User not found or incorrect credentials.";
    }

    private String saveCertificateToFile(String certificateInfo) {
        String name = getValueByKey(certificateInfo.split("\n"), "Name");
        String certificateFileName = name + "_Certificate.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(certificateFileName))) {
            writer.write(certificateInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return certificateFileName;
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

//    public static void main(String[] args) {
//        UserDataRetriever userDataRetriever = new FileUserDataRetriever();
//        VaccineCertificate app = new VaccineCertificate(userDataRetriever,null);
//        app.setVisible(true);
//    }
}

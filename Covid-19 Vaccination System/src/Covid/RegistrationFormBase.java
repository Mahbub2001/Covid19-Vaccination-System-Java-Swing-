package Covid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

class Appointment {
    private String name;
    private int age;
    private String phone;
    private String centerName;
    private Date appointmentDate;
    private String gender;
    private String password;
    private String nID_Number_or_Birth_Number;


    public Appointment(String name,String password,String nid, int age, String phone, String centerName, Date appointmentDate, String gender) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.nID_Number_or_Birth_Number=nid;
        this.phone = phone;
        this.centerName = centerName;
        this.appointmentDate = appointmentDate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getNID_Number_or_Birth_Number() {
        return nID_Number_or_Birth_Number;
    }

    public String getPhone() {
        return phone;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getPassword() {
        return password;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getGender() {
        return gender;
    }
}

class ScheduledAppointment extends Appointment {
    private String vaccinationStatus;

    public ScheduledAppointment(String name,String password,String nid, int age, String phone, String centerName, Date appointmentDate, String gender, String vaccinationStatus) {
        super(name,password,nid, age, phone, centerName, appointmentDate, gender);
        this.vaccinationStatus = vaccinationStatus;
    }

    public String getVaccinationStatus() {
        return vaccinationStatus;
    }
}

public class RegistrationFormBase extends JDialog {
    private JTextField nameTextField;
    private JTextField ageTextField;
    private JTextField nidTextField;
    private JTextField phoneTextField;
    private JTextField centerTextField;
    private JRadioButton MALERadioButton;
    private JPasswordField passwordTextField;
    private JRadioButton FEMALERadioButton;
    private JRadioButton OTHERRadioButton;
    private JButton registerButton;
    private JButton backButton;
    private JTextField appointmentDateLabel;
    private JPanel registerPanel;

    private String gender = "";

    public RegistrationFormBase(JFrame parent) {
        super(parent);
        setTitle("Register Panel");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(800, 600));
        setModal(true);
        setLocationRelativeTo(parent);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(MALERadioButton);
        buttonGroup.add(FEMALERadioButton);
        buttonGroup.add(OTHERRadioButton);

        MALERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MALERadioButton.isSelected()) {
                    gender = "MALE";
                }
            }
        });

        FEMALERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FEMALERadioButton.isSelected()) {
                    gender = "FEMALE";
                }
            }
        });

        OTHERRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OTHERRadioButton.isSelected()) {
                    gender = "OTHER";
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                int age = Integer.parseInt(ageTextField.getText());
                String nidNumberOrBirthNumber = nidTextField.getText();
                String password = passwordTextField.getText();
                String phoneNumber = phoneTextField.getText();
                String centerName = centerTextField.getText();

                Date appointmentDate = new Date();
                appointmentDate.setDate(appointmentDate.getDate() + 10);

                // Check if there are already 20 appointments on the appointment date
                int numberOfAppointments = getNumberOfAppointments(appointmentDate);
                while (numberOfAppointments >= 20) {
                    appointmentDate.setDate(appointmentDate.getDate() + 1);
                    numberOfAppointments = getNumberOfAppointments(appointmentDate);
                }

                String vaccinationStatus = "No";

                Appointment appointment = new ScheduledAppointment(name,password,nidNumberOrBirthNumber, age, phoneNumber, centerName, appointmentDate, gender, vaccinationStatus);

                saveAppointmentData(appointment);

                appointmentDateLabel.setText("Appointment Date: " + appointmentDate);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                VaccinationManagementLandingPage v = new VaccinationManagementLandingPage(null);
            }
        });

        setVisible(true);
    }

    protected int getNumberOfAppointments(Date date) {
        int count = 0;
        File file = new File("registration.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Scanner scanner = new Scanner(file);
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Appointment Date: ")) {
                    String dateString = line.substring("Appointment Date: ".length());
                    Date appointmentDate = dateFormat.parse(dateString);
                    if (appointmentDate.getDate() == date.getDate() &&
                            appointmentDate.getMonth() == date.getMonth() &&
                            appointmentDate.getYear() == date.getYear()) {
                        count++;
                    }
                }
            }
            scanner.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void saveAppointmentData(Appointment appointment) {
        try {
            File file = new File("registration.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("Name: " + appointment.getName() + ";");
            fileWriter.write("Age: " + appointment.getAge() + ";");
            fileWriter.write("NID Number or Birth Number: " + appointment.getNID_Number_or_Birth_Number() + ";");
            fileWriter.write("Phone Number: " + appointment.getPhone() + ";");
            fileWriter.write("Password : " + appointment.getPassword() + ";");
            fileWriter.write("Center Name: " + appointment.getCenterName() + ";");
            fileWriter.write("Appointment Date: " + appointment.getAppointmentDate() + ";");
            fileWriter.write("Vaccination: " + ((ScheduledAppointment) appointment).getVaccinationStatus() + ";");
            fileWriter.write("Gender: " + appointment.getGender() + ";");
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

package LoginPage;

import Covid.VaccinationManagementLandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginPage extends JDialog {
    private boolean authenticate(String username, String password) {
        String adminUsername = "admin";
        String adminPassword = "admin123";

        return username.equals(adminUsername) && password.equals(adminPassword);
    }
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backbtn;

    public AdminLoginPage(JFrame parent) {
        super(parent);
        setTitle("Admin Login Panel");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(800,600));
        setModal(true);
        setLocationRelativeTo(parent);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (authenticate(username, password)) {
                        JOptionPane.showMessageDialog(AdminLoginPage.this, "Login successful!");
                        AppointmentListPage appp = new AppointmentListPage(null);
                        dispose();
                    } else {
                        throw new Exception("Invalid credentials. Please try again.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminLoginPage.this, ex.getMessage());
                }
            }
        });

        backbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                VaccinationManagementLandingPage vc  = new VaccinationManagementLandingPage(null);
            }
        });
        setVisible(true);
    }

}

package Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class VaccinationManagementBase extends JDialog {
    private JPanel vp;
    private JButton vaccineCardButton;
    private JButton registerButton;
    private JButton vaccineCertificateButton;
    private JButton adminLoginButton;
    private JButton searchButton;

    public VaccinationManagementBase(JFrame parent) {
        super(parent);
        setTitle("Home Panel");
        setContentPane(vp);
        setMinimumSize(new Dimension(800, 600));
        setModal(true);
        setLocationRelativeTo(parent);

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLoginPage();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
            }
        });

        vaccineCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openVaccineCardPage();
            }
        });

        vaccineCertificateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openVaccineCertificate();
            }
        });

        setVisible(true);
    }

    protected abstract void openAdminLoginPage();

    protected abstract void openRegistrationForm();

    protected abstract void openVaccineCardPage();

    protected abstract void openVaccineCertificate();

}

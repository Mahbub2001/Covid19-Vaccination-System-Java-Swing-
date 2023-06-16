package Covid;

import LoginPage.AdminLoginPage;
import Util.VaccinationManagementBase;

import javax.swing.*;

public class VaccinationManagementLandingPage extends VaccinationManagementBase {

    public VaccinationManagementLandingPage(JFrame parent) {
        super(parent);
    }

    @Override
    protected void openAdminLoginPage() {
        AdminLoginPage ad = new AdminLoginPage(null);
        dispose();
    }

    @Override
    protected void openRegistrationForm() {
        RegistrationFormBase r = new RegistrationFormBase(null);
        dispose();
    }

    @Override
    protected void openVaccineCardPage() {
        VaccineCardPage vc = new VaccineCardPage(null);
        dispose();
    }

    @Override
    protected void openVaccineCertificate() {
        VaccineCertificate vaccineCertificate = new VaccineCertificate(new FileUserDataRetriever(), null);
        vaccineCertificate.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        VaccinationManagementLandingPage vc = new VaccinationManagementLandingPage(null);
    }
}

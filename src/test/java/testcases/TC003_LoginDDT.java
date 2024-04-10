package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Aura_ApptConfirmPage;
import pageObjects.Aura_HomePage;
import pageObjects.Aura_LoginPage;
import pageObjects.Aura_ScheduleAppointmentPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "master")
    void verifyDDTLogin(String email, String password, String exp){
        logger.info("*** Starting TC003_LoginDDT ***");
        try {
            //HomePage
            Aura_HomePage hp = new Aura_HomePage(driver);
            Assert.assertEquals(hp.validateHomePage(), true, "Failed to Navigate to HomePage");
            hp.clickMakeAppt();
            //LoginPage
            Aura_LoginPage lp = new Aura_LoginPage(driver);
            lp.setUsername(email);
            lp.setPassword(password);
            lp.clickLogin();
            //SchedulePage
            Aura_ScheduleAppointmentPage sched = new Aura_ScheduleAppointmentPage(driver);
            boolean targetPage = sched.validateLogin();
            //Appointment confirmation page
            Aura_ApptConfirmPage cp = new Aura_ApptConfirmPage(driver);
            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    cp.clickToggleBtn();
                    cp.clickLogoutBtn();
                    Assert.assertTrue(true);
                } else {
                    logger.error("Bug");
                    Assert.assertTrue(false);
                }
            }
            if (exp.equalsIgnoreCase("InvaliD")) {
                if (targetPage == true) {
                    cp.clickToggleBtn();
                    cp.clickLogoutBtn();
                    logger.error("Bug");
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
            }
        } catch (Exception e){
            Assert.fail();
        }
        logger.info("*** Finished TC003_LoginDDT ***");
    }
}

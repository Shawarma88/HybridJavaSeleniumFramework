package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Aura_HomePage;
import pageObjects.Aura_LoginPage;
import pageObjects.Aura_ScheduleAppointmentPage;
import testBase.BaseClass;

public class TC001_LoginValidationTest extends BaseClass {
    @Test(groups = {"sanity", "master"})
    void test_Validate_LoginTest(){
        try {
            logger.info("****** Starting TC001_LoginValidationTest *******");
            Aura_HomePage hp = new Aura_HomePage(driver);
            Assert.assertEquals(hp.validateHomePage(), true, "Failed to Navigate to HomePage");
            if(hp.validateHomePage())
                logger.info("Successfully navigated to Aura Homepage");
            else
                logger.error("Failed during opening to Aura Homepage");
            hp.clickMakeAppt();
            logger.info("Clicked on the Make Appointment button");

            Aura_LoginPage lp = new Aura_LoginPage(driver);
            if(lp.validateLoginPage())
                logger.info("Successfully navigated to Aura Login Page");
            else
                logger.error("Failed during opening to Aura Login Page");
            logger.info("Adding Login credentials");
            lp.setUsername(rb.getString("username"));
            lp.setPassword(rb.getString("password"));
            logger.info("Clicking on Login button");
            lp.clickLogin();

            Aura_ScheduleAppointmentPage sched = new Aura_ScheduleAppointmentPage(driver);
            Assert.assertEquals(sched.validateLogin(), true, "Failed while validating Login");
            if(sched.validateLogin())
                logger.info("Successfully navigated to Aura Schedule Appointment Page");
            else
                logger.error("Failed during opening to Aura Schedule Appointment Page");
        }catch(Exception e){
            Assert.fail("TC001_LoginValidationTest failed due to "+e);
        }
        logger.info("****** Finished TC001_LoginValidationTest *******");
    }
}

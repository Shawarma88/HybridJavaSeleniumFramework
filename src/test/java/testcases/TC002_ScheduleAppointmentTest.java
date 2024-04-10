package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Aura_ApptConfirmPage;
import pageObjects.Aura_HomePage;
import pageObjects.Aura_LoginPage;
import pageObjects.Aura_ScheduleAppointmentPage;
import testBase.BaseClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class TC002_ScheduleAppointmentTest extends BaseClass {
    @Test(groups = {"regression", "master"})
    public void test_Validate_ScheduleAppointment() {
        try {
            logger.info("*** Starting TC002_ScheduleAppointmentTest ***");
            Aura_HomePage hp = new Aura_HomePage(driver);
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
            sched.selectFacilityDropdown(rb.getString("facility"));
            logger.info("Facility added for the patient - "+rb.getString("facility"));
            sched.checkAdmissionCB();
            logger.info("Checked the Re-Admission checkbox");
            sched.selectHealthCare();
            logger.info("Health care selected");
            //Visit date
            Date date = new Date();
            SimpleDateFormat dateFormatted = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormatted.format(date);
            sched.setVisitDate(strDate);
            logger.info("Visit date specified - "+strDate);
            //Random comments
            String RandomComments = randomComments();
            sched.setCommentArea(RandomComments);
            logger.info("Comments specified - "+RandomComments);
            // Button
            sched.clickBookAppt();
            logger.info("Clicked on Book Appointment button");

            Aura_ApptConfirmPage confirm = new Aura_ApptConfirmPage(driver);
            Assert.assertEquals(confirm.verifyConfirmation(), true, "Failed while validating the appointment creation");
            if(confirm.verifyConfirmation()){
                logger.info("Successfully created an appointment");
            }else{
                logger.error("Failed while creating an appointment");
            }
        } catch (Exception e) {
            Assert.fail();
        }
        logger.info("*** Finished TC002_ScheduleAppointmentTest ***");
    }
}

package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Aura_ScheduleAppointmentPage extends BasePage{
    public Aura_ScheduleAppointmentPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//h2[text()='Make Appointment']")
    WebElement makeAppointmentValidate;
    @FindBy(xpath = "//select[@id='combo_facility']")
    WebElement facilityDropDown;
    @FindBy(xpath = "//label[@class='checkbox-inline']")
    WebElement readmissionCheckbox;
    @FindBy(xpath="//input[@id='radio_program_medicare']")
    WebElement healthcareProgram;
    @FindBy(xpath="//input[@id='txt_visit_date']")
    WebElement visitDate;
    @FindBy(xpath="//textarea[@id='txt_comment']")
    WebElement commentArea;
    @FindBy(xpath = "//button[@id='btn-book-appointment']")
    WebElement btnBookAppointment;

    public boolean validateLogin(){
        try{
            return (makeAppointmentValidate.isDisplayed());
        }catch(Exception e){
            return(false);
        }
    }
    public void selectFacilityDropdown(String facilityValue){
        Select sel = new Select(facilityDropDown);
        sel.selectByValue(facilityValue);
    }
    public void checkAdmissionCB(){
        if(!readmissionCheckbox.isEnabled()){
            readmissionCheckbox.click();
        }
    }
    public void selectHealthCare(){
        if(!healthcareProgram.isEnabled()){
            healthcareProgram.click();
        }
    }
    public void setVisitDate(String visDate){
        visitDate.sendKeys(visDate);
    }
    public void setCommentArea(String comments){
        commentArea.sendKeys(comments);
    }
    public void clickBookAppt(){
        btnBookAppointment.click();
    }

}

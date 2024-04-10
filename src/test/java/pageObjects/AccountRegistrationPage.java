package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{
    //Constructor
    public AccountRegistrationPage(WebDriver driver){
        super(driver);
    }
    //Elements
    @FindBy(id = "input-firstname")
    WebElement firstName;
    @FindBy(id = "input-lastname")
    WebElement lastName;
    @FindBy(id = "input-email")
    WebElement email;
    @FindBy(id = "input-password")
    WebElement password;
    @FindBy(xpath = "//label[text()='Yes']")
    WebElement subscribeRB;
    @FindBy(xpath = "//input[@name='agree']")
    WebElement agreeCB;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitBtn;

    //Action methods
    public void setFirstName(String FirstName){
        firstName.sendKeys(FirstName);
    }
    public void setLastName(String LastName){
        lastName.sendKeys(LastName);
    }
    public void setEmail(String Email){
        email.sendKeys(Email);
    }
    public void setPassword(String Password){
        password.sendKeys(Password);
    }
    public void clickRadioBtnSubscribe(){
        if(!subscribeRB.isSelected()){
            subscribeRB.click();
        }
    }
    public void clickCheckBoxAgree(){
        if(!agreeCB.isSelected()){
            agreeCB.click();
        }
    }
    public void clickSubmitBtn(){
        submitBtn.click();
    }

}

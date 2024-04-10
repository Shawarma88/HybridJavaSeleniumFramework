package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Aura_ApptConfirmPage extends BasePage{
    public Aura_ApptConfirmPage(WebDriver driver){
        super(driver);
    }
    @FindBy(xpath = "//h2[text()='Appointment Confirmation']")
    WebElement confirmationMessage;
    @FindBy(xpath="//a[@id='menu-toggle']")
    WebElement toggleLink;
    @FindBy(xpath = "//a[text()='Logout']")
    WebElement logoutLink;
    public boolean verifyConfirmation(){
        try{
            return(confirmationMessage.isDisplayed());
        }catch (Exception e){
            return false;
        }
    }
    public void clickToggleBtn(){
        toggleLink.click();
    }
    public void clickLogoutBtn(){
        logoutLink.click();
    }
}

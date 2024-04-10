package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Aura_HomePage extends BasePage{
    public Aura_HomePage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//a[@id='btn-make-appointment']")
    WebElement btn_MakeAppt;

    public void clickMakeAppt(){
        btn_MakeAppt.click();
    }
    public boolean validateHomePage(){
        boolean status = btn_MakeAppt.isDisplayed();
        return status;
    }
}

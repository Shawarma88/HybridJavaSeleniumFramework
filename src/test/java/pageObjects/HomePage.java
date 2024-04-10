package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
    //constructor
    public HomePage(WebDriver driver){
        super(driver);
    }
    //Elements
    @FindBy(xpath = "//span[text()='My Account']")
    WebElement lnkAccount;
    @FindBy(xpath = "//a[@class='dropdown-item' and text()='Register']")
    WebElement lnkRegister;
    //Action methods
    public void clickMyAccount(){
        lnkAccount.click();
    }
    public void clickRegister(){
        lnkRegister.click();
    }
}

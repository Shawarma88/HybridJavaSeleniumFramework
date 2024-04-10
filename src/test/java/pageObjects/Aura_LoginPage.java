package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Aura_LoginPage extends BasePage{
    public Aura_LoginPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "txt-username")
    WebElement Username;
    @FindBy(id = "txt-password")
    WebElement Password;
    @FindBy(xpath = "//button[@id='btn-login']")
    WebElement btn_Login;

    public void setUsername(String Uname){
        Username.sendKeys(Uname);
    }
    public void setPassword(String pwd){
        Password.sendKeys(pwd);
    }
    public void clickLogin(){
        btn_Login.click();
    }
    public boolean validateLoginPage(){
        boolean valLogin = btn_Login.isDisplayed();
        return valLogin;
    }
}

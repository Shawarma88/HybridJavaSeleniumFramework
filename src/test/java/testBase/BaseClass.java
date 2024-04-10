package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

public class BaseClass {
    public static WebDriver driver;
    public Logger logger;
    public ResourceBundle rb;
    @Parameters({"os","browser"})
    @BeforeClass(groups = {"sanity", "regression", "master"})
    public void setup(String os, String engine){
        logger = LogManager.getLogger(this.getClass());
        rb = ResourceBundle.getBundle("config"); //To load the config.properties file
        //WebDriverManager.chromedriver().setup();
        // To remove the chrome running in automated info in Google Chrome
//        ChromeOptions opt = new ChromeOptions();
//        opt.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        if(engine.equals("chrome")){
            driver = new ChromeDriver();
        }else if(engine.equals("edge")){
            driver = new EdgeDriver();
        }else{
            driver = new ChromeDriver();
        }
        driver.get(rb.getString("url"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }
    @AfterClass(groups = {"sanity", "regression", "master"})
    public void teardown(){
        driver.quit();
    }
    public String randomComments(){
        String randomAlp = RandomStringUtils.randomAlphabetic(10);
        String randNum = RandomStringUtils.randomNumeric(10);
        return (randomAlp+randNum);
    }
    public String captureScreen(String testName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir")+"\\screenshots\\"+ testName +"_"+timestamp+".png";
        File targetFile = new File(destination);
        //source.renameTo(targetFile);
        FileUtils.copyFile(source, targetFile);
        return destination;
    }
}

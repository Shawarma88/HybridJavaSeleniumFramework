package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;

public class TestListeners implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extendReports;
    public ExtentTest extentTest;
     public void onStart(ITestContext testContext){
        sparkReporter = new ExtentSparkReporter(".\\tesdata\\reportTest.html");
        extendReports.attachReporter(sparkReporter);
        extendReports.setSystemInfo("Computername", "GOkul da");
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extendReports.setSystemInfo("os", os);
     }
}

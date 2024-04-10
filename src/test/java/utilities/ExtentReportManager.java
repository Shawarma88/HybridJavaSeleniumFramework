package utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
//import java.net.URL;

//Extent report 5.x...//version

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		repName = "Test-Report-" + timeStamp + ".html";
		//creates a basic UI of the report
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);// specify location of the report
		sparkReporter.config().setDocumentTitle("AURA Automation Report"); // Title of report
		sparkReporter.config().setReportName("AURA Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		//Populating test run information
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "AURA_MEDICARE");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Patients");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		// to get the current info of the tests - use ItestContext
		String os = testContext.getCurrentXmlTest().getParameter("os");
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Operating System", os);
		extent.setSystemInfo("Browser", browser);
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()){
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		//adding group name to the test
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, "Test Passed - " +result.getName());
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		//adding group name to the test
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, "Test failed - "+ result.getName());
		test.log(Status.INFO, result.getThrowable().getMessage());
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.log(Status.INFO, "Screenshot path = "+imgPath);
			test.addScreenCaptureFromPath(imgPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, "Test Skipped - " +result.getName());
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
		//Inorder to open the report after the script execution
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\" + repName;
		File reportPath = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(reportPath.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * try { URL url = new
		 * URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		 * 
		 * // Create the email message 
		 * ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.googlemail.com"); 
		 * email.setSmtpPort(465);
		 * email.setAuthenticator(new DefaultAuthenticator("a.gokul9826@gmail.com","password"));
		 * email.setSSLOnConnect(true);
		 * email.setFrom("a.gokul9826@gmail.com"); //Sender
		 * email.setSubject("Test Results");
		 * email.setMsg("Please find Attached Report....");
		 * email.addTo("shawarmacodewith@gmail.com"); //Receiver
		 * email.attach(url, "extent report", "please check report..."); 
		 * email.send(); // send the email 
		 * }
		 * catch(Exception e) { e.printStackTrace(); }
		 */
	}

}

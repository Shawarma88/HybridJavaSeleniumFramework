package utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
//import java.net.URL;

//Extent report 5.x...//version

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;
	String timeStamp;

	public void onStart(ITestContext testContext) {
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
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
		try {
			sendEmail("shawarmacodewith@gmail.com","wrxn aovy qydm lwda", "a.gokul9826@gmail.com" );
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendEmail(String userName, String password, String recipient) throws MessagingException, IOException {
		// Add the Properties
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable","true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		//Create session
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		// Create message
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		message.setSubject("Execution report of Cura Automation testcases");
		message.setDescription("Cura Automation run report on "+timeStamp);
		String filepath = ".\\reports\\" + repName;
		String filename = repName;
		MimeMultipart multipart = new MimeMultipart();
		//Bodypart-1 attachments
		MimeBodyPart attachment = new MimeBodyPart();
		attachment.attachFile(filepath);
		attachment.setFileName(filename);
		//Bodypart-2 Text
		MimeBodyPart text = new MimeBodyPart();
		text.setText("Attached is the execution report of Cura Automation on "+timeStamp);
		//Add to the multipart
		multipart.addBodyPart(attachment);
		multipart.addBodyPart(text);
		// set content
		message.setContent(multipart);
		//send email
		Transport.send(message);
		System.out.println("Email Successfully");
	}

}

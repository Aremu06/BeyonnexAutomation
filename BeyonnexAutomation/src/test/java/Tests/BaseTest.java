package Tests;

import TestData.ConfigData;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;
    public static ExtentReports report ;
    public static ExtentTest logger;

    public void setup(String BrowserName) throws MalformedURLException {
        ExtentHtmlReporter extent = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + "\\src\\test\\java\\TestReports\\Beyonnex-Automation-Task On "+ GetCurrentTime() + " .html"));
        report = new ExtentReports();
        report.attachReporter(extent);

        if (BrowserName.equalsIgnoreCase("CHROME") || BrowserName.equalsIgnoreCase("")) {
            ChromeOptions options = new ChromeOptions();
            options.setCapability(CapabilityType.BROWSER_NAME,"chrome");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            URL url = new URL("http://chrome:4444/wd/hub");
            driver = new RemoteWebDriver(url, options);
        }

        else if (BrowserName.equalsIgnoreCase("FIREFOX")){

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        else if (BrowserName.equalsIgnoreCase("Edge"))
        {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        }

        else if (BrowserName.equalsIgnoreCase("Opera"))
        {
            WebDriverManager.operadriver().setup();
            driver = new EdgeDriver();

        }
        else if (BrowserName.equalsIgnoreCase("Safari"))
        {
           //WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();

        }

        else{

            System.out.println("Browser Name is not correct");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(ConfigData.URL);
        logger = report.createTest("Open weather shopper URL ");
        logger.info("Successfully Opened URL on Browser " );
    }


    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            logger.fail(result.getThrowable());
            String temp=  Screenshot(driver);
            logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED " , ExtentColor.GREEN));
            String temp=  Screenshot(driver);
            logger.pass(result.getMethod().getMethodName(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
        }
        else {
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            logger.skip(result.getThrowable());
        }
        report.flush();
    }

    @AfterSuite
    public void teardown()
    {
        driver.quit();
    }


    // Method to get current date & time to help with naming the Extent report file & screenshots
    public static String GetCurrentTime () {
        DateFormat format = new SimpleDateFormat("dd MMMM YYYY hh.mm.ss");
        Date date=new Date();
        return  format.format(date);
    }


    // Take screenshot when test is failed then attached it to Extent report
    public static String Screenshot(WebDriver driver) {

        TakesScreenshot ts=(TakesScreenshot) driver;

        File src=ts.getScreenshotAs(OutputType.FILE);

        String path=System.getProperty("user.dir")+"\\src\\test\\java\\TestReports\\"+GetCurrentTime()+".jpg";

        File destination=new File(path);

        try
        {
            FileUtils.copyFile(src, destination);
        } catch (IOException e)
        {
            System.out.println("Capture Failed "+e.getMessage());
        }

        return path;
    }
}

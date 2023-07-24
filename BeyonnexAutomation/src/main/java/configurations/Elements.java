package configurations;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.IOException;
import java.time.Duration;

public class Elements {

    private WebDriver driver;
    FluentWait wait;


    public Elements(WebDriver driver) throws IOException {
        this.driver = driver;

    }
    public void waitForElementTobeVisible(By locator) {
        try {
            wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Failed to find element with locator " + locator.toString());
        }
    }

    public void click(By locator) {
        waitForElementTobeVisible(locator);
        driver.findElement(locator).click();
        System.out.println("Successfully clicked on element with locator '" + locator.toString() + "'");
    }

    public void write(By locator, String text) {
        waitForElementTobeVisible(locator);
        driver.findElement(locator).sendKeys(text);
        System.out.println("Successfully wrote text '" + text + "' in element with locator '" + locator.toString() + "'");
    }

    public String getElementText(By locator) {
        waitForElementTobeVisible(locator);
        String text = driver.findElement(locator).getText();
        System.out.println("Successfully got text '" + text + "' from element with locator '" + locator.toString() + "'");
        return text;
    }


    public void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }


    public void writeCharByChar(By locator, String value)
    {
        waitForElementTobeVisible(locator);
        for (int i = 0; i < value.length(); i++){
            char c = value.charAt(i);
            String s = new StringBuilder().append(c).toString();
            driver.findElement(locator).sendKeys(s);
        }
    }

}

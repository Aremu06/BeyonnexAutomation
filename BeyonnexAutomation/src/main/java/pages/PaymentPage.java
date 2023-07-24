package pages;

import configurations.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class PaymentPage {

    private WebDriver driver;
    private Elements elements;

    private By emailField = By.id("email");
    private By cardNumberField = By.id("card_number");
    private By expireField = By.id("cc-exp");
    private By cvvField = By.id("cc-csc");
    private By zipField = By.id("billing-zip");
   private By payButton = By.className("iconTick");

    public PaymentPage(WebDriver driver) throws IOException {
        this.driver = driver;
        elements = new Elements(driver);
    }


    public void enterEmail(String email) {
        elements.switchToFrame(0);
        elements.writeCharByChar(emailField, email);
    }

    public void enterCarNumber(String card) {
        elements.writeCharByChar(cardNumberField, card);
    }

    public void enterMonthYear(String expiry) {
        elements.writeCharByChar(expireField,expiry);
    }

    public void enterCvc(String cvvNumber){elements.write(cvvField,cvvNumber);}

    public void enterBillZipCode(String zip){
        if(driver.findElement(zipField).isDisplayed())
            elements.writeCharByChar(zipField,zip);
        else
            System.out.println("Zip Code Not Displayed");

    }

    public void clickPay(){
        elements.click(payButton);
        driver.switchTo().parentFrame();
    }

private By headerText=By.cssSelector(".text-justify");
    public String getHeaderText(){
        return elements.getElementText(headerText);

    }

}

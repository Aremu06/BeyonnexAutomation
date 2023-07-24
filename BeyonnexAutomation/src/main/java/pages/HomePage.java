package pages;

import configurations.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.IOException;

public class HomePage {

    private WebDriver driver;
    private Elements elements;
    public static String shopItem ="";
    public HomePage(WebDriver driver) throws AWTException, IOException {
        this.driver = driver;
        elements = new Elements(driver);
    }


    private By Moisturizers = By.xpath("//*[text()='Buy moisturizers']");
    private By sunscreens = By.xpath("//*[text()='Buy sunscreens']");
    private By temperature =By.xpath("//span[@id='temperature']");




    public void CheckTempThenSelectProduct() throws Exception {
        int currentTemperature;
        String text = elements.getElementText(temperature);
        String[] val = text.split("\\s+"); //Extract temperature value from the fetched text
        currentTemperature = Integer.parseInt(val[0]);

        if(currentTemperature< 19){
            elements.click(Moisturizers);
            shopItem="Moisturizers";
        }else if (currentTemperature>34){
            elements.click(sunscreens);
            shopItem="sunscreens";

        }else{
            System.out.println("Wrong Temperature - Please Try Again Later");}

    }


    public String getTempValue() {
        return shopItem.toString();
    }
}

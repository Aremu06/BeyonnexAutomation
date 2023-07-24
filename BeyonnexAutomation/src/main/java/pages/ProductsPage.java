package pages;

import configurations.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsPage {

    private WebDriver driver;
    private Elements elements;

    public ProductsPage(WebDriver driver) throws IOException {
        this.driver = driver;
        elements = new Elements(driver);
    }

    private By goToCartPage = By.cssSelector(".thin-text.nav-link");
    private By payWithCardButton = By.cssSelector("button[type='submit'] span");


    public List<WebElement> getProductName() {
        List<WebElement>SunscreenList = driver.findElements(By.xpath("//p[@class='font-weight-bold top-space-10']"));
        return SunscreenList;
    }
    public List<WebElement> getProductPrice() {
        List<WebElement>SunscreenPrice = driver.findElements(By.xpath("//p[@class='font-weight-bold top-space-10'] /following-sibling::p"));
        return SunscreenPrice;
    }

    public List<WebElement> getAddToCartButton() {
        List<WebElement>SunscreenPrice = driver.findElements(By.xpath("//p[@class='font-weight-bold top-space-10']/following-sibling::button"));
        return SunscreenPrice;
    }

    public void ProceedToCartPage() {
        elements.click(goToCartPage);
        elements.click(payWithCardButton);
    }

    /***
     * Get product with a least Price According to the provided Keyword
     * How it works : Loop for the product inside each screen (Sunscreen- Moisturizers)  then extract the products name & Price
     * and if it matches the keyword it will store its price then sort them to get the least one then press add card corresponding to them
     */

    public String getProductsWithLowPrice(String keyword) {
        return getLowPriceProducts(getProductName(),getProductPrice(), keyword);

    }

    public  String getLowPriceProducts(List<WebElement> itemList, List<WebElement> itemsPrices, String ProductsCategory) {

        ArrayList<Integer> selectedProducts= new ArrayList<Integer>();
        for (int i = 0; i < itemsPrices.size() ; i++) {
            String item= itemList.get(i).getText();
            String price= itemsPrices.get(i).getText();
            if(item.contains(ProductsCategory)) {
                String m1= itemsPrices.get(i).getText().substring(price.lastIndexOf(" "),price.length()).trim();
                int m2= Integer.parseInt(m1);
                selectedProducts.add(m2);
            }
        }
        Collections.sort(selectedProducts);

        int lowPrice= selectedProducts.get(0);
        String lowPriceItem = " ";
        for (int i = 0; i < itemsPrices.size() ; i++) {
            String price= itemsPrices.get(i).getText();
            String m1= itemsPrices.get(i).getText().substring(price.lastIndexOf(" "),price.length()).trim();
            int m2= Integer.parseInt(m1);
            if(m2==lowPrice) {

                lowPriceItem = itemList.get(i).getText();
                getAddToCartButton().get((i)).click();
                break;
            }
        }
        return lowPriceItem ;
    }




}

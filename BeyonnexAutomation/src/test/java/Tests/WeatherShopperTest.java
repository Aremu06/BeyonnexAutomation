package Tests;

import TestData.ConfigData;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.PaymentPage;
import pages.ProductsPage;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class WeatherShopperTest extends BaseTest {

    HomePage homePage;
    ProductsPage productsPage;
    PaymentPage paymentPage;
    public static String FirstLowerPriceItem;
    public static String SecondLowerPriceItem;

    @BeforeClass
   @Parameters({"BrowserName"})

    public void setUp(@Optional("chrome") String BrowserName) throws AWTException, NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {

        setup(BrowserName);
        homePage = new HomePage(driver);
        productsPage= new ProductsPage(driver);
        paymentPage = new PaymentPage(driver);

    }

    @Test (priority = 0)
    public void SelectProductTest() throws Exception {

        logger = report.createTest("Select SunScreen Or Moisturizers According to Temperature");
        homePage.CheckTempThenSelectProduct();
        System.out.println(homePage.getTempValue());

    }

    @Test(priority = 1)
    public void SelectLeastPrice() throws Exception {

       logger = report.createTest("Select Least Price Products");

        if(homePage.getTempValue().equalsIgnoreCase("Moisturizers")) {
            FirstLowerPriceItem = productsPage.getProductsWithLowPrice(ConfigData.Product_category_aloe);
            SecondLowerPriceItem = productsPage.getProductsWithLowPrice(ConfigData.Product_category_almond);
        }
         else if (homePage.getTempValue().equalsIgnoreCase("Sunscreens"))
        {
            FirstLowerPriceItem = productsPage.getProductsWithLowPrice(ConfigData.Product_category_spf50);
            SecondLowerPriceItem = productsPage.getProductsWithLowPrice(ConfigData.Product_category_spf30);
        }
        productsPage.ProceedToCartPage();

    }

    @Test (priority = 2)
    public void CheckoutTest() {
        logger = report.createTest("Pay for products");

        paymentPage.enterEmail(ConfigData.Email);
        paymentPage.enterCarNumber(ConfigData.CardNumber);
        paymentPage.enterMonthYear(ConfigData.Expiry);
        paymentPage.enterCvc(ConfigData.Cvv);
        paymentPage.enterBillZipCode(ConfigData.ZipCode);
        paymentPage.clickPay();
        Assert.assertEquals(paymentPage.getHeaderText(),"Your payment was successful. You should receive a follow-up call from our sales team.","Payment didn't complete. Please try again later");

    }
}

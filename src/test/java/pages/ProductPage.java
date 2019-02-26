package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.logging.Logger;

public class ProductPage extends BasePage {


    private static Logger log = Logger.getLogger(ProductPage.class.getName());
    By photoTab = By.xpath("//li[@name ='photo']");
    By isPhotoTabIsActive = By.xpath("//li[@name = 'photo']/a[contains(@class, 'active')]");
    By btnTopPurchase = By.xpath("//button[@name ='topurchases']");
    By countOfPhotoOnPhotoTabInProductPage = By.xpath("//div[@class ='pp-photo-tab-i-img']");
    By countOfPhotoOnProductPage = By.xpath("//div[@class= 'detail-img-thumbs-l']/div");
    By notInStock = By.xpath("//div[@class = 'detail-waitlist-button']");
    public ProductPage(WebDriver driver) {
        super(driver);
    }


    @Step
    public ProductPage clickOnPhotoTab() {
        click(photoTab);
        log.info("Click on photo tab in product page");
        return this;
    }

    @Step
    public ProductPage waitForPhotoTabIsActive() {
        waitVisibility(isPhotoTabIsActive);
        log.info("Wait for visibility of photo tab");
        return this;
    }

    @Step
    public ProductPage waitForBtnTopPurchaseIsPresent() {

        if (getNumbersOfElements(btnTopPurchase) > 0){
            waitVisibility(btnTopPurchase);
            log.info("Wait for visibility of purchase btn");
            } else {
            waitVisibility(notInStock);
            log.info("Wait for visibility of stock btn");
            }

        return this;
    }

    @Step
    public boolean productPhotoCount()
    {
        if (getNumbersOfElements(countOfPhotoOnProductPage) !=
                getNumbersOfElements(countOfPhotoOnPhotoTabInProductPage)){
            return false;
        }
        return true;
    }
}





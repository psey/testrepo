package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public class ProductPage extends BasePage {


    By photoTab = By.xpath("//li[@name ='photo']");
    By isPhotoTabIsActive = By.xpath("//li[@name = 'photo']/a[contains(@class, 'active')]");
    By btnTopPurchase = By.xpath("//button[@name ='topurchases']");
    private static Logger log = Logger.getLogger(ProductPage.class.getName());
    public ProductPage(WebDriver driver) {
        super(driver);
    }


    @Step
    public ProductPage clickOnPhotoTab() {
        click(photoTab);
        log.info("Click on photo tab in probuct page");
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
        waitVisibility(btnTopPurchase);
        log.info("Wait for visibility of purchase btn");
        return this;
    }
}





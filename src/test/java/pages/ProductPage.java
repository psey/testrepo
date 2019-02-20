package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {


    By photoTab = By.xpath("//li[@name ='photo']");
    By isPhotoTabIsActive = By.xpath("//li[@name = 'photo']/a[contains(@class, 'active')]");
    By btnTopPurchase = By.xpath("//button[@name ='topurchases']");
    public ProductPage(WebDriver driver) {
        super(driver);
    }


    @Step
    public ProductPage clickOnPhotoTab() {
        click(photoTab);
        return this;
    }
    @Step
    public ProductPage waitForPhotoTabIsActive() {
        waitVisibility(isPhotoTabIsActive);
        return this;
    }
    @Step
    public ProductPage waitForBtnTopPurchaseIsPresent() {
        waitVisibility(btnTopPurchase);
        return this;
    }
}





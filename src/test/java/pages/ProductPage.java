package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {


    By photoTab = By.xpath("//li[@name ='photo']");
    By isPhotoTabIsActive = By.xpath("//li[@name = 'photo']/a[contains(@class, 'active')]");
    By btnTopPurchase = By.xpath("//button[@name ='topurchases']");
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage clickOnPhotoTab() {
        click(photoTab);
        return this;
    }

    public ProductPage waitForPhotoTabIsActive() {
        waitVisibility(isPhotoTabIsActive);
        return this;
    }

    public ProductPage waitForBtnTopPurchaseIsPresent() {
        waitVisibility(btnTopPurchase);
        return this;
    }
}


// проверить количество фоток на странице продукта и в галлерее совпадает
// что быстрое меню есть на странице прдукта




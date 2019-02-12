package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    String baseURL = "https://rozetka.com.ua/";
    By inputMainSearch = By.xpath("//input[@name='search']");
    By btnSearchSubmit = By.xpath("//button[@type='submit']");
    String searchedText = "ноутбук Asus";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Go to Home page
    public HomePage goToHomePage() {
        driver.get(baseURL);
        return this;
    }

    public HomePage setABTest(String name, String value) {
        deleteAllCookies();
        setCookie(name, value);
        refreshPage();
        return this;

    }

    // переписать метод на более гибкий
    public SearchPage searchNotebook() {
        writeText(inputMainSearch, searchedText);
        click(btnSearchSubmit);
        return new SearchPage(driver);
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    String baseURL = "https://rozetka.com.ua/";
    By inputMainSearch = By.xpath("//input[@name='search']");
    By btnSearchSubmit = By.xpath("//button[@type='submit']");
    String searchedText = "ноутбук Asus";

    // Go to Home page
    public HomePage goToHomePage() {
        driver.get(baseURL);
        return this;
    }

    public SearchPage searchNoteboo//k(String aqweq) {
        writeText(inputMainSearch, searchedText);
        click(btnSearchSubmit);
        return new SearchPage(driver);
    }






}

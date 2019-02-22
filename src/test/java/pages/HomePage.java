package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public class HomePage extends BasePage {

    String baseURL = "https://rozetka.com.ua/";
    By inputMainSearch = By.xpath("//input[@name='search']");
    By btnSearchSubmit = By.xpath("//button[@type='submit']");
    private static Logger log = Logger.getLogger(HomePage.class.getName());


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage() {
        super();
    }
    @Step
    public HomePage goToHomePage() {
        driver.get(baseURL);
        log.info("Go to URL");
        return this;
    }
    @Step
    public HomePage setABTest(String name, String value) {
        deleteAllCookies();
        setCookie(name, value);
        refreshPage();
        log.info("Set AB cookie");
        return this;

    }
    @Step
    public SearchPage searchNotebook(String category, String brand) {
        writeText(inputMainSearch, category + " " + brand);
        click(btnSearchSubmit);
        SearchPage result = new SearchPage(driver);
        result.setBrandName(brand);
        log.info("Search for notebook");
        return result;

    }


}

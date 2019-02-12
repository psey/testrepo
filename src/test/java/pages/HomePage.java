package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    String baseURL = "https://rozetka.com.ua/";
    By inputMainSearch = By.xpath("//input[@name='search']");
    By btnSearchSubmit = By.xpath("//button[@type='submit']");
    String brandName;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage() {
        super();
    }

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

    public SearchPage searchNotebook(String category, String brand) {
        writeText(inputMainSearch, category + " " + brand );
        setBrandName(brand);
        click(btnSearchSubmit);
        System.out.println("Setted brandNme is " + brandName);
        System.out.println("Getter" + getBrandName());
        return new SearchPage(driver);

    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName(){
        return brandName;
    }



}

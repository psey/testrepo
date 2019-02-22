package pages.entities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;
import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;
import static io.github.bonigarcia.wdm.DriverManagerType.OPERA;

public class SetupManager {
    private String browser;

    public SetupManager(String browser) {
        this.browser = browser.toUpperCase();

    }

    public WebDriver getBrowser() {
        if (browser.equals("CHROME")) {
            WebDriverManager.getInstance(CHROME).setup();
            return new ChromeDriver();
        } else if (browser.equals("OPERA")) {
            WebDriverManager.getInstance(OPERA).setup();
            return new OperaDriver();
        } else if (browser.equals("FIREFOX")) {
            WebDriverManager.getInstance(FIREFOX).setup();
            return new FirefoxDriver();
        } else {
        return null;
        }
    }
}

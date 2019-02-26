package pages.entities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.logging.Logger;

import static io.github.bonigarcia.wdm.DriverManagerType.*;

public class DriverManager {
    private static Logger log = Logger.getLogger(DriverManager.class.getName());
    WebDriver driver;
    private String browserType;

    public DriverManager(String browserType) {
        this.browserType = browserType.toUpperCase();

    }

    public WebDriver initBrowser() {
        switch (browserType) {
            case "CHROME":
                WebDriverManager.getInstance(CHROME).setup();
                log.info("Chrome is setup");
                driver = new ChromeDriver();
                break;

            case "OPERA":
                WebDriverManager.getInstance(OPERA).setup();
                log.info("Opera is setup");
                driver =  new OperaDriver();
                break;

            case "FIREFOX":
                WebDriverManager.getInstance(FIREFOX).setup();
                log.info("FF is setup");
                driver = new FirefoxDriver();
                break;

            default:
                return null;

        }
        maximizeWindow();
        return driver;
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void shutDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}

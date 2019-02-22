package pages.entities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.logging.Logger;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;
import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;
import static io.github.bonigarcia.wdm.DriverManagerType.OPERA;

public class SetupManager {
    private String browser;
    private static Logger log = Logger.getLogger(SetupManager.class.getName());

    public SetupManager(String browser) {
        this.browser = browser.toUpperCase();

    }

    public WebDriver getBrowser() {
        if (browser.equals("CHROME")) {
            WebDriverManager.getInstance(CHROME).setup();
            log.info("Chrome is setup");
            return new ChromeDriver();
        } else if (browser.equals("OPERA")) {
            WebDriverManager.getInstance(OPERA).setup();
            log.info("Opera is setup");
            return new OperaDriver();
        } else if (browser.equals("FIREFOX")) {
            WebDriverManager.getInstance(FIREFOX).setup();
            log.info("FF is setup");
            return new FirefoxDriver();
        } else {
        return null;
        }
    }
}

package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

        public WebDriver driver;

        @BeforeClass
        public void setup () {
            //Create a Chrome driver. All test classes use this.
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
      }

        @AfterClass
        public void teardown () {
            if(driver != null){
                driver.quit();
            }
        }
}


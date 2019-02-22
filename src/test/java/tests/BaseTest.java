package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.entities.SetupManager;

public class BaseTest {

        public WebDriver driver;
        protected final SetupManager app = new SetupManager("opera");

        @BeforeClass
        public void setup() throws Exception{
            driver = app.getBrowser();
            driver.manage().window().maximize();

      }

        @AfterClass
        public void teardown () {
            if(driver != null){
               driver.quit();
            }
        }
}


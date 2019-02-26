package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.entities.DriverManager;


public class BaseTest {

        public WebDriver driver;
        protected final DriverManager app = new DriverManager("chrome");

        @BeforeClass
        public void setup() throws Exception {
            driver = app.initBrowser();
        }

        @AfterClass
        public void teardown () {
            app.shutDownBrowser();
        }
}


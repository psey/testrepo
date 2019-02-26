package pages;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class BasePage {

    public WebDriver driver;
    public WebDriverWait wait;
    String screenshotSavePath = "/Users/marynaivanova/IdeaProjects/comrozetkauablah/src/main/screen/";


    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void waitVisibility(By elementBy) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
    }

    public void click(By elementBy) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).click();
    }

    public void writeText(By elementBy, String text) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).sendKeys(text);
    }

    public String readText(By elementBy) {
        waitVisibility(elementBy);
        return driver.findElement(elementBy).getText();
    }

    public int getNumbersOfElements(By elementBy) {
        return driver.findElements(elementBy).size();
    }

    public boolean checkedChbox(By elementBy) {
        return driver.findElement(elementBy).isSelected();
    }

    public void waitInvisibility(By elementBy) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementBy));
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void setCookie(String name, String value) {
        Cookie ck = new Cookie(name, value);
        driver.manage().addCookie(ck);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    private void takeScreenshot(String testName) throws Exception {
        String screenshotDirectory = screenshotSavePath;
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);

        String screenshotAbsolutePath = screenshotDirectory + File.separator
                + sdf.format(resultdate) + "_" + testName + ".png";
        File screenshot = new File(screenshotAbsolutePath);
        if (createFile(screenshot)) {
            try {
                writeScreenshotToFile(driver, screenshot);
            } catch (ClassCastException weNeedToAugmentOurDriverObject) {
                writeScreenshotToFile(new Augmenter().augment(driver),
                        screenshot);
            }
            System.out.println("Written screenshot to "
                    + screenshotAbsolutePath);
        } else {
            System.err.println("Unable to create " + screenshotAbsolutePath);
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] writeScreenshotToFile(WebDriver driver, File screenshot) {
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            byte[] bytes = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            screenshotStream.write(bytes);
            screenshotStream.close();
            return bytes;
        } catch (IOException unableToWriteScreenshot) {
            System.err.println("Unable to write "
                    + screenshot.getAbsolutePath());
            unableToWriteScreenshot.printStackTrace();
        }
        return null;
    }
    private boolean createFile(File screenshot) {
        boolean fileCreated = false;

        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                try {
                    fileCreated = screenshot.createNewFile();
                } catch (IOException errorCreatingScreenshot) {
                    errorCreatingScreenshot.printStackTrace();
                }
            }
        }

        return fileCreated;
    }

    public void snapWhenUWant() {
        try {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            takeScreenshot(name);
        } catch (Exception e) {
            System.err.println("Unable to take screenshot - " + e.getMessage());
        }
    }

}

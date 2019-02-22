package tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;


public class HomeTest extends BaseTest {

    By countOfPhotoOnPhotoTabInProductPage = By.xpath("//div[@class ='pp-photo-tab-i-img']");
    By countOfPhotoOnProductPage = By.xpath("//div[@class= 'detail-img-thumbs-l']/div");
    private String abTestCookieName = "ab_main_page";
    private String abTestCookieValue = "new";
    private SoftAssert softAssert = new SoftAssert(); // не делать приватной переменной

    @DataProvider(name = "Price")
    public Object[][] providePrice() {
        //return new Object[][]{{10000}, {15000}, {20000}};
        return new Object[][]{{10000}};
    }


    @Test(dataProvider = "Price")
    public void searchSpecificNotebookAndSetPriceAndVerifyPriceAndGoToFirstItemAndTakeSnapshoot(int price) //сделать ренейм и разбить по тестам
    {
        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage().setABTest(abTestCookieName, abTestCookieValue);

        SearchPage searchPage = homePage.searchNotebook("ноутбук", "Asus")
                .setPriceForSearch(price)
                .waitForLoader();

      //  driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
     /*   driver.findElement(By.cssSelector("ul#sort_producer>li:nth-child(5)")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        searchPage.initCheckboxesOfProducts();
        searchPage.getProductInTheFilter();

        searchPage.verifyPriceForAllElements(price);

       softAssert.assertEquals(searchPage.vendorsAreCheckedCorrect(), true, "Checkbox for brand name is checked");
       softAssert.assertEquals(searchPage.parseFoundNumberOfItems(), searchPage.getFoundItemsCount(), "Filter counter is equal count of element");

        ProductPage productPage = searchPage.clickToFirstElemAndMakeSnapshoot();

        productPage.clickOnPhotoTab();
        productPage.waitForPhotoTabIsActive();
        softAssert.assertEquals(productPage.getNumbersOfElements(countOfPhotoOnProductPage), productPage.getNumbersOfElements(countOfPhotoOnPhotoTabInProductPage), " count of photos in product tab === count of photos in photo tab ? ");
        productPage.waitForBtnTopPurchaseIsPresent();

    }
}

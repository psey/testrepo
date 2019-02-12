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

        SearchPage searchPage = homePage.searchNotebook()
                .setPriceForSearch(price)
                .waitForLoader();

        boolean isAsusChecked = searchPage.isAsusChecked();

        // SearchPage searchPage = new SearchPage(driver);
        //SearchPage searchPage = homePage.searchNotebook();

        searchPage.verifyPriceForAllElements(price);

        softAssert.assertEquals(isAsusChecked, true, "Checkbox Asus is checked");
        softAssert.assertEquals(searchPage.parseFoundNumberOfItems(), searchPage.getFoundItemsCount(), "Filter counter is equal count of element");

        ProductPage productPage = searchPage.clickToFirstElem();

        productPage.clickOnPhotoTab();
        productPage.waitForPhotoTabIsActive();
        softAssert.assertEquals(productPage.getElementsCount(countOfPhotoOnProductPage), productPage.getElementsCount(countOfPhotoOnPhotoTabInProductPage), " count of photos in product tab === count of photos in photo tab ? ");
        productPage.waitForBtnTopPurchaseIsPresent();
    }
}

package tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;


public class HomeTest extends BaseTest {

    By countOfPhotoOnPhotoTabInProductPage = By.xpath("//div[@class ='pp-photo-tab-i-img']");//todo move to page class
    By countOfPhotoOnProductPage = By.xpath("//div[@class= 'detail-img-thumbs-l']/div");
    private String abTestCookieName = "ab_main_page";
    private String abTestCookieValue = "new";
    private SoftAssert softAssert = new SoftAssert(); // не делать global переменной todo

    @DataProvider(name = "Price")
    public Object[][] providePrice() {
        return new Object[][]{{10000}, {15000}, {20000}};
        //return new Object[][]{{10000}};
    }


    @Test(dataProvider = "Price")
    public void searchNotebookAndSetPrice(int price) //сделать ренейм
    {
        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage().setABTest(abTestCookieName, abTestCookieValue);

        SearchPage searchPage = homePage.searchNotebook("ноутбук", "Acer")
                .setPriceForSearch(price)
                .waitForLoader();
        searchPage.initCheckboxesOfProducts();
        searchPage.getProductInTheFilter();

        searchPage.verifyPriceForAllElements(price);

        softAssert.assertEquals(searchPage.vendorsAreCheckedCorrect(), true, "Checkbox for brand name is not checked");
        softAssert.assertEquals(searchPage.parseFoundNumberOfItems(), searchPage.getFoundItemsCount(), "Filter counter is not equal count of element");

        ProductPage productPage = searchPage.clickToFirstElemAndMakeSnapshoot();

        productPage.clickOnPhotoTab();
        productPage.waitForPhotoTabIsActive();
        softAssert.assertEquals(productPage.getNumbersOfElements(countOfPhotoOnProductPage), productPage.getNumbersOfElements(countOfPhotoOnPhotoTabInProductPage), "Count of photos in product tab  isn't equal count of photos in photo tab");
        productPage.waitForBtnTopPurchaseIsPresent();

    }
}

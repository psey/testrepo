package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;


public class HomeTest extends BaseTest {
    private SoftAssert softAssert = new SoftAssert();

    @DataProvider(name = "Price")
    public Object[][] providePrice() {
        //return new Object[][]{{10000}, {15000}, {20000}};
        return new Object[][]{{10000}};
    }


    @Test(dataProvider = "Price")
    public void searchNotebookAndSetPrice(int price)
    {
        HomePage homePage = new HomePage(driver);
       homePage.goToHomePage().setABTest();

        SearchPage searchPage = homePage.searchNotebook("ноутбук", "Apple")
                .setPriceForSearch(price)
                .foundItemPrice();
        searchPage.initVendorListXPath();
        searchPage.verifyPriceForAllElements(price);
        softAssert.assertEquals(searchPage.vendorInTheFilterCheckedCorrectly(), true, "Checkbox for brand name is not checked");
        softAssert.assertEquals(searchPage.parseFoundNumberOfItems(), searchPage.getFoundItemsCount(), "Filter counter is not equal count of element");

        ProductPage productPage = searchPage.clickToFirstElemAndMakeSnapshoot();

        productPage.clickOnPhotoTab();
        productPage.waitForPhotoTabIsActive();
        softAssert.assertTrue(productPage.productPhotoCount(),"Not equal");
        productPage.waitForBtnTopPurchaseIsPresent();
    }
}

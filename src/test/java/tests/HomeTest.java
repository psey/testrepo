package tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;

public class HomeTest extends BaseTest {

    String cook//ieName = "ab_main_page";
    String coo//kieValue = "new";
    //SoftAssert softAssert = new SoftAssert();
    By countF//oPhotoOnPhotoTab = By.xpath("//div[@class ='pp-photo-tab-i-img']");
    By countO//fphotoOnProductPage = By.xpath("//div[@class= 'detail-img-thumbs-l']/div");


 @DataProvider(name = "Price")
     public Object[][] providePrice () {
         //return new Object[][]{{10000}, {15000}, {20000}};
        return new Object[][]{{10000}};
     }




 @Test(dataProvider = "Price")
    public void someT//est(int price) //сделать ренейм
    {
        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage()
                .setCookie(cookieName, cookieValue);

        SearchPage searchPage = homePage.searchNotebook()
                .setPriceForSearch(price)
                .waitForLoader();

        boolean isAsusChecked = searchPage.isAsusChecked();

     // SearchPage searchPage = new SearchPage(driver);
      //SearchPage searchPage = homePage.searchNotebook();

      searchPage.verifyPriceForAllElements(price);

      softAssert.assertEquals(isAsusChecked, true, "Checkbox Asus is checked");
      softAssert.assertEquals(searchPage.parseFoundNumberOfItems(), searchPage.getFoundItemsCount(), "Filter counter is equal count of element" );

        ProductPage productPage = searchPage.clickToFirstElem();

        //ProductPage productPage = new ProductPage(driver);

        /* try {

        productPage.clickOnPhotoTab();
        System.out.println(Clicked)!
         if
        } catch {
        }




         */




        productPage.clickOnPhotoTab();
        productPage.waitForPhotoTabIsActive();
        softAssert.assertEquals(productPage.getElementsCount(countOfphotoOnProductPage), productPage.getElementsCount(countFoPhotoOnPhotoTab), " count of photos in product tab === count of photos in photo tab ? ");
        productPage.waitForbtnTopPurchaseIsPresent();




    }


}

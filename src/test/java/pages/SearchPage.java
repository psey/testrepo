package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import pages.entities.Vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchPage extends BasePage {

    private static Logger log = Logger.getLogger(SearchPage.class.getName());
    By inputMaxPriceFilterValue = By.id("price[max]");
    By submitPriceButtonId = By.id("submitprice");
    By searchLoaderXPath = By.xpath("//div[@class = 'progress-b progress-b-fixed']");
    By ItemsFoundTextClassName = By.className("filter-active-i-text");
    By foundItemPriceXPath = By.xpath("//div[@class = 'g-i-tile g-i-tile-catalog']//div[@class ='g-price-uah']");
    By foundItemsXPath = By.xpath("//div[@class = 'g-i-tile g-i-tile-catalog']");
    List<WebElement> foundItemsPrices;
    String brandName;
    List<Vendor> vendors;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public SearchPage setPriceForSearch(int price) {
        writeText(inputMaxPriceFilterValue, String.valueOf(price));
        click(submitPriceButtonId);
        log.info("Set price for search in the product filter");
        return this;
    }

    public SearchPage foundItemPrice() {
        waitInvisibility(searchLoaderXPath);
        foundItemsPrices = driver.findElements(foundItemPriceXPath);//
        log.info("Wait for page load");
        return this;
    }

    @Step
    public int getFoundItemsCount() {
        return getNumbersOfElements(foundItemsXPath);
    }


    // Это парсит строку выдачи поиска типа
    //Подобрано 31 товаров из 3250 и вытягивает число 31

    public int parseFirstNumber(By elementBy) {
        return parseFirstNumber(readText(elementBy));
    }

    public int parseFirstNumber(String parsed) {
        //System.out.println("  >>>  To parse first number: '" + parsed + "'");
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(parsed);
        if (m.find()) {
            //System.out.println("  >>>  First number is '" + String.valueOf(Integer.parseInt(m.group(0))) + "'");
            return Integer.parseInt(m.group(0));
        } else {
            return 0; // хз что тут лучше
        }

    }

    public int parseFoundNumberOfItems() {
        return parseFirstNumber(ItemsFoundTextClassName);
    }

    // Парсит стоимость из строки типа "10 000 грн"
    public int parseItemPrice(String price) {
        String filteredPrice = price.replaceAll("[^0-9]", "");
        return parseFirstNumber(filteredPrice);
    }

    public boolean pricesAreValid(List<WebElement> ItemsPricesBy, int price) {
        for (WebElement item : ItemsPricesBy) {
            String itemText = item.getText();
            //System.out.println("  >>>  Price text is '" + itemText + "'");
            int parsedPrice = parseItemPrice(itemText);
            //System.out.println("  >>>  Price number is '" + String.valueOf(parsedPrice) + "'");
            if (parsedPrice > price)
                return false;
        }
        return true;
    }

    @Step
    public boolean verifyPriceForAllElements(int price) {
        return pricesAreValid(foundItemsPrices, price);
    }

    public void getNthItem(By elementBy, int n) {
        driver.findElements(elementBy).get(n).click();
    }

    @Step
    public ProductPage clickToFirstElemAndMakeSnapshoot() {
        getNthItem(foundItemsXPath, 0);
        driver.getCurrentUrl();
        snapWhenUWant();

        return new ProductPage(driver);
    }

    @Step
    public void initVendorListXPath() {
        vendors = new ArrayList<>();
        By listOfParentElement = By.xpath("//ul[@id='sort_producer']//label[@class = 'filter-parametrs-i-l-i-label']");
        By listOfCheckboxesValuesInBrandFilter = By.xpath(".//input[@type = 'checkbox']");
        By listOfBrandValuesInTheBrandFilter = By.xpath(".//i[@class = 'filter-parametrs-i-l-i-default-title']");
        List<WebElement> listOfBrands = driver.findElements(listOfParentElement);

        for (int i = 0; i < listOfBrands.size(); i++ )
        {
            WebElement brandItem = listOfBrands.get(i);
            List <WebElement> brandChbox = brandItem.findElements(listOfCheckboxesValuesInBrandFilter);
            List <WebElement> brandName = brandItem.findElements(listOfBrandValuesInTheBrandFilter);
            Vendor currentVendor = new Vendor();
             if (brandChbox.size() > 0){
                 for(WebElement elem :brandChbox ){
                     currentVendor.setBrandCheckbox(elem.getAttribute("checked"));
                 }
             }
             if (brandName.size() > 0)
             {
                 for(WebElement elem:brandName ){
                     currentVendor.setBrandName(elem.getText());
                 }
             }
             vendors.add(currentVendor);
        }
    }

    @Step
    public boolean vendorInTheFilterCheckedCorrectly() {
        for(Vendor vendor:vendors){
            if (vendor.getBrandName().equals(getBrandName()) != vendor.getBrandCheckbox() ){
                return false;
            }
        }
        return true;
    }

}
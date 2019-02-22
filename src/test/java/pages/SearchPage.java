package pages;

import io.qameta.allure.Attachment;
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

    By inputMaxPriceFilterValue = By.id("price[max]");
    By submitPriceButtonId = By.id("submitprice");
    By searchLoaderXPath = By.xpath("//div[@class = 'progress-b progress-b-fixed']");
    By chboxForAsusNotebookInTheFilterMenuXPath = By.xpath("//*[@id = 'filter_producer_4']//input[@type = 'checkbox']");
    By ItemsFoundTextClassName = By.className("filter-active-i-text");
    By foundItemPriceXPath = By.xpath("//div[@class = 'g-i-tile g-i-tile-catalog']//div[@class ='g-price-uah']");
    By foundItemsXPath = By.xpath("//div[@class = 'g-i-tile g-i-tile-catalog']");
    List<WebElement> foundItemsPrices;
    String screenshotSavePath = "/Users/marynaivanova/IdeaProjects/comrozetkauablah/src/main/screen/";
    String brandName;
    List<String> listOfBrandsInBrandFilter;
    List<String> checkboxListInTheFilter;
    List<Vendor> checkboxPlusBrand;
    private static Logger log = Logger.getLogger(SearchPage.class.getName());



    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName(){
        return brandName;
    }

    public SearchPage setPriceForSearch(int price) {
        writeText(inputMaxPriceFilterValue, String.valueOf(price));
        click(submitPriceButtonId);
        log.info("Set price for search in the product filter");
        return this;
    }

    public SearchPage waitForLoader() {
        waitInvisibility(searchLoaderXPath);
        foundItemsPrices = driver.findElements(foundItemPriceXPath);
        log.info("Wait for page load");
        return this;
    }

    public boolean isAsusChecked() {
        return checkedChbox(chboxForAsusNotebookInTheFilterMenuXPath);
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
    public ProductPage clickToFirstElem() {
        getNthItem(foundItemsXPath, 0);
        driver.getCurrentUrl();
        try {
            final Random random = new Random();
            takeAndSaveSnapShot(driver, screenshotSavePath + "screen+" + random.nextInt() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductPage(driver);

    }

    @Step
    public ProductPage clickToFirstElemAndMakeSnapshoot() {
        getNthItem(foundItemsXPath, 0);
        driver.getCurrentUrl();
        saveScreenshot(driver);
        return new ProductPage(driver);

    }

    public boolean brandnameIsValid(List<WebElement> foundBrandName)
    {
        String currentBrand = getBrandName();

        for (WebElement item:foundBrandName)
        {
            String brandText = item.getText();
            System.out.println("Brand is " + brandText);
            System.out.println("item is " + item);
            if (! brandText.equals(currentBrand)){
                //(!brandText.equals(currentBrand) || !item.isSelected())

                return false;
            }
        }

        return true;
    }

//НЕПРАВИЛЬНО переделать

    @Step
    public void initCheckboxesOfProducts() {
        By listOfBrKolbasa = By.cssSelector("ul#sort_producer label.filter-parametrs-i-l-i-label");
        By listOfCheckboxesValuesInBrandFilter = By.cssSelector("ul#sort_producer label.filter-parametrs-i-l-i-label>input");
        By listOfBrandValuesInTheBrandFilter = By.xpath("//ul[@id ='sort_producer']//i[@class = 'filter-parametrs-i-l-i-default-title']");
        checkboxListInTheFilter = new ArrayList<String>();
        listOfBrandsInBrandFilter = new ArrayList<String>();

        //
        for (int i = 0; i < getNumbersOfElements(listOfBrKolbasa); i++) {
           String chbox = driver.findElements(listOfCheckboxesValuesInBrandFilter).get(i).getAttribute("checked");
            checkboxListInTheFilter.add(chbox);
            String brName = driver.findElements(listOfBrandValuesInTheBrandFilter).get(i).getText();
            listOfBrandsInBrandFilter.add(brName);
        }

    }

    @Step
    public void getProductInTheFilter() {
        checkboxPlusBrand = new ArrayList<Vendor>();
        if (checkboxListInTheFilter.size() == listOfBrandsInBrandFilter.size())
        {
            for(int i = 0; i < checkboxListInTheFilter.size(); i++)
            {
                Vendor name = new Vendor(checkboxListInTheFilter.get(i),listOfBrandsInBrandFilter.get(i));
                checkboxPlusBrand.add(name);
            }
        }


    }
    @Step
    public boolean vendorsAreCheckedCorrect()
    {
        for(int i = 0; i < checkboxPlusBrand.size(); i++)
        {
            Vendor vend = checkboxPlusBrand.get(i);
            if (vend.getBrandName().equals(getBrandName()) != vend.isBrandCheckboxIsChecked())
            {
                return false;
            }
        }
        return true;
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public  byte[] saveScreenshot(WebDriver driver) {
       return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

    }

}

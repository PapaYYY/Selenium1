import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by voyo on 24.10.2016.
 */
public class Page {

    private WebDriver driver;
    private List<WebElement> rerultsList;

    public List<WebElement> getResultsList() {
        return rerultsList;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setResultsList() {
        this.rerultsList = driver.findElements(By.xpath("//a[ancestor::div[@class='pet_title']]"));
            }

    public Page(WebDriver driver, List<WebElement> resultsList) {
        this.driver = driver;
        this.rerultsList = resultsList;
    }

    //additional method to check that element of the page is exist
    public boolean isElementPresent(By locator) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean result = driver.findElements(locator).size() > 0;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return result;
    }

   //additional method that wait until the element with xpaht will appear on the page
    public void waitResultPage(String xPath) {
        WebDriverWait waiter = new WebDriverWait(driver, 10, 500);
        waiter.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return isElementPresent(By.xpath(xPath));
            }
        });
    }

    // method that returns link from pool of results with text that we need
    public String getLinkContains(String example) {
        String result = "https://petition.president.gov.ua/";
        for (WebElement el : rerultsList) {
            if (el.getText().contains(example)) result = el.getAttribute("href");
        }
        return result;
    }

    public Page search(String searchfield) {
        List<WebElement> list;
        Assert.assertTrue(isElementPresent(By.cssSelector(".txt_input.vat")), "Search field is not exist!");
        WebElement searchField = driver.findElement(By.cssSelector(".txt_input.vat"));
        searchField.clear();
        searchField.sendKeys(searchfield);
        searchField.submit();

        waitResultPage("//h1[text()='ВСІ ЕЛЕКТРОННІ ПЕТИЦІЇ']");

        list = driver.findElements(By.xpath("//a[ancestor::div[@class='pet_title']]"));
        return new ResultPage(driver, (ArrayList<WebElement>) list);


    }

}

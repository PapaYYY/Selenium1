import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by voyo on 24.10.2016.
 */
public class TestingSearch {
    WebDriver driver;
    String searchfield = "РОБОЧИЙ ЧАС — УКРАЇНЦЯМ!";
    String textInResult = new String("СКОРОЧЕНИЙ європейський РОБОЧИЙ ЧАС — УКРАЇНЦЯМ!");

    @BeforeClass
    public void seting() {
        driver = new FirefoxDriver();
        driver.get("https://petition.president.gov.ua/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void searchtest() {
        Page page1 = new Page(driver, null);
        Page page2 = page1.search(searchfield);

        Assert.assertTrue(page2.getResultsList().size() > 0, "No search results!");
        System.out.println(page2.getResultsList().size() + " search results was found.");

        driver.get(page2.getLinkContains(textInResult));
        page1.waitResultPage("//h1[text()='СКОРОЧЕНИЙ європейський РОБОЧИЙ ЧАС — УКРАЇНЦЯМ!']");

        Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains(textInResult), "No searched text in headers of resulted page!");
        System.out.println("Text was found");


    }

    @AfterClass
    public void finish() {
        driver.quit();
    }
}

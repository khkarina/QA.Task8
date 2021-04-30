import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.apache.commons.io.FileUtils.copyFile;

public class Main {

    String url = "https://rozetka.com.ua/";
    WebDriver driver;

    @Test
    public void main() {
        System.setProperty("webdriver.chrome.driver", "src/browserDriver/chromedriver.exe");
        driver = new ChromeDriver();
        Actions actions = new Actions(driver);
        WebDriverWait pageLoad = new WebDriverWait(driver, 10);

        driver.get(url);
        driver.manage().window().maximize();

        WebElement searchElement = driver.findElement(By.cssSelector(".search-form__input"));
        searchElement.sendKeys("Монитор");

        WebElement searchButton = driver.findElement(By.cssSelector(".search-form__submit"));
        searchButton.click();

        pageLoad.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Samsung Odyssey G5")));
        WebElement product = driver.findElement(By.partialLinkText("Samsung Odyssey G5"));
        product.click();

        pageLoad.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='buy-button__label ng-star-inserted'] ")));
        WebElement target = driver.findElement(By.xpath("//span[@class='buy-button__label ng-star-inserted']"));
        actions.moveToElement(target).perform();
        target.click();

        String preloaderSpinner = "//div[contains(@class,'modal__preloader')]";
        pageLoad.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(preloaderSpinner)));

        pageLoad.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='https://rozetka.com.ua//checkout/']")));
        WebElement acceptYes = driver.findElement(By.xpath("//a[@href='https://rozetka.com.ua//checkout/']"));
        acceptYes.click();

        String preloaderLine = "//div[@class='preloader']";
        pageLoad.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(preloaderLine)));

        driver.quit();
    }
    @AfterMethod(alwaysRun = true)
    public void takeScreenshot(ITestResult result) {
        if (!result.isSuccess())
            try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                copyFile(scrFile, new File(result.getName() +"["+ LocalDate.now() + "][" + System.currentTimeMillis() + "].png"));

            } catch (
                    IOException e) {
                e.printStackTrace();
            }
    }
}


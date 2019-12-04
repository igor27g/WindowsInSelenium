import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Windows {

    WebDriver driver;
    WebDriverWait wait;

    By cookieAccept = By.cssSelector("#cn-accept-cookie");

    @BeforeEach
    public void driverSetup()
    {
        String fakeStore = "https://fakestore.testelka.pl/";
        String blog = "https://testelka.pl/blog/";



        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(fakeStore);
        wait = new WebDriverWait(driver,10);
       // driver.findElement(cookieAccept).click();
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieAccept));

    }

    @AfterEach
    public void driverClose()
    {
       // driver.close();
        driver.quit();
    }

    @Test
    public void windowHandlesTest() {
        driver.findElement(By.cssSelector("i[class='fa fa-youtube fa-3x']")).click();
        Set<String> windows = driver.getWindowHandles();
        String parentWindow = driver.getWindowHandle();
        windows.remove(parentWindow);
        String secondWindow =  windows.iterator().next();
        driver.switchTo().window(secondWindow);
        String activeWindow = driver.getWindowHandle();
        driver.findElement(By.cssSelector("#logo-icon-container")).click();
        driver.switchTo().window(parentWindow);
    }

    @Test
    public void instagramWindowHandlesTest() {
        driver.findElement(By.cssSelector("i[class='fa fa-instagram fa-3x']")).click();
        Set<String> windows = driver.getWindowHandles();
        String blogWindow = driver.getWindowHandle();
        windows.remove(blogWindow);
        String instaWindow = windows.iterator().next();
        driver.switchTo().window(instaWindow);
        String activeWindow = driver.getWindowHandle();
        driver.switchTo().window(blogWindow);
    }

    //Assertions.assertDoesNotThrow(()->waitForSomething(), "Error message.");

    By cookies = By.cssSelector(".woocommerce-store-notice__dismiss-link");
    By addToCart = By.cssSelector("[src='https://fakestore.testelka.pl/wp-content/uploads/2019/01/damir-spanic-1209718-unsplash-324x324.jpg']");
    By addToWhishList = By.cssSelector("[class='add_to_wishlist']");
    By feedbackMessage = By.cssSelector("span.feedback");
    By wishList = By.cssSelector("a[rel='noopener noreferrer']");
    By deleteProductFromWishList = By.cssSelector("[class='remove remove_from_wishlist']");
    By alertMessage = By.cssSelector("div[role='alert']");

    @Test
    public void deleteFromWishList() {
        driver.findElement(cookies).click();
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addToWhishList)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(feedbackMessage));
        wait.until(ExpectedConditions.elementToBeClickable(wishList)).click();
        Set<String> windows = driver.getWindowHandles();
        String parentWindow = driver.getWindowHandle();
        windows.remove(parentWindow);
        String secondWindow =  windows.iterator().next();
        driver.switchTo().window(secondWindow);
        wait.until(ExpectedConditions.elementToBeClickable(deleteProductFromWishList)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage));
        String alertMessage = driver.findElement(By.cssSelector("div[role='alert']")).getText();
        Assertions.assertEquals("Produkt został usunięty.", alertMessage, "Wrong alert message");
        //Assertions.assertDoesNotThrow(()->wait.until(ExpectedConditions.presenceOfElementLocated(alertMessage))),"Wishlist is not empty");
    }

}

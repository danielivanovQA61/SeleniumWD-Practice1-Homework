package base;

import org.example.BrowserTypes;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;



    protected static WebDriver startBrowser(BrowserTypes browserTypes){

        //Chrome, FF, Edge
        switch (browserTypes){
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions(); //may not be needed
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);
        }

        return null;
    }

    protected static void authenticateWithUser(String username, String password){
        //data-test="username"
        WebElement usernameField = driver.findElement(By.xpath("//input[@data-test='username']"));
        //data-test="password"
        WebElement passwordField = driver.findElement(By.xpath("//input[@data-test='password']"));
        //data-test="login-button"
        WebElement loginBtn = driver.findElement(By.xpath("//input[@data-test='login-button']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();

        WebElement inventoryPageTitle = driver.findElement(By.xpath("//div[@class='app_logo']"));
        wait.until(ExpectedConditions.visibilityOf(inventoryPageTitle));
    }

    protected static void fillUserInfo(String firstName, String lastName, String postalCode){

        WebElement firstNameField = driver.findElement(By.xpath("//input[@data-test='firstName']"));
        WebElement lastNameField = driver.findElement(By.xpath("//input[@data-test='lastName']"));
        WebElement postalCodeField = driver.findElement(By.xpath("//input[@data-test='postalCode']"));

        WebElement continueBtn = driver.findElement(By.xpath("//input[@data-test='continue']"));

        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postalCodeField.sendKeys(postalCode);
        continueBtn.click();
    }

    protected WebElement getProductByTitle(String title) {
        return driver.findElement(By.xpath(String.format("//div[@class='cart_item' and descendant::div[text()='%s']]", title)));
    }

}

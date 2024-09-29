package saucedemo;

import base.BaseTest;
import org.example.BrowserTypes;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductTests extends BaseTest {

    @BeforeEach
    public void setup(){
        driver = startBrowser(BrowserTypes.CHROME);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://www.saucedemo.com/");


        authenticateWithUser("standard_user", "secret_sauce");

        var firstProduct = driver.findElement(By.xpath("//button[@data-test='add-to-cart-sauce-labs-backpack']"));
        firstProduct.click();
        var secondProduct = driver.findElement(By.xpath("//button[@data-test='add-to-cart-sauce-labs-bike-light']"));
        secondProduct.click();
        var shoppingCart = driver.findElement(By.xpath("//span[@data-test='shopping-cart-badge']"));
        shoppingCart.click();
    }

    @AfterEach
    public void afterTest(){
        driver.close();
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart() {
        String searchTermFirstItem = "Sauce Labs Backpack";
        String searchTermSecondItem = "Sauce Labs Bike Light";
        var firstItem = getProductByTitle(searchTermFirstItem);
        var secondItem = getProductByTitle(searchTermSecondItem);
        Assertions.assertTrue(firstItem.getText().contains(searchTermFirstItem), "Expected text not found.");
        Assertions.assertTrue(secondItem.getText().contains(searchTermSecondItem), "Expected text not found.");
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation(){
        String logoTitle = "Swag Labs";
        driver.findElement(By.xpath("//button[@data-test='checkout']")).click();
        fillUserInfo("Daniel","Ivanov","1000");
        WebElement inventoryPageTitle = driver.findElement(By.xpath("//div[@class='app_logo']"));
        wait.until(ExpectedConditions.visibilityOf(inventoryPageTitle));
        Assertions.assertTrue(inventoryPageTitle.getText().contains(logoTitle), "Expected Logo Title not found.");
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm(){
        driver.findElement(By.xpath("//button[@data-test='checkout']")).click();
        fillUserInfo("Daniel","Ivanov","1000");
        WebElement inventoryPageTitle = driver.findElement(By.xpath("//div[@class='app_logo']"));
        wait.until(ExpectedConditions.visibilityOf(inventoryPageTitle));
        driver.findElement(By.xpath("//button[@data-test='finish']")).click();
        driver.findElement(By.xpath("//a[@data-test='shopping-cart-link']")).click();

        // Check if the anchor element has no children using XPath / No child elements = no products in cart
        WebElement anchorWithNoChildren = driver.findElement(By.xpath("//a[@data-test='shopping-cart-link' and not(node())]"));

        // Assert that the cart has no products
        Assertions.assertNotNull(anchorWithNoChildren, "Anchor element should have no children.");
    }
}

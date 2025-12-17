package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends BasePage {
    private final By firstNameField = By.id("billing_first_name");
    private final By lastNameField = By.id("billing_last_name");
    private final By addressLineOneField = By.id("billing_address_1");
    private final By billingCityField = By.id("billing_city");
    private final By billingPostCodeField = By.id("billing_postcode");
    private final By billingEmailField = By.id("billing_email");
    private final By placeOrderButton = By.id("place_order");
    private final By successNotice = By.cssSelector(".woocommerce-notice");
    private final By clickHereToLoginButton = By.cssSelector(".showlogin");
    private final By userNameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector(".woocommerce-button");
    private final By overlay = By.cssSelector("div.blockUI.blockOverlay");

    public CheckoutPage(WebDriver driver) { super(driver); }

    public CheckoutPage enterFirstName(String firstName) {
        type(firstNameField, firstName);
        return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        type(lastNameField, lastName);
        return this;
    }

    public CheckoutPage enterAddressLineOne(String addressLineOne) {
        type(addressLineOneField, addressLineOne);
        return this;
    }

    public CheckoutPage enterCity(String city) {
        type(billingCityField, city);
        return this;
    }

    public CheckoutPage enterPostCode(String postCode) {
        type(billingPostCodeField, postCode);
        return this;
    }

    public CheckoutPage enterEmail(String email) {
        type(billingEmailField, email);
        return this;
    }

    public String getNotice() {
        return waitVisible(successNotice).getText();
    }

    public OrderConfirmationPage placeOrder() {
        waitInvisible(overlay);

        // Stale ihtimaline karşı 2 deneme
        for (int i = 0; i < 2; i++) {
            try {
                WebElement button = waitClickable(placeOrderButton); // her seferinde yeniden bul
                button.click();
                return new OrderConfirmationPage(driver);
            } catch (StaleElementReferenceException e) {
                // tekrar dene; loop yeniden bulacak
            }
        }
        // son çare: yeniden locate edip tıkla veya exception fırlat
        WebElement button = waitClickable(placeOrderButton);
        button.click();
        return new OrderConfirmationPage(driver);
    }


    public CheckoutPage clickHereToLogin() {
        clickWhenClickable(clickHereToLoginButton);
        return this;
    }

    public CheckoutPage enterUserNameField(String userName) {
        type(userNameField, userName);
        return this;
    }

    public CheckoutPage enterPasswordField(String password) {
        type(passwordField, password); // locator düzeltildi
        return this;
    }

    public CheckoutPage clickloginButton() {
        clickWhenClickable(loginButton);
        return this;
    }

    public CheckoutPage login(String userName, String password) {
        waitVisible(userNameField).sendKeys(userName);
        waitVisible(passwordField).sendKeys(password);
        clickWhenClickable(loginButton);
        waitInvisible(overlay);                // login overlay kaybolsun
        waitClickable(firstNameField);         // billing form tekrar aktif olsun
        return this;
    }
}

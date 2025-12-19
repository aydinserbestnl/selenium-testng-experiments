package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import com.selenium.pom.objects.BillingAddress;
import com.selenium.pom.objects.User;
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

    public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
        return enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .enterAddressLineOne(billingAddress.getAddressLine())
                .enterCity(billingAddress.getCity())
                .enterPostCode(billingAddress.getPostalCode())
                .enterEmail(billingAddress.getEmail());
    }

    public OrderConfirmationPage placeOrder() {
        waitInvisible(overlay);
        for (int i = 0; i < 2; i++) {
            try {
                WebElement button = waitClickable(placeOrderButton);
                button.click();
                return new OrderConfirmationPage(driver);
            } catch (StaleElementReferenceException e) {
                // retry
            }
        }
        WebElement button = waitClickable(placeOrderButton);
        button.click();
        return new OrderConfirmationPage(driver);
    }

    public CheckoutPage clickHereToLogin() {
        clickWhenClickable(clickHereToLoginButton);
        waitInvisible(overlay); // login form açılırken overlay kalksın
        return this;
    }

    public CheckoutPage enterUserNameField(String userName) {
        type(userNameField, userName);
        return this;
    }

    public CheckoutPage enterPasswordField(String password) {
        type(passwordField, password);
        return this;
    }

    public CheckoutPage clickloginButton() {
        clickWhenClickable(loginButton);
        return this;
    }

    public CheckoutPage login(User user) {
        waitInvisible(overlay); // form gerçekten etkileşime açık olsun
        WebElement userEl = waitClickable(userNameField);
        userEl.clear();
        userEl.sendKeys(user.getUserName());

        WebElement passEl = waitClickable(passwordField);
        passEl.clear();
        passEl.sendKeys(user.getPassword());

        clickWhenClickable(loginButton);
        waitInvisible(overlay);        // login sonrası overlay kalksın
        waitClickable(firstNameField); // billing form aktif
        return this;
    }
}

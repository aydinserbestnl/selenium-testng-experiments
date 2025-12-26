package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import com.selenium.pom.objects.BillingAddress;
import com.selenium.pom.objects.User;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class CheckoutPage extends BasePage {
    private final By firstNameField = By.id("billing_first_name");
    private final By lastNameField = By.id("billing_last_name");
    private final By addressLineOneField = By.id("billing_address_1");
    private final By billingCityField = By.id("billing_city");
    private final By billingPostCodeField = By.id("billing_postcode");
    private final By billingEmailField = By.id("billing_email");
    private final By placeOrderButton = By.id("place_order");
    private final By successNotice = By.cssSelector("p.woocommerce-notice");
    private final By clickHereToLoginButton = By.cssSelector(".showlogin");
    private final By userNameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector(".woocommerce-button");
    private final By overlay = By.cssSelector("div.blockUI.blockOverlay");
    private final By productName = By.cssSelector("td.product-name");

    public CheckoutPage(WebDriver driver) { super(driver); }
    public CheckoutPage load(){
        load("/checkout/");
        return this;
    }

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
    @Step
    public String getNotice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice)).getText();
    }
    @Step
    public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
        return enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .enterAddressLineOne(billingAddress.getAddressLine())
                .enterCity(billingAddress.getCity())
                .enterPostCode(billingAddress.getPostalCode())
                .enterEmail(billingAddress.getEmail());
    }

    @Step
    public CheckoutPage placeOrder() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

        Wait<WebDriver> clickWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(StaleElementReferenceException.class);

        clickWait.until(d -> {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
            WebElement button = d.findElement(placeOrderButton);
            ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            button.click();
            return true;
        });

        wait.until(ExpectedConditions.urlContains("order-received"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        return this;
    }

    public CheckoutPage clickHereToLogin() {
        clickWhenClickable(clickHereToLoginButton);

        // 1️⃣ overlay gerçekten gitsin
        waitInvisible(overlay);

        // 2️⃣ login formun DOM'da göründüğünden emin ol
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField));

        // 3️⃣ ve etkileşime açık olsun
        wait.until(ExpectedConditions.elementToBeClickable(userNameField));

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
    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

}

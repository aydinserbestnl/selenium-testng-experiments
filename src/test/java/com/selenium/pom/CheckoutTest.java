package com.selenium.pom;

import com.selenium.pom.base.BaseTest;
import com.selenium.pom.pages.*;
import com.selenium.pom.utils.ConfigLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test
    public void guestCheckoutBlueShoes() {
        // Use a single variable for the dynamic search term so the flow remains data-driven.
        String term = "Blue";
        StorePage storePage = new HomePage(getDriver()).load()
                .navigateToStoreUsingMenu()
                .searchFor(term)
                .waitForResultsHeading(term)
                .clickAddToCartFor("Blue Shoes");

        Assert.assertEquals(
                storePage.getResultsHeadingText(),           // POM’dan DOM metnini oku
                storePage.buildResultsHeading(term)          // Beklenen stringi yine POM’dan üret
        );
        CartPage cartPage = storePage.clickViewCart();

        Assert.assertEquals(
                cartPage.getProductName(), "Blue Shoes");
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        OrderConfirmationPage orderConfirmationPage = checkoutPage.
                enterFirstName("demo").
                enterLastName("user").
                enterAddressLineOne("San Fransisco").
                enterCity("San Fransisco").
                enterPostCode("12345").
                enterEmail("abc@gmail.com").
                placeOrder();

        Assert.assertEquals(
                orderConfirmationPage.getSuccessMessage(),
                "Thank you. Your order has been received."
        );


    }
    @Test
    public void loginAndCheckout() throws InterruptedException {
        // Use a single variable for the dynamic search term so the flow remains data-driven.
        String term = "Blue";
        StorePage storePage = new HomePage(getDriver()).load()
                .navigateToStoreUsingMenu()
                .searchFor(term)
                .waitForResultsHeading(term)
                .clickAddToCartFor("Blue Shoes");

        Assert.assertEquals(
                storePage.getResultsHeadingText(),           // POM’dan DOM metnini oku
                storePage.buildResultsHeading(term)          // Beklenen stringi yine POM’dan üret
        );
        CartPage cartPage = storePage.clickViewCart();

        Assert.assertEquals(
                cartPage.getProductName(), "Blue Shoes");
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        checkoutPage.clickHereToLogin();

        // creds config.properties'ten gelsin
        String username = ConfigLoader.getInstance().getUsername();
        String password = ConfigLoader.getInstance().getPassword();
        OrderConfirmationPage orderConfirmationPage =
                checkoutPage.
                        login(username, password).  //123@xyz.com
                        enterFirstName("user").
                        enterLastName("demo").
                        enterAddressLineOne("San Fransisco").
                        enterCity("San Fransisco").
                        enterPostCode("12345").
                        enterEmail("abc@gmail.com").
                        placeOrder();

        Assert.assertEquals(
                orderConfirmationPage.getSuccessMessage(),
                "Thank you. Your order has been received.");
    }
}

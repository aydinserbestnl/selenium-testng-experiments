package com.selenium.pom.tests;

import com.selenium.pom.base.BaseTest;
import com.selenium.pom.objects.BillingAddress;
import com.selenium.pom.objects.Product;
import com.selenium.pom.objects.User;
import com.selenium.pom.pages.*;
import com.selenium.pom.utils.ConfigLoader;
import com.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest {

    @Test
    public void guestCheckout() {
        // Use a single variable for the dynamic search term so the flow remains data-driven.
        String searchFor = "Blue";

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setFirstName("user").
                setLastName("demo").
                setAddressLine("San Fransisco").
                setCity("San Fransisco").
                setPostalCode("12345").
                setEmail("abc@gmail.com");

        StorePage storePage = new HomePage(getDriver()).load()
                .navigateToStoreUsingMenu()
                .search(searchFor)
                .waitForResultsHeading(searchFor)
                .clickAddToCartFor("Blue Shoes");

        Assert.assertEquals(
                storePage.getResultsHeadingText(),
                storePage.buildResultsHeading(searchFor)
        );
        CartPage cartPage = storePage.clickViewCart();

        Assert.assertEquals(
                cartPage.getProductName(), "Blue Shoes");
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        OrderConfirmationPage orderConfirmationPage =
                checkoutPage.
                        setBillingAddress(billingAddress).
                        placeOrder();

        Assert.assertEquals(
                orderConfirmationPage.getSuccessMessage(),
                "Thank you. Your order has been received."
        );
    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        // Use a single variable for the dynamic search term so the flow remains data-driven.
        String searchFor = "Blue";
//        BillingAddress billingAddress = new BillingAddress();
//        billingAddress.setFirstName("user").
//                setLastName("demo").
//                setAddressLine("San Fransisco").
//                setCity("San Fransisco").
//                setPostalCode("12345").
//                setEmail("abc@gmail.com");
        BillingAddress billingAddress =  JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);

        User user = new User(ConfigLoader.getInstance().getUsername(), ConfigLoader.getInstance().getPassword());
        StorePage storePage = new HomePage(getDriver()).load()
                .navigateToStoreUsingMenu()
                .search(searchFor)
                .waitForResultsHeading(searchFor)
                .clickAddToCartFor(product.getName());

        Assert.assertEquals(
                storePage.getResultsHeadingText(),
                storePage.buildResultsHeading(searchFor)
        );
        CartPage cartPage = storePage.clickViewCart();

        Assert.assertEquals(
                cartPage.getProductName(), "Blue Shoes");
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        checkoutPage.clickHereToLogin();

        checkoutPage.login(user);
        OrderConfirmationPage orderConfirmationPage =
                checkoutPage.
                        setBillingAddress(billingAddress).
                        placeOrder();

        Assert.assertEquals(
                orderConfirmationPage.getSuccessMessage(),
                "Thank you. Your order has been received.");
    }
}

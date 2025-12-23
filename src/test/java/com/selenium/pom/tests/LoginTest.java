package com.selenium.pom.tests;

import com.selenium.pom.api.actions.CartApi;
import com.selenium.pom.api.actions.SignUpApi;
import com.selenium.pom.base.BaseTest;
import com.selenium.pom.objects.BillingAddress;
import com.selenium.pom.objects.Product;
import com.selenium.pom.objects.User;
import com.selenium.pom.pages.CheckoutPage;
import com.selenium.pom.utils.ConfigLoader;
import com.selenium.pom.utils.FakerUtils;
import com.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends BaseTest {

    @Test
    public void registrationWithApiAndUseCookies() throws IOException, InterruptedException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User()
                .setUserName(username)
                .setPassword("2134")
                .setEmail(username + "@askomdch.com");
        // Register the generated user in the system so login can succeed.
        // With a random username you must register before attempting UI login.
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1); // we are not still in the logged-in session
        //because we did not inject signUp.getCookies() to the browser yet

        // open domain first so cookie domain matches
        getDriver().get(ConfigLoader.getInstance().getBaseUrl());
        // inject cart/session cookies from the API into the browser
        injectCookiesToBrowser(cartApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        // after cookies are injected, open checkout
        checkoutPage.load();

        // UI steps
        checkoutPage
                .clickHereToLogin()
                .login(user);

        System.out.println(checkoutPage.getProductName());
        System.out.println(product.getName());
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }

    @Test
    public void registrationAndLoginDuringCheckout() throws IOException {
        BillingAddress billingAddress =  JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);

        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        // open domain first so cookie domain matches
        getDriver().get(ConfigLoader.getInstance().getBaseUrl());
        // inject only the cart/session cookies (anon cart) into the browser
        injectCookiesToBrowser(cartApi.getCookies());
        // after cookies are injected, re-open checkout
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).
                load().
                setBillingAddress(billingAddress).
                placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
    @Test
    public void loginAndCheckout() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);

        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User()
                .setUserName(username)
                .setPassword("2134")
                .setEmail(username + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user); // got login cookies

        CartApi cartApi = new CartApi(signUpApi.getCookies()); // same session
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1); // add to cart in the logged-in session

        // open domain first so cookie domain matches
        getDriver().get(ConfigLoader.getInstance().getBaseUrl());
        // inject cart/session cookies from the API into the browser
        injectCookiesToBrowser(signUpApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        // after cookies are injected, open checkout
        checkoutPage.load();

        // UI steps
        checkoutPage
                .setBillingAddress(billingAddress)
                .placeOrder();

        System.out.println(checkoutPage.getProductName());
        System.out.println(product.getName());
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }
}
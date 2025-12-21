package com.selenium.pom.tests;

import com.selenium.pom.api.actions.CartApi;
import com.selenium.pom.api.actions.SignUpApi;
import com.selenium.pom.base.BaseTest;
import com.selenium.pom.objects.Product;
import com.selenium.pom.objects.User;
import com.selenium.pom.pages.CheckoutPage;
import com.selenium.pom.utils.ConfigLoader;
import com.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends BaseTest {
    @Test
    public void loginDuringCheckout() throws IOException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUserName(username).
                setPassword("2134").
                setEmail(username + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

//        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
//        injectCookiesToBrowser(cartApi.getCookies());
//        checkoutPage.load().
//                clickHereToLogin().
//                login(user);
        //CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        // önce domain’i aç ki cookie’yi kabul etsin
        getDriver().get(ConfigLoader.getInstance().getBaseUrl());
        // API’den gelen sepet/oturum çerezlerini tarayıcıya bas
        injectCookiesToBrowser(cartApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        // Çerezler yüklendikten sonra checkout sayfasını aç
        checkoutPage.load();

        // UI adımları
        checkoutPage
                .clickHereToLogin()
                .login(user);
        System.out.println(checkoutPage.getProductName());
        System.out.println(product.getName());
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));

    }
}

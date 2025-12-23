package com.selenium.pom.tests;

import com.selenium.pom.api.actions.CartApiWithMergeCookies;
import com.selenium.pom.base.BaseTest;
import com.selenium.pom.objects.BillingAddress;
import com.selenium.pom.pages.CheckoutPage;
import com.selenium.pom.utils.ConfigLoader;
import com.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {
    @Test
    public void guestCheckoutUsingDirectBankTransfer() {
        BillingAddress billingAddress =  JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);

        CartApiWithMergeCookies cartApiWithMergeCookies = new CartApiWithMergeCookies();
        cartApiWithMergeCookies.addToCart(1215, 1);

        // 1) Domain’i aç (cookie domain hatası olmasın)
        getDriver().get(ConfigLoader.getInstance().getBaseUrl());
        // 2) API’den gelen sepet/oturum çerezlerini tarayıcıya bas
        injectCookiesToBrowser(cartApiWithMergeCookies.getCookies());
        // 3) Çerezler yüklendikten sonra checkout’u yükle
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).
                load().
                setBillingAddress(billingAddress).
                placeOrder();

        Assert.assertEquals(
                checkoutPage.getNotice(),
                "Thank you. Your order has been received.");
    }
}

package com.selenium.pom.tests;

import com.selenium.pom.base.BaseTest;
import com.selenium.pom.objects.Product;
import com.selenium.pom.pages.CartPage;
import com.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddToCartTest extends BaseTest {
    @Test
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver()).
                load().
                clickAddToCartFor(product.getName()).
                clickViewCart();

        Assert.assertEquals(
                cartPage.getProductName(), product.getName());
    }
}

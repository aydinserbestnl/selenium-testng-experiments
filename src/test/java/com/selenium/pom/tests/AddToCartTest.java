package com.selenium.pom.tests;

import com.selenium.pom.base.BaseTest;
import com.selenium.pom.dataProviders.MyDataProvider;
import com.selenium.pom.objects.Product;
import com.selenium.pom.pages.CartPage;
import com.selenium.pom.pages.HomePage;
import com.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddToCartTest extends BaseTest {
    @Test
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver()).
                load().getProductThumbnail().
                clickAddToCartFor(product.getName()).
                clickViewCart();

        Assert.assertEquals(
                cartPage.getProductName(), product.getName());
    }
    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class)
    public void addToCartFeatureProducts(Product product) {
        CartPage cartPage = new HomePage(getDriver()).
                load().getProductThumbnail().
                clickAddToCartFor(product.getName()).
                clickViewCart();
        Assert.assertEquals(
                cartPage.getProductName(), product.getName());
    }

}

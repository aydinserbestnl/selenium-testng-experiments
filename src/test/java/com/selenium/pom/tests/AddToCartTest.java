package com.selenium.pom.tests;

import com.selenium.pom.base.BaseTest;
import com.selenium.pom.dataProviders.MyDataProvider;
import com.selenium.pom.objects.Product;
import com.selenium.pom.pages.CartPage;
import com.selenium.pom.pages.HomePage;
import com.selenium.pom.pages.StorePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Spotify Tests")
@Feature("Playlist API Tests")
public class AddToCartTest extends BaseTest {

    @Story("Add to cart from store page")
    @Description("Add to cart from store page")
    @Feature("Cart")
    @Flaky
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Add to cart from store page")
    public void addToCartFromStorePage() throws IOException {

        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver()).
                load().getProductThumbnail().
                clickAddToCartFor(product.getName()).
                clickViewCart();

        Assert.assertEquals(
                cartPage.getProductName(), product.getName() );

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
